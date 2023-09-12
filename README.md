# Trabalho-banco-de-dados
RESUMO: A proposta de trabalho é desenvolver um sistema que utilize uma API de dados abertos para coletar dados e apresente relatórios e gráficos com consultas sobre esses dados.  
OBJETIVO: adquirir conhecimentos práticos de integração de bancos de dados em aplicações web em camadas, seguindo boas práticas de desenvolvimento.

DETALHAMENTO:

Deve-se implementar um esquema relacional de banco de dados que faça o armazenamento integrado e normalizado dos dados coletados dos serviços acessíveis via web. Devem ser escolhidos serviços de coleta de dados via APIs de dados dados abertos (e.g., http://api.portaldatransparencia.gov.br/, https://www.gov.br/conecta/catalogo/, https://servicodados.ibge.gov.br/api/docs/, https://dadosabertos.camara.leg.br/swagger/api.html, https://www.census.gov/data/developers/data-sets.html, http://data.un.org/Host.aspx?Content=API, etc.).

Em geral, deve-se combinar dados de diferentes fontes e/ou datasets relacionados, enriquecendo o banco de dados. Por exemplo, coletar dados de uma mesma natureza, mas de provedores diferentes (e.g., dados de acidentes de trânsito de diferentes países), ou dados complementares (e.g., dados de censo escolar de um estado e combinados com dados de desempenho em avaliações, como IDEB). É fundamental vislumbrar consultas e relatórios relevantes que possam ser obtidos a partir dos conjuntos de dados coletados.

Ao acessar um serviço de coleta de dados, o sistema deverá processar a resposta da requisição e fazer as inserções correspondentes no banco de dados, utilizando, necessariamente um ou mais controllers e os objetos das camadas de model e de acesso a dados. O banco de dados obrigatoriamente deve armazenar dados normalizados. Isto significa que, nos casos em que o serviço retorna dados não normalizados, deve ser feito um processamento para armazená-lo de forma a eliminar as redundâncias no conjunto de dados.

O sistema também deve apresentar relatórios a respeito dos conjuntos de dados, utilizando gráficos, tabelas e informações relevantes. Espera-se que as consultas utilizadas para produzir os relatórios explorem diferentes recursos da linguagem SQL (junções internas e externas, agregações, agrupamentos, ranking, ordenações, subconsultas, filtros diversos, etc.). Os relatórios também fazendo uso de objetos da camada de acesso a dados, possivelmente, objetos específicos para a geração dos relatórios.

