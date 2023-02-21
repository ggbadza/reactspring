package com.tankmilu.spring.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tankmilu.spring.entity.User;
import com.tankmilu.spring.enums.UserRole;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails{

    private final User user;
    private final int uid;
    private final String email;
    private final String password;
    private boolean emailVerified;  //이메일 인증 (1이면 인증됨)
    private boolean locked;         //잠김 여부 (1이면 잠김)


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override  
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = user.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override 
    public boolean isEnabled() {
        return (emailVerified && !locked); // 이메일 인증, 잠김 여부가 모두 1
    }

    
}
