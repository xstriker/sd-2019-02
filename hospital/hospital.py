import os
import requests

from flask import abort
from flask import Flask, request, jsonify

from config.flask_vars import Config
from model.appointments import check_appointment, save_to_temp_agenda

# Declare Flask app
application = Flask(__name__)
application.config.from_object(Config)


@application.route('/query_appointment', methods=['POST'])
def query_appointments():
    if not request.json:
        abort(400)
    appointment_date = request.json['appointment_date']

    has_free_date = check_appointment(appointment_date, 'hospital')

    if has_free_date:
        return jsonify({'appointment': 'date avaible'}), 201
    
    return jsonify({'appointment': 'date not avaible'}), 201

@application.route('/schedule_appointment', methods=['POST'])
def schedule_appointments():
    if not request.json:
        abort(400)
    appointment_date = request.json['appointment_date']

    has_free_date = check_appointment(appointment_date, 'hospital')

    if has_free_date:
        id = save_to_temp_agenda(appointment_date, 'hospital')
        request_body = {
            'id': id,
            'appointment_date': appointment_date
        }
        r_surgeon = requests.post(
            "http://localhost:{}".format(os.getenv('SURGEON_PORT')),
            data=request_body
        )
        r_anesthetist = requests.post(
            "http://localhost:{}".format(os.getenv('ANESTHETIST_PORT')),
            data=request_body
        )

    return jsonify({'appointment': 'date not avaible'}), 201


if __name__ == '__main__':
    application.run(host='0.0.0.0', threaded=True)
