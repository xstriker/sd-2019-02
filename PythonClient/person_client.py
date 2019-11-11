import json
import requests


def curriculum():
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


base_url = 'https://api.github.com/some/endpoint'
headers = {'content-type': 'application/json'}

option = input(
    """\nO que voce deseja fazer?
    1 - Cadastrar Curriculo
    2 - Atualizar Curriculo"""
)

if option == '1':
    data = curriculum()
    url = '{}/{}'.format(base_url, 'create_curriculum')
elif option == '2':
    data = curriculum()
    url = '{}/{}'.format(base_url, 'update_curriculum')

response = requests.post(
    url,
    data=json.dumps(data),
    headers=headers
)

print(response)