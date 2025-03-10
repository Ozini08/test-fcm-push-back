package com.test_fcm_push_back.fcm.service;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.test_fcm_push_back.fcm.dao.FCMDao;
import com.test_fcm_push_back.fcm.entity.FcmToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FCMService {
    private final FCMDao fcmDao;
    public void sendMessage(String targetToken, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        FirebaseMessaging.getInstance().send(message);
    }

    public FcmToken findByMemberNo(Integer number) {
        return fcmDao.findByMemberNo(number);
    }

    @Transactional
    public void saveToken(FcmToken fcmToken) {
        fcmDao.saveToken(fcmToken);
    }

    @Transactional
    public void deleteByMemberNo(int memberNo) {
        fcmDao.deleteByMemberNo(memberNo);
    }

    public List<HashMap<String, String>> fcmEnableList() {
        return fcmDao.fcmEnableList();
    }

    public void updateToken(FcmToken fcmToken) {
        fcmDao.updateToken(fcmToken);
    }
}
