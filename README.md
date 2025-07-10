Para executar a aplicacao é necessário possuir o docker instalado.
Clone o projeto, acesse o diretório onde está localizado o arquivo Dockerfile e docker-compose.yml e execute o seguinte comando:

docker compose up --build


A collection com os endpoints de teste estão no projeto. O arquivo se chama collection-g10-auth-endpoints.json

Importe o arquivo json no postman para que seja possível testar os endpoints.


Para executar somente o banco de dados no docker utilize o seguinte comando:
docker-compose -f docker-compose-local.yml up -d


Para rodar os testes e verificar o percentual de cobertura basta rodar o seguinte comando:
mvn clean verify

O plugin do jacoco deve gerar um relatório na pasta target:
Exemplo: file:///C:/Users/Lucas/IdeaProjects/foodcore/target/site/jacoco/index.html


