package com.suncontrol.common.security;

import com.suncontrol.common.dto.AuthDto;
import com.suncontrol.core.constant.common.Role;
import com.suncontrol.core.entity.Member;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;
    private final PlantService plantService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberService.findByUserId(username);
        if (member == null) {
            throw new UsernameNotFoundException("사용자 없음");
        }

        Role role = Role.fromRole(member.getRole());
        if (role == null) {
            throw new UsernameNotFoundException("권한 정보가 올바르지 않습니다.");
        }

        boolean hasPlant = !plantService.findAllByMemberId(member.getId()).isEmpty();

        AuthDto authDto = new AuthDto();
        authDto.setId(member.getId());
        authDto.setUserId(member.getUserId());
        authDto.setPassword(member.getPassword());
        authDto.setRole(role);
        authDto.setStatus(member.getStatus());
        authDto.setTemporary(member.isTemporary());
        authDto.setHasPlant(hasPlant);

        return new CustomUserDetails(authDto);
    }
}
