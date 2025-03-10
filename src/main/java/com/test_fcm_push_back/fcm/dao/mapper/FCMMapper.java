package com.test_fcm_push_back.fcm.dao.mapper;

import com.test_fcm_push_back.fcm.entity.FcmToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface FCMMapper {

    List<HashMap<String, String>> fcmEnableList();

    void saveToken(
            @Param("saveToken") FcmToken fcmToken);

    void deleteByMemberNo(
            @Param("deleteToken") int memberNo);

    void updateToken(
            @Param("updateToken") FcmToken fcmToken);

    FcmToken findByMemberNo(
            @Param("findMember") Integer number);
}
