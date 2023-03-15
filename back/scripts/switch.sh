echo "> 현재 구동중인 profile 확인"
CURRENT_PROFILE=$(curl -X POST https://j8e207.p.ssafy.io/api/v1/profile)

if [ $CURRENT_PROFILE == dev1 ]
then
  IDLE_PORT=8082
elif [ $CURRENT_PROFILE == dev2 ]
then
  IDLE_PORT=8081
else
  echo "> 일치하는 Profile이 없습니다. Profile:$CURRENT_PROFILE"
  echo "> 8081을 할당합니다."
  IDLE_PORT=8081
fi

echo "> 전환할 Port : $IDLE_PORT"
echo "> Port 전환"
echo "set \$service_url http://j8e207.p.ssafy.io:${IDLE_PORT};" | sudo tee /home/ubuntu/dev/conf.d/service-url.inc

echo "> Nginx Reload"
docker exec front service nginx reload