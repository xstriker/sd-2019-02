import json
import requests


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
    1 - Cadastrar Vaga
    2 - Atualizar Vaga\n"""
)

if option == '1':
    data = job_opportunity()
    url = '{}/{}'.format(base_url, 'create_job_opportunity')
elif option == '2':
    data = job_opportunity()
    url = '{}/{}'.format(base_url, 'update_job_opportunity')

response = requests.post(
    url,
    data=json.dumps(data),
    headers=headers
)

print(response)