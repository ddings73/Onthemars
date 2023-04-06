package onthemars.back.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.notification.app.NotiTitle;
import onthemars.back.notification.domain.NotificationRedis;
import onthemars.back.notification.dto.request.NotiRequestDto;
import onthemars.back.notification.repository.NotiRedisRepository;
import onthemars.back.notification.repository.NotiRepository;
import onthemars.back.user.domain.Member;
import onthemars.back.user.repository.MemberRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service @Slf4j
@RequiredArgsConstructor
public class FirebaseMessageService {

    private final MemberRepository memberRepository;
    private final NotiRepository notiRepository;
    private final NotiRedisRepository notiRedisRepository;

    @PostConstruct
    private void init() {
        ClassPathResource resource = new ClassPathResource("firebase_service_key.json");
        try (InputStream stream = resource.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialization complete");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMessageTo(String targetToken, NotiRequestDto requestDto)
        throws FirebaseMessagingException {
        log.info("Send Message To FCM => {}", targetToken);

        NotiTitle title = requestDto.getTitle();
        String body = requestDto.getContent();

        Notification notification = new Notification(title.name(), body);
        Message message = Message.builder()
            .setNotification(notification)
            .setToken(targetToken)
            .build();

        String response = FirebaseMessaging.getInstance().send(message);
        log.info("Message send => {}", response);

        Long notiId = saveInJpa(requestDto);

        NotificationRedis nr = NotificationRedis.createWithDto(notiId, requestDto);
        saveInRedis(nr);
    }

    private Long saveInJpa(NotiRequestDto requestDto){
        // db에 저장
        log.info("Save In Jpa!!!!");
        Member member = memberRepository.findById(requestDto.getAddress())
            .orElseThrow(UserNotFoundException::new);

        return notiRepository.save(requestDto.toEntity(member, requestDto.getTitle())).getId();
    }

    public NotificationRedis saveInRedis(NotificationRedis nr){
        // redis에 저장
        log.info("Save In Redis!!!!");
        return notiRedisRepository.findByAddress(nr.getAddress()).orElse(
            notiRedisRepository.save(nr)
        );
    }
}
