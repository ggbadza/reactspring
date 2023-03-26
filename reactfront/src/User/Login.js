import {Button,Form} from 'react-bootstrap';
import axios from 'axios';
import React, {useState} from 'react';
import setAuthorization from './SetAuthorization';
import { setCookie, getCookie, removeCookie } from './cookies';

function Login() {
  const [emailId, setEmailId] = useState("")
  const [passwordId, setPasswordId] = useState("")

    const onClickLogin = () => {
      if (emailId==="" ||  passwordId==="") {
      window.alert("아이디와 비밀번호가 입력되어야 합니다.");
      return;
      }
      console.log("Email : ", emailId);
      console.log("PW : ", passwordId);
      axios.post("/api/user/login", {
          email: emailId,
          password: passwordId,
        },
        { withCredentials: true })
        .then(response => {
          let jwtToken = response.headers.get("Authorization");
          if(jwtToken.substr(0,6)==="Bearer"){ 
            jwtToken=jwtToken.substr(7)
          }
          console.log("Authorization : ", jwtToken); 
          setCookie("Authorization",jwtToken); //토큰 값 쿠키에 저장
          setAuthorization(jwtToken);
          document.location.href = '/';
          return response;
        })
        .catch((error) => {
          console.log(error);
          window.alert(error.response);
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
        <Button variant="primary" type="submit" onClick={onClickLogin}>
          로그인
        </Button>
      </Form>
    );
  }
  
  export default Login;