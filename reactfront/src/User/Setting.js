import {Button,Form} from 'react-bootstrap';
import axios from 'axios';
import React, {useState} from 'react';

function SettingUser() {
  const [passwordId, setPasswordId] = useState("")
  const [nickName, setNickName] = useState("")
  const [phone, setPhone] = useState("")

  const REST_API_KEY_KAKAO = process.env.REST_API_KEY_KAKAO;
  const REDIRECT_URI_KAKAO =  "http://localhost:3000/oauthregister?type=kakao";
  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY_KAKAO}&redirect_uri=${REDIRECT_URI_KAKAO}&response_type=code`
  
  const REST_API_KEY_NAVER = process.env.REST_API_KEY_NAVER;
  const REDIRECT_URI_NAVER =  "http://localhost:3000/oauthregister?type=naver";
  const STATE_NAVER = "test"
  const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${REST_API_KEY_NAVER}&redirect_uri=${REDIRECT_URI_NAVER}&state=${STATE_NAVER}`
  

    return (
      <Form>
        <div>
        <Button variant="success" href={NAVER_AUTH_URL}>네이버 연동</Button>
        <Button variant="warning" href={KAKAO_AUTH_URL}>카카오 연동</Button>
        </div>
      </Form>
    );
  }
  
  export default SettingUser;