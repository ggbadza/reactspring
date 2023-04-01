import {Button,Form} from 'react-bootstrap';
import axios from 'axios';
import React, {useState} from 'react';

function SettingUser() {
  const [passwordId, setPasswordId] = useState("")
  const [nickName, setNickName] = useState("")
  const [phone, setPhone] = useState("")

  const REST_API_KEY_KAKAO = "e251174f9e5b682a58df8ae695d66ef7";
  const REDIRECT_URI_KAKAO =  "http://localhost:3000/registerkakao";
  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY_KAKAO}&redirect_uri=${REDIRECT_URI_KAKAO}&response_type=code`


    return (
      <Form>
        <div>
        <Button variant="success" href="">네이버 연동</Button>
        <Button variant="warning" href={KAKAO_AUTH_URL}>카카오 연동</Button>
        </div>
      </Form>
    );
  }
  
  export default SettingUser;