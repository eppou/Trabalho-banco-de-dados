import os
import json
import subprocess

diretorio = "C:\\Users\\marco\\Documents\\Banco de Dados\\Dados Brutos\\Birmingham"

for nome_arquivo in os.listdir(diretorio):
    if nome_arquivo.endswith('.json'):
        caminho_arquivo = os.path.join(diretorio, nome_arquivo)
        print(caminho_arquivo)

        # Chame o seu programa Python com o arquivo JSON como argumento
        comando = ['python', 'treat-Birmingham.py', caminho_arquivo]
        print(comando)
        # Execute o programa usando o subprocess
        subprocess.run(comando)