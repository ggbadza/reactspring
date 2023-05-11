package com.tankmilu.spring.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.tankmilu.spring.OAuth2.KakaoUserInfo;
import com.tankmilu.spring.OAuth2.NaverUserInfo;
import com.tankmilu.spring.OAuth2.OAuth2UserInfo;
import com.tankmilu.spring.entity.User;
import com.tankmilu.spring.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;


@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession httpSession;

    OAuth2UserInfo oAuth2UserInfo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId(); // OAuth2 ID 받아옴

        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); //OAuth2 key 받아옴
        
        if (registrationId.equals("naver")){
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());

        } else if (registrationId.equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }
        

        String providerId = oAuth2UserInfo.getProviderId();
        String name = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();

        User user;
        if (registrationId.equals("naver")){
            user = userRepository.findByNaverId(providerId).get();

        } else if (registrationId.equals("kakao")){
            user = userRepository.findByKakaoId(providerId).get();
        }

        
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
                , oAuth2UserInfo.getAttributes()
                , userNameAttributeName);
    }

    
}