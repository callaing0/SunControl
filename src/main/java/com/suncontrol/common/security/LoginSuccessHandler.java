package com.suncontrol.common.security;

import com.suncontrol.core.constant.common.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomUserDetails userDetails= (CustomUserDetails) authentication.getPrincipal();

        // 관리자가 로그인 시 관리자 페이지로 이동
        if(userDetails.getRole().equals(Role.ROLE_ADMIN)){
            response.sendRedirect("/admin");
            return;
        }

        // 임시 비밀번호 사용자면 비밀번호 변경 페이지로 이동
        if(userDetails.isTemporary()){
            response.sendRedirect("/auth/change-password");
            return;
        }
        // 발전소가 등록되어 있지 않으면 자산 안내 페이지로 이동
        if(!userDetails.hasPlant()){
            response.sendRedirect("/error/assets");
            return;
        }
        // 그 외 정상 사용자는 대시보드로 이동
        response.sendRedirect("/dashboard");
    }
}
