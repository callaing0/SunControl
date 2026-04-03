package com.suncontrol.core.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDetailVo extends MemberInfoVo {

    // 로그인 계정
    private String userId;
    // 권한
    private String role;
    // 소속
    private String affiliation;
    // TODO: 상태 (마이페이지에 있는건지 확인 필요)
    private String status;
}