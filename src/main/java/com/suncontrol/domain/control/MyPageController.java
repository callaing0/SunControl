package com.suncontrol.domain.control;

import com.suncontrol.domain.form.PasswordChangeForm;
import com.suncontrol.domain.vo.MyPageVo;
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
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 마이페이지 조회
    @GetMapping("/")
    public String showMyPage(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        // 로그인 사용자 userId 가져오기
        String userId = principal.getName();

        // MyPage 데이터 생성
        MyPageVo myPageVo = myPageService.findMyPageView(userId);

        if (myPageVo == null) {
            return "redirect:/login";
        }

        // 화면 전달
        model.addAttribute("myPage", myPageVo);

        // 비밀번호 변경 폼
        model.addAttribute("passwordChangeForm", new PasswordChangeForm());

        return "mypage";
    }

    // 마이페이지 내부 비밀번호 변경
    @PostMapping("/password")
    public String changePassword(@ModelAttribute PasswordChangeForm passwordChangeForm,
                                 Principal principal) {
        myPageService.changePassword(principal.getName(), passwordChangeForm);
        return "redirect:/mypage/";
    }
}