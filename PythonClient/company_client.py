import json
import requests


def job_opportunity():
    # Build a job_opportunity object
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
    2 - Atualizar Vaga
    3 - Pesquisar Curriculos\n"""
)

if option == '1':
    data = job_opportunity()
    url = '{}/job_opportunity/insert'.format(base_url)
    # Make a post request in the server
    response = requests.post(
        url,
        data=json.dumps(data),
        headers=headers
    )

elif option == '2':
    data = job_opportunity()
    url = '{}/job_opportunity/update'.format(base_url)
    # Make a put request in the server
    response = requests.put(
        url,
        data=json.dumps(data),
        headers=headers
    )

elif option == '3':
    area = input('Digite a area desejada: ')
    url = '{}/curriculum/area={}'.format(base_url, area)
    # Make a get request in the server
    response = requests.get(url)

print(response)
