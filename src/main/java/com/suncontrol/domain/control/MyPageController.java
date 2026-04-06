package com.suncontrol.domain.api;

import com.suncontrol.product.vo.MyPageVo;
import com.suncontrol.domain.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/")
    public String showMyPage(Model model, Principal principal) {

        // 로그인 사용자 userId 가져오기
        String userId = principal.getName();

        // MyPage 데이터 생성
        MyPageVo myPageVo = myPageService.findMyPageView(userId);

        if (myPageVo == null) {
            return "redirect:/login";
        }

        // 화면 전달
        model.addAttribute("myPage", myPageVo);

        return "mypage";
    }
}