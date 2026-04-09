package com.suncontrol.common.dto;

import com.suncontrol.core.constant.common.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {

    // 회원 식별
    private Long id;
    // 로그인 계정 아이디
    private String userId;
    // 암호화된 비밀번호
    private String password;
    // 회원 권한
    private Role role;
    // 회원 상태
    private String status;
    // 임시 비밀번호 여부
    private boolean isTemporary;
    // 보유 자산 여부
    private boolean hasPlant;
}
