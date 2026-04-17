package com.suncontrol.core.vo;

import com.suncontrol.core.entity.Member;
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

    public MemberDetailVo(Member member) {
        super(member);
        if (member == null) return;
        this.userId = member.getUserId();
        this.role = member.getRole();
        this.affiliation = member.getAffiliation();
        this.status = member.getStatus();
    }
}