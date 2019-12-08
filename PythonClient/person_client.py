import json
import requests
from urllib.parse import quote_plus as urlencode

# Função para invocar a criação de um novo objeto currículo
def curriculum():
    # Make a curriculum object
    name  = input('Digite seu nome: ')
    contact = input('Digite seu contato: ')
    area = input('Digite sua area de interesse: ')
    workload = input('Digite sua carga horaria pretendida: ')
    salary = input('Digite seu salario pretendido: ')

    # Retorna um currículo encapsulada num json para realizar as requisições
    curriculum = {
        "curriculum": {
            "nome": name,
            "contato": contact,
            "area": area,
            "tempo": workload,
            "salario": salary
        }
    }
    
    return curriculum

# Endereço local do servidor
base_url = 'http://localhost:4000'
# Definição do conteúdo da requisição
headers = {'content-type': 'application/json'}
option = 0

# Menu simples para navegação
while option != 4:
    option = input(
        """\nO que voce deseja fazer?
        1 - Cadastrar Currículo
        2 - Atualizar Currículo
        3 - Pesquisar Vagas
        4 - Encerrar \n"""
    )

    # Inserir um novo currículo
    if option == '1':
        data = curriculum()
        url = '{}/curriculum'.format(base_url)
        # Make a post request in the server
        response = requests.post(
            url,
            data=json.dumps(data),
            headers=headers
        )
    # Atualizar um currículo baseado no ID enviado
    elif option == '2':
        data = curriculum()
        id = input("Insira o ID do objeto para atualização")
        url = '{}/curriculum/{}'.format(base_url, id)
        # Make a put request in the server
        response = requests.put(
            url,
            data=json.dumps(data),
            headers=headers
        )
    # Buscar vagas filtrando por área e salario
    elif option == '3':
        area = input("Digite a area desejada: ")
        salary = input("Digite o salario desejado: ")
        url = "{}/job_opportunity?area={}&salario={}".format(
            base_url,
            urlencode(area),
            salary
        )
        print(url)
        # Make a get request in the server
        response = requests.get(url)

    # Printa a resposta do servidor na tela para o usuário
    print("""\n""" + response.text)
