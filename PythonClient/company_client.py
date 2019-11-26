import json
import requests
from urllib.parse import quote_plus as urlencode


def job_opportunity():
    # Build a job_opportunity object
    name  = input('Digite o nome da sua empresa: ')
    contact = input('Digite seu contato: ')
    area = input('Digite a area de interesse: ')
    workload = input('Digite a carga horaria pretendida: ')
    salary = input('Digite o salario pretendido: ')

    job_opportunity = {
	"job_opportunity": {
		"nomeEmpresa": name,
		"contato": contact,
		"area": area,
		"tempo": workload,
		"salario": salary
	}
    }
    
    return job_opportunity


base_url = 'http://localhost:4000'
headers = {'content-type': 'application/json'}

option = input(
    """\nO que voce deseja fazer?
    1 - Cadastrar Vaga
    2 - Atualizar Vaga
    3 - Pesquisar Curriculos\n"""
)

if option == '1':
    data = job_opportunity()
    url = '{}/job_opportunity'.format(base_url)
    # Make a post request in the server
    response = requests.post(
        url,
        data=json.dumps(data),
        headers=headers
    )

elif option == '2':
    data = job_opportunity()
    url = '{}/job_opportunity'.format(base_url)
    # Make a put request in the server
    response = requests.put(
        url,
        data=json.dumps(data),
        headers=headers
    )

elif option == '3':
    area = input('Digite a area desejada: ')
    url = '{}/curriculum?area={}'.format(base_url, urlencode(area))
    # Make a get request in the server
    print(url)
    response = requests.get(url)

print(response)
