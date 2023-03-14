sudo docker pull e207/front:1.0

docker run -d -p 80:80 -p 443:443 -v /home/ubuntu/dev/conf.d:/etc/nginx/conf.d -v /home/ubuntu/certbot/conf:/etc/letsencrypt/ -v /home/ubuntu/certbot/www:/var/www/certbot --name front e207/front:1.0
docker image prune -af