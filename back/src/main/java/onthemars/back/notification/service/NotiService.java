package onthemars.back.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.RpcClient.Response;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.common.config.RabbitmqConfig;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.notification.app.Message;
import onthemars.back.notification.domain.Notification;
import onthemars.back.notification.domain.NotificationRedis;
import onthemars.back.notification.dto.request.NotiRequestDto;
import onthemars.back.notification.dto.response.AlarmListResponseDto;
import onthemars.back.notification.repository.NotiRedisRepository;
import onthemars.back.notification.repository.NotiRepository;
import onthemars.back.user.domain.Member;
import onthemars.back.user.repository.MemberRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
@Transactional
@RequiredArgsConstructor
public class NotiService {

    private final RabbitTemplate rabbitTemplate;

    private final MemberRepository memberRepository;
    private final NotiRepository notiRepository;
    private final NotiRedisRepository notiRedisRepository;

    public void sendMessage(String address, String title, String content){
        NotiRequestDto requestDto = new NotiRequestDto(address, title, content);
        rabbitTemplate.convertAndSend(RabbitmqConfig.USER_EXCHANGE_NAME, RabbitmqConfig.USER_ROUTING_KEY, requestDto);
    }

    @RabbitListener(queues = RabbitmqConfig.USER_QUEUE_NAME)
    public void consume(NotiRequestDto requestDto){
        log.info("Rabbit MQ!! Consume Message : {}", requestDto.toString());

        // redis에 저장
        NotificationRedis nr = NotificationRedis.create(requestDto, 86400L);
        notiRedisRepository.save(nr);

        // db에 저장
        Member member = memberRepository.findById(requestDto.getAddress())
            .orElseThrow(UserNotFoundException::new);

        Notification notification = Notification.of(requestDto, member);
        notiRepository.save(notification);

        // fcm호출

    }

    public AlarmListResponseDto findUserAlramList(Pageable pageable) {
        // redis 에서 조회
        
        // redis 에서 부족한거 db에서 조회
        
        // 최종 조회된 목록 반환

        return null;
    }
}
