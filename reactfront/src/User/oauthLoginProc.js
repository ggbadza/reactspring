import { useEffect } from "react";
import axios from 'axios';

function OauthLoginProc() {
    const PARAMS = new URL(document.location).searchParams;
    const CODE = PARAMS.get('code');
    const TYPE = PARAMS.get('type');

    useEffect(() => {
        axios.post(`/api/user/oauthlogin?code=${CODE}&type=${TYPE}`)
        .then(response => {
            document.location.href = '/';
          })
          .catch((error) => {
            console.log(error);
            window.alert(error.response.data["message"]);
          });
    })

    return <></>;
}
  
export default OauthLoginProc;