import { useEffect } from "react";
import axios from 'axios';

function RegisterOauth() {
    const PARAMS = new URL(document.location).searchParams;
    const CODE = PARAMS.get('code');
    const TYPE = PARAMS.get('type');

    useEffect(() => {
        axios.put(`http://localhost:8080/api/user/registeroauth?code=${CODE}&type=${TYPE}`)
        .then(response => {
            alert(`${TYPE} 계정 연동이 완료되었습니다.`)
            document.location.href = '/';
          })
          .catch((error) => {
            console.log(error);
            window.alert(error.response.data["message"]);
          });
    })

    return <></>;
}
  
export default RegisterOauth;

