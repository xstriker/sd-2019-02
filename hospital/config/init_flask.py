import os

import psycopg2


def make_db_connection():
    connection = psycopg2.connect(user='dis_rest',
                                  password='E!bS{c5CS4Zsm#',
                                  host="postgres",
                                  port='5432',
                                  database='dis_rest')
    return connection
