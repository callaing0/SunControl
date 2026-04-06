package com.suncontrol.domain.api;

import com.suncontrol.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    // 관리자 페이지 화면 반환
    @GetMapping("/")
    public String showAdminPage() {
        return "admin"; // 관리자페이지 html 과 이름 맞는지 확인 부탁드립니다!
    }
}