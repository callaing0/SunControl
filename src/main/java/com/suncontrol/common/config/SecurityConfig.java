package com.suncontrol.common.config;

import com.suncontrol.common.security.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;

    // 비밀번호 암호화 Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // 로그인 페이지 및 정적 리소스는 누구나 접근 가능
                        .requestMatchers("/auth/login", "/css/**", "/js/**", "/images/**").permitAll()
                        // 관리자 페이지는 ADMIN 권한만 접근 가능
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        // 그 외 요청은 로그인 필요
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        // 사용자 정의 로그인 페이지
                        .loginPage("/auth/login")
                        // 로그인 form action 경로
                        .loginProcessingUrl("/login")
                        // 로그인 성공 후 사용자의 상태에 따라 이동 경로 분기
                        .successHandler(loginSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        // 로그아웃 요청 URL
                        .logoutUrl("/logout")
                        // 로그아웃 성공 후 이동 페이지
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}