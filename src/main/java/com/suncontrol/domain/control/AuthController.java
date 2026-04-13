package com.suncontrol.domain.control;

import com.suncontrol.core.dto.asset.MainSummaryDto;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.domain.form.PasswordChangeForm;
import com.suncontrol.domain.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MyPageService myPageService;
    private final PlantService plantService;

    // 로그인 페이지 조회
    @GetMapping("/login")
    public String loginPage(Model model){
        MainSummaryDto mainSummaryDto = plantService.getMainSummary();
        model.addAttribute("summary", mainSummaryDto);
        return "auth/login";
    }

    // 최초 로그인 비밀번호 변경 페이지 조회
    @GetMapping("/change-password")
    public String changePasswordPage(Model model){
        model.addAttribute("passwordChangeForm", new PasswordChangeForm());
        return "auth/change-password";
    }

    // 최초 로그인 비밀번호 변경 처리
    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("passwordChangeForm") PasswordChangeForm passwordChangeForm,
                                 Principal principal,
                                 Model model){
        String result = myPageService.changePassword(principal.getName(), passwordChangeForm);

        // 비밀번호 변경 성공시 -> 자산 등록 안내 페이지 , 실패시 -> 비밀번호 변경 페이지
        if ("success".equals(result)) {
            return "redirect:/registration";
        } else {
            model.addAttribute("errorMessage", result);
            model.addAttribute("passwordChangeForm", passwordChangeForm);
            return "auth/change-password";
        }
    }
}
