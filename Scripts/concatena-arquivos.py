import pandas as pd
import os

# Diretório que contém os arquivos CSV que você deseja juntar
diretorio = 'C:\\Users\\marco\\Documents\\Banco de Dados\\Data_Treat'

# Lista para armazenar DataFrames de cada arquivo CSV
dataframes = []

# Iterar pelos arquivos CSV no diretório
for nome_arquivo in os.listdir(diretorio):
    if nome_arquivo.endswith('.csv'):
        caminho_arquivo = os.path.join(diretorio, nome_arquivo)

        # Ler cada arquivo CSV e armazená-lo como um DataFrame
        df = pd.read_csv(caminho_arquivo)
        dataframes.append(df)

# Concatenar os DataFrames em um único DataFrame
resultado_final = pd.concat(dataframes, ignore_index=True)

# Salvar o DataFrame resultante em um novo arquivo CSV
resultado_final.to_csv('C:\\Users\\marco\\Documents\\Banco de Dados\\Data_Treat\\Dados_Concatenados\\dados_Birmingham_tratados.csv', index=False)
