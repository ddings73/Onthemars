package onthemars.back.notification.controller;

import lombok.RequiredArgsConstructor;
import onthemars.back.notification.app.NotiTitle;
import onthemars.back.notification.dto.response.AlarmListResponseDto;
import onthemars.back.notification.service.NotiService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class NotiController {

    private final NotiService notiService;

    @GetMapping
    public ResponseEntity findAlramList(@PageableDefault(direction = Sort.Direction.DESC, sort = "id") Pageable pageable) {
        AlarmListResponseDto responseDto = notiService.findUserAlarmList(pageable);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity registerMemberToken(@RequestHeader String fcmToken){
        notiService.registerToken(fcmToken);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{alarmId}")
    public ResponseEntity readAlarm(@PathVariable Long alarmId){
        notiService.readAlram(alarmId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{alarmId}")
    public ResponseEntity removeAlarm(@PathVariable Long alarmId) {
        notiService.removeAlram(alarmId);
        return ResponseEntity.ok().build();
    }
}
