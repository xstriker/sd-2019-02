import time
from datetime import date

from config.init_flask import make_db_connection


def check_appointment(date, schema):
    connection = make_db_connection()
    cur = connection.cursor()

    # Check temporary agenda
    check_if_appointment_temp_exist = """
        SELECT * FROM {}.appointments_temp where appointment_time = '{}'
    """.format(schema, date)
    cur.execute(check_if_appointment_temp_exist)
    response = cur.fetchone()
    
    connection.close()

    # If there is one transaction using that date, lock the request till its free
    if response:
        time.sleep(10)
        return check_appointment(date, schema)
    
    # Else check the valid agenda
    return _check_valid_agenda(date, schema)

def _check_valid_agenda(date, schema):
    connection = make_db_connection()
    cur = connection.cursor()

    check_if_appointment_exist = """
        SELECT * FROM {}.appointments where appointment_time = '{}'
    """.format(schema, date)
    cur.execute(check_if_appointment_exist)
    response = cur.fetchone()
    
    connection.close()

    if response:
        return 0
    return 1

def save_to_temp_agenda(date, schema):
    connection = make_db_connection()
    cur = connection.cursor()

    insert_appointment = """
        INSERT INTO {}.appointments_table VALUES (appointment_time, open_time)\
        values ({},{})
    """.format(schema, date, date.now())
    cur.execute(insert_appointment)
    cur.commit

    query_id = """
        select id from {}.appointments_table where appointment_time = {}
    """.format(schema, date)
    cur.execute(query_id)
    id = cur.fetchone()
    
    connection.close()

    if id:
        return id
    return 0