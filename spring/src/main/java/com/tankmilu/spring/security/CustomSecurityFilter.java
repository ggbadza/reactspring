package com.tankmilu.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomSecurityFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("email = " + email);
        System.out.println("password = " + password);
        System.out.println("request.getRequestURI() = " + request.getRequestURI());


        if(email != null && password  != null && request.getRequestURI().equals("/user/login") ){
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 비밀번호 확인
            if(!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new IllegalAccessError("비밀번호가 일치하지 않습니다.");
            }

            // 인증 객체 생성 및 등록
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request,response);
    }
}