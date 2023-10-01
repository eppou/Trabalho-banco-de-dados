#!/usr/bin/env python

# Certifique-se de instalar esses pacotes antes de executar:
# pip install pandas
# pip install sodapy

import pandas as pd
from sodapy import Socrata

# Autenticação do cliente (necessária para conjuntos de dados não públicos)
client = Socrata("data.lacity.org", None)

# Defina o tamanho de página desejado para cada solicitação (aqui, 2000 é o máximo permitido)
page_size = 2000

# Inicialize uma lista vazia para armazenar todos os resultados
all_results = []

# Defina um contador de página inicial
page_num = 0

while page_num < 1:
    # Faça a solicitação para a página atual com o tamanho de página especificado
    results = client.get("2nrs-mtv8", limit=page_size, offset=page_num * page_size)

    # Se não houver mais resultados, saia do loop
    if len(results) == 0:
        break

    # Adicione os resultados atuais à lista de resultados
    all_results.extend(results)

    # Incremente o contador de página para a próxima página
    page_num += 1

# Converta todos os resultados em um DataFrame
results_df = pd.DataFrame.from_records(all_results)

# Salve os dados em um arquivo JSON
results_df.to_json('dadosLosAngeles_example.json', orient='records', lines=True)

