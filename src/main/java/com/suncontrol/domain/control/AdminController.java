package com.suncontrol.domain.control;

import com.suncontrol.core.service.asset.PlantService;
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
    private final PlantService plantService;

    // 관리자 페이지 화면 반환
    @GetMapping({"", "/"})
    public String showAdminPage(Model model) {
        model.addAttribute("userList", memberService.findAll());
        //관리자 페이지 발전 수동 데이터 발전소 선택을 위한 추가 코드
        model.addAttribute("plantList", plantService.findAllActive());
        return "admin";
    }
    }