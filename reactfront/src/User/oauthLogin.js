import {Button,Form} from 'react-bootstrap';
import axios from 'axios';
import React, {useEffect, useState} from 'react';



function OauthLogin() {
    const PARAMS = new URL(document.location).searchParams;
    const type = PARAMS.get('type');

    const REST_API_KEY_KAKAO = process.env.REDIRECT_URI_KAKAO;
    const REDIRECT_URI_KAKAO =  "http://localhost:3000/oauthloginproc?type=kakao";
    const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY_KAKAO}&redirect_uri=${REDIRECT_URI_KAKAO}&response_type=code`
  
    const REST_API_KEY_NAVER = process.env.REST_API_KEY_NAVER;
    const REDIRECT_URI_NAVER =  "http://localhost:3000/oauthloginproc?type=naver";
    const STATE_NAVER = "test"
    const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${REST_API_KEY_NAVER}&redirect_uri=${REDIRECT_URI_NAVER}&state=${STATE_NAVER}`
    
    useEffect(() => {
        if(type=="kakao"){
            document.location.href = KAKAO_AUTH_URL;
        } else if(type=="naver"){
            document.location.href = NAVER_AUTH_URL;
        } else {
            window.alert("존재하지 않는 타입입니다.");
        }
        
    })


    return <></>;
    }
    
    export default OauthLogin;