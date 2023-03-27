#!/bin/bash

echo "> 현재 구동중인 profile 확인"
CURRENT_PROFILE=$(curl -X POST https://j8e207.p.ssafy.io/api/v1/profile)

echo "> $CURRENT_PROFILE"

if [ $CURRENT_PROFILE == v1 ]
then
        IDLE_PROFILE=v2
        IDLE_PORT=8082
				IDLE_TAG=2.0
				CURRENT_TAG=1.0
elif [ $CURRENT_PROFILE == v2 ]
then
        IDLE_PROFILE=v1
        IDLE_PORT=8081
				IDLE_TAG=1.0
				CURRENT_TAG=2.0
else
        echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
        IDLE_PROFILE=v1
        IDLE_PORT=8081
				IDLE_TAG=1.0
				CURRENT_TAG=2.0
fi



sudo docker ps -a -q --filter "name=back-${IDLE_PROFILE}" | grep -q . && docker stop back-$IDLE_PROFILE && docker rm back-$IDLE_PROFILE | true
sudo docker rmi e207/back:$IDLE_TAG
sudo docker pull e207/back:$IDLE_TAG
docker run -d -p $IDLE_PORT:${IDLE_PORT} --name back-$IDLE_PROFILE -e Profile=dev,$IDLE_PROFILE e207/back:$IDLE_TAG

# 정상구동 확인

sleep 10

for retry_count in {1..10}
do
  response=$(curl -X GET https://j8e207.p.ssafy.io/api/v1/actuator/health)
  up_count=$(echo $response | grep 'UP' | wc -l)

  if [ $up_count -ge 1 ]
  then # $up_count >= 1 ("UP" 문자열이 있는지 검증)
      echo "> Health check 성공"
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
      echo "> Health check: ${response}"
  fi

  if [ $retry_count -eq 10 ]
  then
    echo "> Health check 실패. "
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done


# 정상구동 성공, nginx 포트변경
echo "> 전환할 Port : $IDLE_PORT"
echo "> Port 전환"
echo "set \$service_url http://j8e207.p.ssafy.io:${IDLE_PORT};" | sudo tee /home/ubuntu/dev/conf.d/service-url.inc

echo "> Nginx Reload"
docker exec nginx service nginx reload