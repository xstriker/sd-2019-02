import json
import requests
from urllib.parse import quote_plus as urlencode


def curriculum():
    # Make a curriculum object
    name  = input('Digite seu nome: ')
    contact = input('Digite seu contato: ')
    area = input('Digite sua area de interesse: ')
    workload = input('Digite sua carga horaria pretendida: ')
    salary = input('Digite seu salario pretendido: ')

    curriculum = { 
        'nome': name,
        'contato': contact,
        'area': area,
        'carga_horaria': workload,
        'salario': salary 
    }
    return curriculum


base_url = 'http://localhost'
headers = {'content-type': 'application/json'}

option = input(
    """\nO que voce deseja fazer?
    1 - Cadastrar Curriculo
    2 - Atualizar Curriculo
    3 - Consultar Vagas\n"""
)

if option == '1':
    data = curriculum()
    url = '{}/curriculum/insert'.format(base_url)
    # Make a post request in the server
    response = requests.post(
        url,
        data=json.dumps(data),
        headers=headers
    )

elif option == '2':
    data = curriculum()
    url = '{}/curriculum/update'.format(base_url)
    # Make a put request in the server
    response = requests.put(
        url,
        data=json.dumps(data),
        headers=headers
    )

elif option == '3':
    area = input("Digite a area desejada: ")
    salary = input("Digite o salario desejado: ")
    url = "{}/job_opportunity/area={}&salary={}".format(
        base_url,
        urlencode(area),
        salary
    )
    # Make a get request in the server
    response = requests.get(url)

print(response)