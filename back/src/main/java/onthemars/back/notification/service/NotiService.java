package onthemars.back.notification.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.common.config.RabbitmqConfig;
import onthemars.back.common.security.SecurityUtils;
import onthemars.back.firebase.FirebaseMessageService;
import onthemars.back.notification.domain.FcmToken;
import onthemars.back.notification.dto.request.NotiRequestDto;
import onthemars.back.notification.dto.response.AlarmListResponseDto;
import onthemars.back.notification.repository.FcmTokenRepository;
import onthemars.back.notification.repository.NotiRedisRepository;
import onthemars.back.notification.repository.NotiRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
@Transactional
@RequiredArgsConstructor
public class NotiService {
    private final RabbitTemplate rabbitTemplate;
    private final FirebaseMessageService messageService;
    private final FcmTokenRepository fcmTokenRepository;
    private final NotiRepository notiRepository;
    private final NotiRedisRepository notiRedisRepository;

    public void sendMessage(String address, String title, String content){
        NotiRequestDto requestDto = new NotiRequestDto(address, title, content);
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

    public AlarmListResponseDto findUserAlramList(Pageable pageable) {
        // redis 에서 조회
        
        // redis 에서 부족한거 db에서 조회
        
        // 최종 조회된 목록 반환

        return null;
    }

    public void registerToken(String token) {
        String address = SecurityUtils.getCurrentUserId();
        FcmToken fcmToken = new FcmToken(address, token);
        fcmTokenRepository.save(fcmToken);
    }

    public void readAlram(Long alarmId){
        notiRepository.findById(alarmId).ifPresent(notification -> {
            notification.verify();
        });
    }
    public void removeAlram(Long alarmId) {
        notiRepository.findById(alarmId).ifPresent(notification -> {
            notification.delete();
            notiRedisRepository.deleteById(alarmId);
        });
    }
}
