from flask import abort
from flask import Flask, request, jsonify

from config.flask_vars import Config
from model.appointments import (
    insert_request, receive_update_appointment, check_appointment_reponse
)

# Declare Flask app
application = Flask(__name__)
application.config['DEBUG'] = True
application.config.from_object(Config)

@application.route('/query_appointment', methods=['POST'])
def schedule_appointments(): #(appointment_date):
    if not request.json:
        abort(400)
    appointment_date = request.json['appointment_date']
    has_free_date = insert_request(appointment_date, 'hospital')

    return jsonify({'appointment': has_free_date}), 201


@application.route('/schedule_appointment', methods=['POST'])
def appointment_response():
    if not request.json:
        abort(400)

    id = request.json['id']
    date = request.json['appointment_date']
    persona = request.json['persona']
    response = request.json['success']

    receive_update_appointment(id, 'hospital', persona, response)
    response = check_appointment_reponse('hospital', id, date)

    if response:
        return jsonify({'appointment': 'success'}), 201
    return jsonify({'appointment': 'not success'}), 201

if __name__ == '__main__':
    application.run(host='0.0.0.0', threaded=True, debug=True)
