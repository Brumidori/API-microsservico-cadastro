echo "Parando os contaienrs"


echo "removendo "
docker rmi -f  19031988/cadastro-starters:latest

echo "Criando "
docker build . -t  19031988/cadastro-starters:latest

echo "executando docker-compose"
docker push 19031988/cadastro-starters:latest
