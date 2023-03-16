echo "> 현재 구동중인 profile 확인"
CURRENT_PROFILE=$(curl -X POST https://j8e207.p.ssafy.io/api/v1/profile)

echo "> $CURRENT_PROFILE"

if [ $CURRENT_PROFILE == v1 ]
then
        IDLE_PROFILE=v2
        IDLE_TAG=2.0
        IDLE_PORT=3001
        CURRENT_TAG=1.0
elif [ $CURRENT_PROFILE == v2 ]
then
        IDLE_PROFILE=v1
        IDLE_TAG=1.0
        IDLE_PORT=3002
        CURRENT_TAG=2.0
else
        echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
        IDLE_PROFILE=v1
        IDLE_TAG=1.0
        IDLE_PORT=3001
        CURRENT_TAG=2.0
fi


sudo docker ps -a -q --filter "name=front-${IDLE_PROFILE}" | grep -q . && docker stop front-$IDLE_PROFILE && docker rm front-$IDLE_PROFILE | true
sudo docker rmi e207/front:$IDLE_TAG
sudo docker pull e207/front:$IDLE_TAG
docker run -d -p $IDLE_PORT:${IDLE_PORT} -v /home/ubuntu/dev/html:/usr/src/app/buil --name front-$IDLE_PROFILE e207/front:$IDLE_TAG
docker image prune -af