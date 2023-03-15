echo "> 현재 구동중인 profile 확인"
CURRENT_PROFILE=$(curl -X POST https://j8e207.p.ssafy.io/api/v1/profile)

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

sudo docker pull e207/back:$IDLE_TAG
docker run -d -p $IDLE_PORT:${IDLE_PORT} --name back-$IDLE_PROFILE -e Profile=$IDLE_PROFILE e207/back:$IDLE_TAG

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
sh switch.sh


sudo docker ps -a -q --filter "name=back-${CURRENT_PROFILE}" | grep -q . && docker stop back-$CURRENT_PROFILE && docker rm back-$CURRENT_PROFILE | true
sudo docker rmi e207/back:$CURRENT_TAG