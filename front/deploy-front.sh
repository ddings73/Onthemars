sudo docker rmi e207/front:1.0
sudo docker pull e207/front:1.0

docker rm front
docker run -d -p 3000:3000 --name front e207/front:1.0

docker cp front:/usr/src/app/build /home/ubuntu/dev/html

sudo cp -rf /home/ubuntu/dev/html/build/* /home/ubuntu/dev/html
sudo rm -r /home/ubuntu/dev/html/build

docker exec nginx service nginx reload