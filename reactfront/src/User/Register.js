import {Button,Form} from 'react-bootstrap';
import axios from 'axios';
import React, {useState} from 'react';

function RegisterUser() {
  const [emailId, setEmailId] = useState("")
  const [passwordId, setPasswordId] = useState("")
  const [nickName, setNickName] = useState("")
  const [phone, setPhone] = useState("")

    const onClickLogin = () => {
      if (emailId==="" ||  passwordId==="") {
      window.alert("아이디와 비밀번호가 입력되어야 합니다.");
      return;
      }
      axios.post("/api/user/signup", {
          email: emailId,
          password: passwordId,
          nickname: nickName,
          phone: phone
        })
        .then(response => {
          alert(`환영합니다 ${emailId}님, 가입이 완료되었습니다!`)
          document.location.href = '/';
          return response;
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

        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>닉네임</Form.Label>
          <Form.Control type="nickname" value={nickName} onChange={({ target: { value } }) => setNickName(value)} placeholder="Enter nickname" />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>휴대폰</Form.Label>
          <Form.Control type="phone" value={phone} onChange={({ target: { value } }) => setPhone(value)} placeholder="Enter phone" />
        </Form.Group>

        <Button variant="primary" type="submit" onClick={onClickLogin}>
          로그인
        </Button>
      </Form>
    );
  }
  
  export default RegisterUser;