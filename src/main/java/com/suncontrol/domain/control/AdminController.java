package com.suncontrol.domain.control;

import com.suncontrol.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    // 관리자 페이지 화면 반환
    @GetMapping({"", "/"})
    public String showAdminPage(Model model) {
        model.addAttribute("userList", memberService.findAll());
        return "admin";
    }
    }