import os
import time
import json
import requests
from datetime import date

from config.init_flask import make_db_connection

# lib with reusable methods called by all members 

# Create first row in db schema with serial ID and DATE in params
# called by all members to create the register
def insert_request(date, schema, id=None):
    time.sleep(2)
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

# Check itself if DATE is free
# called by all members check a date
def _check_appointment(id, date, schema, restore=False):
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

# if DATE is free set myself as true
def _myself_success(id, schema, date, restore=False):
    # UPDATE MYSELF SUCCESS 
    connection = make_db_connection()
    cur = connection.cursor()

    check_if_appointment_exist = """
        UPDATE {}.appointments_temp SET myself_success = '1' WHERE id = {}
    """.format(schema, id)
    cur.execute(check_if_appointment_exist)
    connection.commit()
    connection.close()

    if restore:
        return call_check_appointment_state(id, date, schema)
    if schema == 'hospital':
        _call_your_friend(id, schema, 'ANESTHETIST', date, 2)
        _call_your_friend(id, schema, 'SURGEON', date, 2)
    else:
        _call_your_friend(id, schema, 'HOSPITAL', date, 1)
    
    return True

# create request to ask another members a DATE
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

# set another members response after answer
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

# check if all members already answer the request 
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
    
# if all members response  are OK - commit the DATE
# update to succsses / insert on the final db / kill the queue / call other members
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

# if someone response is NOK - abort transaction
# update to fail / continue queue / call other members
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
            _continue_queue(date, schema)
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

# restart the next member from the queue
def _continue_queue(date, schema):
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
    if id:
        _check_appointment(id, date, schema)

# check myself status
def check_appointment_status(schema, id):
    connection = make_db_connection()
    cur = connection.cursor()

    # Check temporary agenda
    query = """
        SELECT myself_success FROM {}.appointments_temp WHERE id = {} 
        AND appointment_success is null
    """.format(schema, id)

    cur.execute(query)
    response = cur.fetchone()
    connection.close()
    if response:
        return 1

# restore the system using logs - called on startup
def restore(schema):
    appointment = find_next_incomplete_appointments(schema)
    while(appointment):
        if schema == 'hospital':
            hospital_restore_conditions(appointment)
        else:
            friends_restore_conditions(appointment, schema)
        appointment = find_next_incomplete_appointments(schema)
    
    return "System restored"
        
# conditions to restore the hospital based on state
def hospital_restore_conditions(appointment):
    if appointment[5] is None:
        return _check_appointment(appointment[0], appointment[1], 'hospital')
    elif appointment[2] is None or appointment[3] is None:
        if appointment[2] is None:
            _call_your_friend(
                appointment[0], 'hospital',
                'ANESTHETIST', appointment[1], 2
            )
        if appointment[3] is None:
            _call_your_friend(
                appointment[0], 'hospital',
                'SURGEON', appointment[1], 2
            )
    else:
        return check_appointment_reponse('hospital', appointment[0], appointment[1])

# conditions to restore the ANESTHETIST and the surgeon based on state
def friends_restore_conditions(appointment, schema):
    if appointment['myself_success'] is None:
        return _check_appointment(appointment[0], appointment[1], schema, True)
    elif appointment['appointment_success']:
        return call_check_appointment_state(appointment[0], appointment[1], schema)
    else:
        return check_appointment_reponse(schema, appointment[0], appointment[1])

# find next appointment to be restored
def find_next_incomplete_appointments(schema):
    connection = make_db_connection()
    cur = connection.cursor()

    # Check temporary agenda
    query = """
        SELECT * FROM {}.appointments_temp 
            WHERE myself_success is null 
            AND id = (SELECT MIN(id) FROM {}.appointments_temp 
                        WHERE myself_success is null)
    """.format(schema, schema)

    cur.execute(query)
    response = cur.fetchone()
    connection.close()
    return response

# check the state of a appointment
def check_appointment_state(id):
    connection = make_db_connection()
    cur = connection.cursor()

    # Check temporary agenda
    query = """
        SELECT appointment_success FROM hospital.appointments_temp 
        WHERE id = {}
    """.format(id)

    cur.execute(query)
    response = cur.fetchone()[0]
    connection.close()
    return response

# make the request to ask hospital the status of a transaction
def call_check_appointment_state(id, date, schema):
    request_body = {'id': id}
    request_body = json.dumps(request_body)
    headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
    r = requests.post(
        "http://{}:5000/check_appointment"
            .format('HOSPITAL'),
        headers = headers,
        data=request_body
    )
    # find out how content arrives
    if r.content == 'true':
        return commit_appointment(id, schema, date)
    elif r.content == 'false':
        return abort(id, schema, date, False)
    else:
        return "Waiting for response"
