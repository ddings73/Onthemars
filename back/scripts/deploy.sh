sudo docker ps -a -q --filter "name=back" | grep -q . && docker stop back && docker rm back | true
sudo docker rmi e207/back:1.0
sudo docker pull e207/back:1.0
docker run -d -p 8080:8080 --name back e207/back:1.0
docker image prune -af