package com.suncontrol.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Member {


    private Long id; // 회원 식별
    private String userId; // 로그인 계정 아이디
    private String password; // 암호화된 비밀번호
    private String role; // 회원 권한 (USER / ADMIN)
    private String name; // 사용자 이름
    private String affiliation; // 소속
    private boolean isTemporary; // 임시 비밀번호 여부
    private String status; // 계정 상태
    private LocalDateTime createdAt; // 등록일
    private LocalDateTime updatedAt; // 수정일
}
