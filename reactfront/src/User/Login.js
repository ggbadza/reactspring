import {Button,Form} from 'react-bootstrap';
import axios from 'axios';
import React, {useState} from 'react';
import setAuthorization from './SetAuthorization';
import { setCookie, getCookie, removeCookie } from './cookies';



function Login() {
  const [emailId, setEmailId] = useState("")
  const [passwordId, setPasswordId] = useState("")

  const REST_API_KEY_KAKAO = "e251174f9e5b682a58df8ae695d66ef7";
  const REDIRECT_URI_KAKAO =  "http://localhost:3000/loginkakao";
  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY_KAKAO}&redirect_uri=${REDIRECT_URI_KAKAO}&response_type=code`

    const onClickLogin = async () => {
      if (emailId==="" ||  passwordId==="") {
      window.alert("아이디와 비밀번호가 입력되어야 합니다.");
      return;
      }
      console.log("Email : ", emailId);
      console.log("PW : ", passwordId);
      await axios.post("/api/user/login", {
          email: emailId,
          password: passwordId,
        })
        .then(response => {
          let jwtToken = response.headers.get("Authorization");
          if(jwtToken.substr(0,6)==="Bearer"){ 
            setCookie("Authorization",jwtToken.substr(7)); //토큰 값 쿠키에 저장
          }
          document.location.href = '/';
        })
        .catch((error) => {
          console.log(error);
          window.alert(error.response.data["message"]);
        });
    };


    return (
      <Form>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>아이디</Form.Label>
          <Form.Control type="email" value={emailId} onChange={({ target: { value } }) => setEmailId(value)} placeholder="Enter email" />
          
        </Form.Group>
  
        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>비밀번호</Form.Label>
          <Form.Control type="password" value={passwordId} onChange={({ target: { value } }) => setPasswordId(value)} placeholder="Password" />
        </Form.Group>
        <div>
        <Button variant="primary" type="submit" onClick={onClickLogin}>
          로그인
        </Button>
        </div>
        <div>
        <Button variant="success" href="">네이버 로그인</Button>
        <Button variant="warning" href={KAKAO_AUTH_URL}>카카오 로그인</Button>
        </div>
      </Form>
    );
  }
  
  export default Login;