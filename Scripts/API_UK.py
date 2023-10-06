import requests
import json

def download_json_from_url(url, output_file):
    try:
        # Faz a solicitação HTTP GET para a URL
        response = requests.get(url)

        # Verifica se a solicitação foi bem-sucedida (código de status 200)
        if response.status_code == 200:
            # Decodifica o conteúdo JSON da resposta
            json_data = response.json()

            # Salva o conteúdo JSON em um arquivo
            with open(output_file, 'w', encoding='utf-8') as file:
                json.dump(json_data, file, ensure_ascii=False, indent=4)
            
            print(f'Arquivo JSON baixado com sucesso e salvo em {output_file}')
        else:
            print(f'Erro ao fazer a solicitação HTTP. Código de status: {response.status_code}')

    except requests.exceptions.RequestException as e:
        print(f'Erro ao fazer a solicitação HTTP: {e}')

if __name__ == "__main__":
    force = 'city-of-london'
    london_poly = '51.5552584358537, -0.20893083378158345:51.509297022342196, -0.03783056522290192:51.494773249935285,  -0.11041855794476678'
    leeds_poly = '53.8640976279893, -1.6630509456622875:53.77395068013903, -1.4098576820329076:53.767254628930104, -1.5556654758739514'
    liverpool_poly = '53.40037001067784, -2.903581214055851:53.49769368554596, -2.8204971094784144:53.356952373135854,  -3.0052047469274266 '
    birmingham_poly = '52.600783830512015, -2.033930044637152:52.40307738685891, -1.7057134992816583:52.458340733199265,  -1.8526556346500176'
    
    ano = 2020
    mes = 1 
    for i in range(4):
        for j in range(1,13):
            
            
            if mes < 10:
                mes = '0'+str(mes)
        
            data = str(ano)+ '-' + str(mes)
            print(data)
            mes = int(mes)+1
            
            url = (f"https://data.police.uk/api/crimes-street/all-crime?poly={london_poly}&date={data}")  # Substitua pela URL do seu arquivo JSON
            output_file = (f"Jsons/Crimes_London_{data}.json")  # Nome do arquivo de saída
            download_json_from_url(url, output_file)
            
            url = (f"https://data.police.uk/api/crimes-street/all-crime?poly={liverpool_poly}&date={data}")  # Substitua pela URL do seu arquivo JSON
            output_file = (f"Jsons/Crimes_LIverpool_{data}.json")  # Nome do arquivo de saída
            download_json_from_url(url, output_file)
            
            url = (f"https://data.police.uk/api/crimes-street/all-crime?poly={birmingham_poly}&date={data}")  # Substitua pela URL do seu arquivo JSON
            output_file = (f"Jsons/Crimes_Birmingham_{data}.json")  # Nome do arquivo de saída
            download_json_from_url(url, output_file)
            
            url = (f"https://data.police.uk/api/crimes-street/all-crime?poly={leeds_poly}&date={data}")  # Substitua pela URL do seu arquivo JSON
            output_file = (f"Jsons/Crimes_Leeds_{data}.json")  # Nome do arquivo de saída
            download_json_from_url(url, output_file)
        ano = ano+1
        mes = 1