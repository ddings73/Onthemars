sudo docker ps -a -q --filter "name=back" | grep -q . && docker stop back && docker rm back | true
sudo docker rmi e207/back:1.0
sudo docker pull e207/back:1.0
docker run --name $IDLE_PROFILE -d --rm -p $IDLE_PORT:${IDLE_PORT} --name e207/back:1.0
docker image prune -af
sudo sh switch.sh