package com.suncontrol.domain.service;

import com.suncontrol.core.vo.MemberDetailVo;
import com.suncontrol.product.vo.MyPageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberService memberService;

    public MyPageVo findMyPageView(String userId) {
        // MyPageVo 생성
        MyPageVo myPageVo = new MyPageVo();

        MemberDetailVo memberDetail = new MemberDetailVo(memberService.findByUserId(userId));

        if (memberDetail.getId() == null) {
            return null;
        }

        myPageVo.setMember(memberDetail);
        // TODO : 마이페이지 다른 부분도 조립

        return myPageVo;
    }
}