#!/usr/bin/env sh

cd code/web 

docker build -t 118.126.66.51/wenanguo/little_bird_api:v1.0.$BUILD_NUMBER -f Dockerfile .

docker push 118.126.66.51/wenanguo/little_bird_api:v1.0.$BUILD_NUMBER

wget "http://123.206.104.174:5000/update?images=118.126.66.51/wenanguo/little_bird-api:v1.0.$BUILD_NUMBER&project=little_bird_api"

#wget "http://123.206.104.174:5000/updateenv?value=http://123.206.104.174:18085/static/doc/swagger.json?v=$BUILD_NUMBER&key=URL&project=swagger-ui_web"

#docker build -t 118.126.66.51/wenanguo/ninepoint-web:v1.0.$BUILD_NUMBER -f Dockerfile-web .

#docker push 118.126.66.51/wenanguo/ninepoint-web:v1.0.$BUILD_NUMBER

#wget "http://123.206.104.174:5000/update?images=118.126.66.51/wenanguo/ninepoint-web:v1.0.$BUILD_NUMBER&project=ninepoine-gray_web"


