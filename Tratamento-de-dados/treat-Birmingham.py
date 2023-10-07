import sys
import os
import pandas as pd
from translate import Translator


def extract_lat_lon(location_data):
    latitude = location_data['latitude']
    longitude = location_data['longitude']
    return latitude, longitude


def getLatLon(row):
    lat_lon = extract_lat_lon(row['location'])
    return lat_lon[0], lat_lon[1]


def translateToPortuguese(row):
    categoria = row['category'].replace('-', ' ')
    nome = row['location']['street']['name']

    # Concatene a categoria e o nome
    texto_para_traduzir = f'{categoria} {nome}'

    # Traduza o texto para o português (essa parte pode pesar o codigo)
    tradutor = Translator(to_lang="pt")
    traducao = tradutor.translate(texto_para_traduzir)

    return traducao


def determineType(tl, l):
    if l['category'] == "criminal-damage-arson" or l['category'] == "violent-crime":
        tl['grave'] = 1
        tl['tipo'] = "crime violento"
    else:
        tl['grave'] = 0

        if l['category'] == "anti-social-behaviour":
            tl['tipo'] = "danos morais"

        if l['category'] == "bicycle-theft":
            tl['tipo'] = "roubo"

        if l['category'] == "burglary":
            tl['tipo'] = "furto"

        if l['category'] == "drugs":
            tl['tipo'] = "drogas"

        if l['category'] == "other-theft":
            tl['tipo'] = "furto"

        if l['category'] == "possession-of-weapons":
            tl['tipo'] = "posse de armas"

        if l['category'] == "public-order":
            tl['tipo'] = "violação da ordem publica"

        if l['category'] == "robbery":
            tl['tipo'] = "roubo"

        if l['category'] == "shoplifting":
            tl['tipo'] = "furto"

        if l['category'] == "theft-from-the-person":
            tl['tipo'] = "roubo"

        if l['category'] == "vehicle-crime":
            tl['tipo'] = "roubo de veiculo"

        if l['category'] == "other-crime":
            tl['tipo'] = "outros"


# -=============================================MAIN=======================================-#
def main():
    path = sys.argv[1]
    path.replace('\\',"\\\\")

    df = pd.read_json(path)
    df_treated = pd.DataFrame(columns=['data', 'latitude', 'longitude', 'descricao', 'tipo', 'grave', 'n_vitimas'])

    for index, line in df.iterrows():
        treated_line = {
            'data': line['month'],
            'latitude': getLatLon(df.iloc[index])[0],
            'longitude': getLatLon(df.iloc[index])[1],
            'descricao': translateToPortuguese(df.iloc[index]),
            'tipo': None,  # Você pode definir o valor padrão aqui
            'grave': None,  # Você pode definir o valor padrão aqui
            'n_vitimas': -1
        }
        determineType(treated_line, line)

        df_treated = df_treated._append(treated_line, ignore_index=True)

    nome_arquivo = os.path.basename(path)
    nome_arquivo = nome_arquivo.replace(".json", "_tratado.csv")
    df_treated.to_csv(nome_arquivo,index=False)
    print("DataFrame salvo com sucesso.")

if __name__ == "__main__":
    main()