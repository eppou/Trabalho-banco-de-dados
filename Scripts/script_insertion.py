import pandas as pd
import sys



def main():
    # Read the data from the csv file
    df = pd.read_csv('data.csv')
    sql_queries = []


    for index, row in df.iterrows():
        data_crime = row['data']
        data_crime = data_crime + "-15"
        tipo = row['tipo']
        descricao = row['descricao']
        latitude = float(row['latitude'])
        longitude = float(row['longitude'])
        nome_cidade = sys.argv[1]
        nome_estado = sys.argv[2]
        sigla_estado = sys.argv[3]
        nome_pais = "Reino Unido"

        sql = f"""
        INSERT INTO crime_db.crimes(tipo, descricao, data_crime, latitude, longitude, nome_cidade, nome_estado, sigla_estado, nome_pais)
        VALUES('{tipo}', '{descricao}', '{data_crime}', {latitude}, {longitude}, '{nome_cidade}', '{nome_estado}', '{sigla_estado}', '{nome_pais}');
        """
        sql_queries.append(sql)
    
    with open(f'queries_{sys.argv[1]}.sql', 'w') as query_file:
        query_file.write('\n'.join(sql_queries))
    

if __name__ == "__main__":
    main()