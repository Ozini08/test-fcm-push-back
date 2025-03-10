package com.test_fcm_push_back.fcm.controller;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.test_fcm_push_back.fcm.service.FCMService;
import com.test_fcm_push_back.fcm.entity.FcmToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm")
public class FCMController {

    private final FCMService fcmService;

    @PostMapping("/send")
    public Map<String, String> sendTestMessage(@RequestBody Map<String, String> request) throws FirebaseMessagingException {
        String title = request.get("title");
        String message = request.get("message");
        Map<String, String> response = new HashMap<>();

        List<HashMap<String, String>> memberList = fcmService.fcmEnableList();

        if (memberList.isEmpty()) {
            response.put("message", "저장된 회원이 없습니다.");
            return response;
        }

        title = (title != null && !title.isEmpty()) ? title : "기본 제목";
        message = (message != null && !message.isEmpty()) ? message : "기본 메시지 내용";

        for (HashMap<String, String> member : memberList) {
            String fcmToken = member.get("FCM_TOKEN");
            if (fcmToken != null && !fcmToken.isEmpty()) {
                fcmService.sendMessage(fcmToken, title, message);
            }
        }

        response.put("message", "푸시 메시지가 전송되었습니다.");
        return response;
    }



    @GetMapping("/status")
    public ResponseEntity<?> getNotificationStatus() {
        FcmToken token = fcmService.findByMemberNo(15);

        if (token == null) {
            return ResponseEntity.ok(Collections.singletonMap("enabled", false));
        }

        boolean enabled = token.isEnabled();
        return ResponseEntity.ok(Collections.singletonMap("enabled", enabled));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String memberNoStr = body.get("memberNo");
        Integer memberNo = Integer.parseInt(memberNoStr);
        FcmToken fcmToken = new FcmToken();
        fcmToken.setMemberNo(memberNo);
        fcmToken.setFcmToken(token);
        fcmToken.setEnabled(true);
        /*
            테이블에 기존 멤버가 있지 않을 때
         */
        if(fcmService.findByMemberNo(fcmToken.getMemberNo())==null){
            fcmService.saveToken(fcmToken);
        }else {
        /*
            테이블에 기존 멤버가 있을 때
         */
            fcmService.updateToken(fcmToken);
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "토큰 저장 완료"));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteToken(
            @RequestBody Map<String, Integer> body
    ) {
        Integer memberNo = body.get("memberNo");
        fcmService.deleteByMemberNo(memberNo);
        return ResponseEntity.ok(Collections.singletonMap("message", "토큰 삭제 완료"));
    }

    @GetMapping("/list")
    public ResponseEntity<?> memberListToken() {
        try {
            List<HashMap<String, String>> memberList = fcmService.fcmEnableList();

            if (memberList == null || memberList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("데이터 없음");
            }

            return ResponseEntity.ok(memberList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

}
