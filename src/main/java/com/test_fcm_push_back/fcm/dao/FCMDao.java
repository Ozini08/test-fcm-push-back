package com.test_fcm_push_back.fcm.dao;


import com.test_fcm_push_back.fcm.dao.mapper.FCMMapper;
import com.test_fcm_push_back.fcm.entity.FcmToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FCMDao {
    private final FCMMapper fcmMapper;
    public List<HashMap<String, String>> fcmEnableList() {
        return fcmMapper.fcmEnableList();
    }

    public void saveToken(FcmToken fcmToken) {
        fcmMapper.saveToken(fcmToken);
    }

    public void deleteByMemberNo(int memberNo) {
        fcmMapper.deleteByMemberNo(memberNo);
    }

    public void updateToken(FcmToken fcmToken) {
        fcmMapper.updateToken(fcmToken);
    }

    public FcmToken findByMemberNo(Integer number) {
        return fcmMapper.findByMemberNo(number);
    }
}
