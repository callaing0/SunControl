package com.suncontrol.core.vo;

import com.suncontrol.core.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoVo {

    // 회원 식별자
    private Long id;
    // 사용자 이름
    private String name;

    public MemberInfoVo(Member member) {
        if (member == null) return;
        this.id = member.getId();
        this.name = member.getName();
    }
}