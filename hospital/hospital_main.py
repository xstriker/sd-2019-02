import json
import requests

from model.appointments import restore
from hospital import schedule_appointments

r = requests.get("http://localhost:5002/restore")
r = requests.get("http://localhost:5001/restore")
while True:
    date = input("Escolha uma data: ")
    
    request_body = json.dumps({"appointment_date": date})
    headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
    r = requests.post(
        "http://localhost:5000/query_appointment",
        headers = headers,
        data=request_body
    )
    print(r.content)