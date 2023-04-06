package onthemars.back.notification.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.common.config.RabbitmqConfig;
import onthemars.back.common.security.SecurityUtils;
import onthemars.back.firebase.FirebaseMessageService;
import onthemars.back.firebase.FcmToken;
import onthemars.back.notification.app.NotiTitle;
import onthemars.back.notification.domain.Notification;
import onthemars.back.notification.domain.NotificationRedis;
import onthemars.back.notification.dto.request.NotiRequestDto;
import onthemars.back.notification.dto.response.AlarmListResponseDto;
import onthemars.back.notification.repository.FcmTokenRepository;
import onthemars.back.notification.repository.NotiRedisRepository;
import onthemars.back.notification.repository.NotiRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
@Transactional
@RequiredArgsConstructor
public class NotiService {
    private final RabbitTemplate rabbitTemplate;
    private final FirebaseMessageService messageService;
    private final FcmTokenRepository fcmTokenRepository;
    private final NotiRepository notiRepository;
    private final NotiRedisRepository notiRedisRepository;

    public void sendMessage(String address, NotiTitle title, String content){
        NotiRequestDto requestDto = new NotiRequestDto(address, title, content, 86400L);
        rabbitTemplate.convertAndSend(RabbitmqConfig.USER_EXCHANGE_NAME, RabbitmqConfig.USER_ROUTING_KEY, requestDto);
    }

    @RabbitListener(queues = RabbitmqConfig.USER_QUEUE_NAME)
    public void consume(NotiRequestDto requestDto) {
        log.info("Rabbit MQ!! Consume Message : {}", requestDto.toString());

        String address = requestDto.getAddress();
        fcmTokenRepository.findByAddress(address).ifPresent(fcmToken -> {
            try {
                messageService.sendMessageTo(fcmToken.getToken(), requestDto);
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public AlarmListResponseDto findUserAlarmList(Pageable pageable) {
        String address = SecurityUtils.getCurrentUserId();
        // redis 에서 조회
        Page<NotificationRedis> redisPages = notiRedisRepository.findAllByAddress(address, pageable);

        int redisSize = redisPages.getNumberOfElements();
        int pageSize = pageable.getPageSize();
        if(redisSize < pageSize){
            // redis 에서 부족한거 db에서 조회
            Page<Notification> jpaPages = notiRepository.findAllByMemberAddressAndDeletedIsFalse(address, pageable);
            if(jpaPages.getNumberOfElements() > redisSize){
                // 가져온만큼 redis에 저장
                List<Notification> jpaList = jpaPages.stream().collect(Collectors.toList());
                for(int i = redisSize; i < jpaList.size(); i++){
                    Notification notification = jpaList.get(i);
                    NotificationRedis nr = notification.toRedisEntity();
                    notiRedisRepository.save(nr);
                }
                redisPages = notiRedisRepository.findAllByAddress(address, pageable);
            }
        }
        return AlarmListResponseDto.toDtoWithRedisPages(redisPages);
    }

    public void registerToken(String token) {
        String address = SecurityUtils.getCurrentUserId();
        fcmTokenRepository.findByAddress(address).ifPresentOrElse(fcmToken -> {
            log.info("이미 존재하는 회원입니다. 토큰 업데이트! => {}", token);
            fcmToken.updateToken(token);
            FcmToken save = fcmTokenRepository.save(fcmToken);
            log.info("{} ", save.getToken());
        }, () -> {
            log.info("새롭게 토큰 추가!! => {}", token);
            FcmToken fcmToken = new FcmToken(address, token);
            FcmToken save = fcmTokenRepository.save(fcmToken);
            log.info("{} ", save.getToken());
        });
    }

    public void readAlarm(Long alarmId){
        notiRepository.findById(alarmId).ifPresent(notification -> {
            notification.verify();
            notiRedisRepository.findById(alarmId).ifPresent(nr -> {
                nr.verify();
                notiRedisRepository.save(nr);
            });
        });
    }

    public void removeAlarm(Long alarmId) {
        notiRepository.findById(alarmId).ifPresent(notification -> {
            notification.delete();
            notiRedisRepository.deleteById(alarmId);
        });
    }
}
