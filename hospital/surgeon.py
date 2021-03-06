from flask import abort as abort_flask
from flask import Flask, request, jsonify

from config.flask_vars import Config
from model.appointments import (
    insert_request, commit_appointment, 
    check_appointment_status, abort, restore
)

# surgeon main with endpoints

# Declare Flask app
application = Flask(__name__)
application.config['DEBUG'] = True
application.config.from_object(Config)

# response of status to a request
@application.route('/schedule_appointment', methods=['POST'])
def query_appointments():
    if not request.json:
        abort_flask(400)

    id = request.json['id']
    success = request.json['success']
    date = request.json['appointment_date']
    
    if success == 1:
        commit_appointment(id, 'surgeon', date)
        return jsonify({'appointment': 'commited'}), 201
    elif success == 0:
        is_open = check_appointment_status('surgeon', id)
        if is_open:
            abort(id, 'surgeon', date, False)
        return jsonify({'appointment': 'denied'}), 201
    else:
        insert_request(date, 'surgeon', id)
        return jsonify({'appointment': 'avaible'}), 201

@application.route('/restore', methods=['GET'])
def restore_surgeon():
    restore('surgeon')
    return jsonify({'restore': 'ok'}), 201

if __name__ == '__main__':
    application.run(host='0.0.0.0', threaded=True, debug=True)