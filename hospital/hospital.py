import os

from flask import abort
from flask import Flask, request, jsonify

from config.flask_vars import Config
from config.init_flask import make_db_connection

# Declare Flask app
application = Flask(__name__)
application.config.from_object(Config)


@application.route('/query_appointments', methods=['POST'])
def check_appointment():
    connection = make_db_connection()
    cursor = connection.cursor()

    if not request.json:
        abort(400)
    appointment = request.json['appointment']

    check_if_appointment_exist_query = """ SELECT * FROM surgeon.appointments 
                                where id = {}""".format(appointment)
    cursor.execute(check_if_appointment_exist_query)
    connection.commit()
    connection.close()

    return jsonify({'sucess': 'appointment queried'}), 201


if __name__ == '__main__':
    application.run(host='0.0.0.0', threaded=True)
    print("teste")
    ok = input("ok: ")