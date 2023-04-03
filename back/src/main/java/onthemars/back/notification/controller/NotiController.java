package onthemars.back.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@RestController @Slf4j
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class NotiController {

    private final NotiService notiService;

    @Operation(summary = "알람 목록 조회", description = "내림차순으로 조회한다", tags = {
        "noti-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "UnAuthorization"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity findAlramList(@PageableDefault(direction = Sort.Direction.DESC, sort = "id") Pageable pageable) {
        AlarmListResponseDto responseDto = notiService.findUserAlarmList(pageable);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "fcm 토큰 추가", description = "회원의 fcm토큰을 추가한다", tags = {
        "noti-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "UnAuthorization"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity registerMemberToken(@RequestHeader String fcmToken){
        log.info("FCM token => {}", fcmToken);
        notiService.registerToken(fcmToken);

        log.info("토큰 저장 완료!!");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알람 읽기", description = "alarmId에 해당하는 알람을 읽음처리한다", tags = {
        "noti-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "UnAuthorization"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{alarmId}")
    public ResponseEntity readAlarm(@PathVariable Long alarmId){
        notiService.readAlarm(alarmId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알람 삭제", description = "alarmId에 해당하는 알람을 삭제처리한다", tags = {
        "noti-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "UnAuthorization"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{alarmId}")
    public ResponseEntity removeAlarm(@PathVariable Long alarmId) {
        notiService.removeAlarm(alarmId);
        return ResponseEntity.ok().build();
    }
}
