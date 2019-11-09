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


def job_opportunity():
    name  = input('Digite o nome da sua empresa: ')
    contact = input('Digite seu contato: ')
    area = input('Digite a area de interesse: ')
    workload = input('Digite a carga horaria pretendida: ')
    salary = input('Digite o salario pretendido: ')

    job_opportunity = { 
        'nome': name,
        'contato': contact,
        'area': area,
        'carga_horaria': workload,
        'salario': salary 
    }

    return job_opportunity


base_url = 'https://api.github.com/some/endpoint'
headers = {'content-type': 'application/json'}

option = input(
    """\nO que voce deseja fazer?
    1 - Cadastrar Curriculo
    2 - Cadastrar Vaga
    3 - Atualizar Curriculo
    4 - Atualizar Vaga\n"""
)

if option == '1':
    data = curriculum()
    url = '{}/{}'.format(base_url, 'create_curriculum')
elif option == '2':
    data = job_opportunity()
    url = '{}/{}'.format(base_url, 'create_job_opportunity')
elif option == '3':
    data = curriculum()
    url = '{}/{}'.format(base_url, 'update_curriculum')
elif option == '4':
    data = curriculum()
    url = '{}/{}'.format(base_url, 'update_job_opportunity')

response = requests.post(
    url,
    data=json.dumps(data),
    headers=headers
)

print(response)