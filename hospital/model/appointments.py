import os
import time
import json
import requests
from datetime import date

from config.init_flask import make_db_connection


def insert_request(date, schema, id=None):
    connection = make_db_connection()
    cur = connection.cursor()

    if schema == 'hospital':
        insert_request = """
            INSERT INTO {}.appointments_temp (appointment_date)
            VALUES ('{}') RETURNING id;
        """.format(schema, date)
    else:
        insert_request = """
            INSERT INTO {}.appointments_temp (id, appointment_date)
            VALUES ({}, '{}') RETURNING id;
        """.format(schema, id, date)

    cur.execute(insert_request)
    connection.commit()
    id = cur.fetchone()[0]
    connection.close()

    return _check_appointment(id, date, schema) 


def _check_appointment(id, date, schema):
    connection = make_db_connection()
    cur = connection.cursor()

    # Check agenda
    check_if_appointment_exist = """
        SELECT * FROM {}.appointments WHERE appointment_date = '{}' 
    """.format(schema, date)
    cur.execute(check_if_appointment_exist)
    response = cur.fetchone()
    
    if response and schema == 'hospital':
        return abort(id, schema, date, False)
    elif response:
        return abort(id, schema, date)

    # Check temporary agenda
    check_if_appointment_temp_exist = """
        SELECT * FROM {}.appointments_temp WHERE appointment_date = '{}' 
        AND appointment_success is null AND id != {}
    """.format(schema, date, id)
    cur.execute(check_if_appointment_temp_exist)
    response = cur.fetchone()
    connection.close()
    
    if response:
        return "you are on queue"

    # Else check the valid agenda
    return _myself_success(id, schema, date)

def _myself_success(id, schema, date):
    # UPDATE MYSELF SUCCESS 
    connection = make_db_connection()
    cur = connection.cursor()

    check_if_appointment_exist = """
        UPDATE {}.appointments_temp SET myself_success = '1' WHERE id = {}
    """.format(schema, id)
    cur.execute(check_if_appointment_exist)
    connection.commit()
    connection.close()

    if schema == 'hospital':
        _call_your_friend(id, schema, 'ANESTHETIST', date, 2)
        _call_your_friend(id, schema, 'SURGEON', date, 2)
    else:
        _call_your_friend(id, schema, 'HOSPITAL', date, 1)
    
    return True

def _call_your_friend(id, persona, target, appointment_date, success):
    request_body = {
        'id': id,
        'appointment_date': appointment_date,
        'persona': persona,
        'success': success
    }
    request_body = json.dumps(request_body)
    headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
    r = requests.post(
        "http://{}:5000/schedule_appointment"
            .format(target),
        headers = headers,
        data=request_body
    )
    print(r.content)
    return r.content

def receive_update_appointment(id, schema, persona, response):
    connection = make_db_connection()
    cur = connection.cursor()

    insert_appointment = """
        UPDATE {}.appointments_temp 
        SET {}_appointment_response = '{}'
        WHERE id = {}
    """.format(schema, persona, response, id)

    cur.execute(insert_appointment)
    connection.commit()
    connection.close()

def check_appointment_reponse(schema, id, date):
    connection = make_db_connection()
    cur = connection.cursor()

    # If both responses were success
    query = """
        SELECT * FROM {}.appointments_temp WHERE id = {} AND 
        anesthetist_appointment_response is true AND
        surgeon_appointment_response is true AND
        appointment_success is null
    """.format(schema, id)
    cur.execute(query)
    response = cur.fetchone()

    connection.close()
    if response:
        return commit_appointment(id, schema, date)
    
    # If at least one of them failed
    connection = make_db_connection()
    cur = connection.cursor()
    query = """
        SELECT * FROM {}.appointments_temp WHERE id = {} AND 
        (anesthetist_appointment_response is false OR
        surgeon_appointment_response is false)
    """.format(schema, id)
    cur.execute(query)
    response = cur.fetchone()

    connection.close()
    if response:
        return abort(id, schema, date)
    

def commit_appointment(id, schema, date):
    connection = make_db_connection()
    cur = connection.cursor()

    # Check temporary agenda
    query = """
        UPDATE {}.appointments_temp SET appointment_success = '1'
        WHERE id = {}
    """.format(schema, id)
    cur.execute(query)
    
    query = """
        INSERT INTO {}.appointments (id, appointment_date) 
        VALUES ({}, '{}')
    """.format(schema, id, date)
    cur.execute(query)
    
    # Query all other appointments on queue
    query = """
        UPDATE {}.appointments_temp SET appointment_success = '0'
        WHERE appointment_date = '{}' AND id != {} 
    """.format(schema, date, id)
    cur.execute(query)
    connection.commit()
    connection.close()

    if schema == 'hospital':
        _call_your_friend(id, schema, 'SURGEON', date, 1)
        _call_your_friend(id, schema, 'ANESTHETIST', date, 1)

    return 'appointment successs'


def abort(id, schema, date, send_response=True):
    if send_response == True:
        connection = make_db_connection()
        cur = connection.cursor()

        insert_appointment = """
            UPDATE {}.appointments_temp SET appointment_success = '0',
            myself_success = '0'
            WHERE id = {}
        """.format(schema, id)

        cur.execute(insert_appointment)
        connection.commit()
        connection.close()

        if schema == 'hospital':
            _call_your_friend(id, schema, 'SURGEON', date, 0)
            _call_your_friend(id, schema, 'ANESTHETIST', date, 0)
            continue_queue(date, schema)
        else:
            _call_your_friend(id, schema, 'HOSPITAL', date, 0)
    else:
        connection = make_db_connection()
        cur = connection.cursor()

        insert_appointment = """
            UPDATE {}.appointments_temp SET appointment_success = '0',
            myself_success = '0'
            WHERE id = {}
        """.format(schema, id)

        cur.execute(insert_appointment)
        connection.commit()
        connection.close()

    return 'appointment denied'

def continue_queue(date, schema):
    connection = make_db_connection()
    cur = connection.cursor()

    # Check temporary agenda
    query = """
        SELECT min(id) FROM {}.appointments_temp WHERE appointment_date = '{}' 
        AND appointment_success is null 
    """.format(schema, date)

    cur.execute(query)
    id = cur.fetchone()[0]
    connection.close()

    _check_appointment(id, date, schema)

def check_appointment_status(schema, id):
    connection = make_db_connection()
    cur = connection.cursor()

    # Check temporary agenda
    query = """
        SELECT myself_success FROM {}.appointments_temp WHERE id = {} 
        AND myself_success is null 
    """.format(schema, id)

    cur.execute(query)
    response = cur.fetchone()
    connection.close()
    if response:
        return 1