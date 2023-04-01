import { useEffect } from "react";
import axios from 'axios';
import { setCookie } from "./cookies";

function LoginKakao() {
    const PARAMS = new URL(document.location).searchParams;
    const KAKAO_CODE = PARAMS.get('code');

    useEffect(() => {
        axios.post(`http://localhost:8080/api/user/kakaologin?code=${KAKAO_CODE}`)
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
    })

    return <></>;
}
  
export default LoginKakao;