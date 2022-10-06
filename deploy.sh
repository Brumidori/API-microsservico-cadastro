echo "Parando os contaienrs"

docker-compose -f devops/docker-compose.yml down

echo "removendo as imagens docker"
docker rmi -f auth:latest

echo "removendo container"
docker rm -f auth

echo "executando docker-compose"
docker-compose -f devops/docker-compose.yml up -d
