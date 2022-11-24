# cloud-parking
Cloud Parking

## Run database
docker run --name parking-db -p:5432:5432 -e POSTGRES_DB=parking -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123 -d postgres:10-alpine

## depois
docker ps

## para parar a aplicacao no docker
docker stop parking-db

## iniciar
docker start parking-db