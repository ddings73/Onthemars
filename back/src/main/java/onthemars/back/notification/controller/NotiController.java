package onthemars.back.notification.controller;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import onthemars.back.notification.app.Message;
import onthemars.back.notification.domain.Notification;
import onthemars.back.notification.dto.response.AlarmListResponseDto;
import onthemars.back.notification.service.NotiService;
import onthemars.back.user.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class NotiController {

    private final NotiService notiService;

    @GetMapping
    public ResponseEntity findAlramList(@PageableDefault Pageable pageable){
        AlarmListResponseDto responseDto = notiService.findUserAlramList(pageable);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{alarmId}")
    public ResponseEntity removeAlarm(@PathVariable Long alarmId){
        return ResponseEntity.ok().build();
    }
}
