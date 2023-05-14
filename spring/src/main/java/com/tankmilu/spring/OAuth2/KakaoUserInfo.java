package com.tankmilu.spring.OAuth2;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes; 
    private Map<String, Object> kakao_account; 
    private Map<String, Object> profile; 

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        this.profile = (Map<String, Object>) kakao_account.get("profile");
        
    }
    
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "Kakao";
    }

    @Override
    public String getEmail() {
        return kakao_account.get("email").toString();
    }

    @Override
    public String getName() {
        return profile.get("nickname").toString();
    }
}