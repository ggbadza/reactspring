package com.tankmilu.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tankmilu.spring.security.JwtAuthFilter;
import com.tankmilu.spring.security.JwtUtil;
import com.tankmilu.spring.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화(url별 권한 설정)
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService; //OAuth2 커스텀

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()// Rest api 서버 CSRF 비활성화
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // SessionCreationPolicy.STATELESS
            .authorizeHttpRequests()
                .antMatchers("/api/user/**").permitAll() // 임시
                .anyRequest()
                .authenticated() // 인증 된 유저만 접근
            .and()
            .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class) // JWT 적용
            .oauth2Login() //OAuth2 로그인 필터 추가
                .defaultSuccessUrl("/")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("*")
            .maxAge(3000);
            }
    };
	


}
