echo "> 현재 구동중인 profile 확인"
CURRENT_PROFILE=$(curl -s https://j8e207.p.ssafy.io/api/v1/profile)

echo "> $CURRENT_PROFILE"

if [ $CURRENT_PROFILE == dev1 ]
then
        IDLE_PROFILE=dev2
        IDLE_PORT=8082
elif [ $CURRENT_PROFILE == dev2 ]
then
        IDLE_PROFILE=dev1
        IDLE_PORT=8081
else
        echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
        echo "> dev1을 할당합니다. IDLE_PROFILE: dev1"
        IDLE_PROFILE=dev1
        IDLE_PORT=8081
fi

echo $CURRENT_PROFILE
echo $IDLE_PROFILE
echo $IDLE_PORT

sudo docker rmi e207/back:1.0
sudo docker pull e207/back:1.0
docker run -d -p $IDLE_PORT:${IDLE_PORT} --name $IDLE_PROFILE -e Profile=$IDLE_PROFILE e207/back:1.0
docker image prune -af

sh switch.sh
