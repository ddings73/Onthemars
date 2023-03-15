sudo docker ps -a -q --filter "name=back" | grep -q . && docker stop back && docker rm back | true
sudo docker ps -a -q --filter "name=front" | grep -q . && docker stop front && docker rm front | true

sudo docker rmi e207/back:latest
sudo docker rmi e207/front:latest

sudo docker pull e207/back:latest
sudo docker pull e207/front:latest

docker run -d -p 8080:8080 --name back e207/back:latest
docker run -d -p 80:80 -p 443:443 -v /home/ubuntu/nginx:/etc/nginx/conf.d -v /home/ubuntu/certbot/conf:/etc/letsencrypt/ -v /home/ubuntu/certbot/www:/var/www/certbot --name front e207/front:latest

docker image prune -af