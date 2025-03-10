package com.test_fcm_push_back.fcm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FcmToken {
    private Integer tokenNo;
    private Integer memberNo;
    private String fcmToken;
    private boolean enabled;
}