echo "> 현재 구동중인 profile 확인"
CURRENT_PROFILE=$(curl -s https://j8e207.p.ssafy.io/api/v1/profile)

echo "> $CURRENT_PROFILE"

if [ $CURRENT_PROFILE == dev1 ]
then
        IDLE_PROFILE=dev2
        IDLE_PORT=8082
				IDLE_TAG=2.0
				CURRENT_TAG=1.0
elif [ $CURRENT_PROFILE == dev2 ]
then
        IDLE_PROFILE=dev1
        IDLE_PORT=8081
				IDLE_TAG=1.0
				CURRENT_TAG=2.0
else
        echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
        IDLE_PROFILE=dev1
        IDLE_PORT=8081
				IDLE_TAG=1.0
				CURRENT_TAG=2.0
fi

sudo docker ps -a -q --filter "name=back-${CURRENT_PROFILE}" | grep -q . && docker stop back-$CURRENT_PROFILE && docker rm back-$CURRENT_PROFILE | true
sudo docker rmi e207/back:$CURRENT_TAG
sudo docker pull e207/back:$IDLE_TAG
docker run -d -p $IDLE_PORT:${IDLE_PORT} --name back-$IDLE_PROFILE -e Profile=$IDLE_PROFILE e207/back:$IDLE_TAG

sh switch.sh