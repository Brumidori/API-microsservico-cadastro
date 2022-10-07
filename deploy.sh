echo "Parando os contaienrs"

docker-compose -f docker-compose.yml down

echo "removendo as imagens docker"
docker rmi -f  19031988/cadastro-starters:latest

echo "removendo container"
docker rm -f  19031988/cadastro-starters:latest

echo "executando docker-compose"
docker-compose -f docker-compose.yml up -d
