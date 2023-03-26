import axios from 'axios';

function setAuthorization(jwtToken) {
    if (jwtToken) {
        axios.defaults.headers.common["Authorization"] = `Bearer ${jwtToken}`;
    } else {
        delete axios.defaults.headers.common["Authorization"];
    }
}

export default setAuthorization;