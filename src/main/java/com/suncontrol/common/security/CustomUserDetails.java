package com.suncontrol.common.security;

import com.suncontrol.common.dto.AuthDto;
import com.suncontrol.core.constant.common.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final AuthDto authDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authDto.getRole().getRole()));
    }

    @Override
    public String getPassword() {
        return authDto.getPassword();
    }

    @Override
    public String getUsername() {
        return authDto.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"LOCKED".equals(authDto.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "ACTIVE".equals(authDto.getStatus());
    }

    public Long getId() {
        return authDto.getId();
    }

    public String getUserId() {
        return authDto.getUserId();
    }

    public Role getRole() {
        return authDto.getRole();
    }

    public boolean isTemporary() {
        return authDto.isTemporary();
    }

    public boolean hasPlant() {
        return authDto.isHasPlant();
    }

}
