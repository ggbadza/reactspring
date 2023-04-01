import { useEffect } from "react";
import axios from 'axios';

function RegisterKakao() {
    const PARAMS = new URL(document.location).searchParams;
    const KAKAO_CODE = PARAMS.get('code');

    useEffect(() => {
        axios.put(`http://localhost:8080/api/user/registerkakao?code=${KAKAO_CODE}`)
        .then(response => {
            alert(`카카오 계정 연동이 완료되었습니다.`)
            document.location.href = '/';
          })
          .catch((error) => {
            console.log(error);
            window.alert(error.response.data["message"]);
          });
    })

    return <></>;
}
  
export default RegisterKakao;

