import json
import requests
from urllib.parse import quote_plus as urlencode

# Função para invocar a criação de um novo objeto vaga
def job_opportunity():
    # Build a job_opportunity object
    name  = input('Digite o nome da sua empresa: ')
    contact = input('Digite seu contato: ')
    area = input('Digite a area de interesse: ')
    workload = input('Digite a carga horaria pretendida: ')
    salary = input('Digite o salario pretendido: ')
    # Retorna uma vaga encapsulada num json para realizar as requisições
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

# Endereço local do servidor
base_url = 'http://localhost:4000'
# Definição do conteúdo da requisição
headers = {'content-type': 'application/json'}
option = 0

# Menu simples para navegação
while option != 4:
    option = input(
        """\nO que voce deseja fazer?
        1 - Cadastrar Vaga
        2 - Atualizar Vaga
        3 - Pesquisar Curriculos
        4 - Encerrar \n"""
    )
    # Inserir uma nova vaga
    if option == '1':
        data = job_opportunity()
        url = '{}/job_opportunity'.format(base_url)
        # Make a post request in the server
        response = requests.post(
            url,
            data=json.dumps(data),
            headers=headers
        )
    # Atualizar uma vaga baseada no ID enviado
    elif option == '2':
        data = job_opportunity()
        id = input("Insira o ID do objeto para atualização: ")
        url = '{}/job_opportunity/{}'.format(base_url, id)
        # Make a put request in the server
        response = requests.put(
            url,
            data=json.dumps(data),
            headers=headers
        )
    # Buscar curriculos filtrando por área
    elif option == '3':
        area = input('Digite a area desejada: ')
        url = '{}/curriculum?area={}'.format(base_url, urlencode(area))
        # Make a get request in the server
        response = requests.get(url)

    # Printa a resposta do servidor na tela para o usuário
    print("""\n""" + response.text)
