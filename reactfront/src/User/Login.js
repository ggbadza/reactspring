import {Button,Form} from 'react-bootstrap';
import axios from 'axios';
import React, {useState} from 'react';



function Login() {
  const [emailId, setEmailId] = useState("")
  const [passwordId, setPasswordId] = useState("")

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
        <Button variant="success" href="/oauthlogin?type=naver">네이버 로그인</Button>
        <Button variant="warning" href="/oauthlogin?type=kakao">카카오 로그인</Button>
        </div>
      </Form>
    );
  }
  
  export default Login;