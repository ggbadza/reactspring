import React, {useEffect, useState} from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import axios from 'axios';
import NavBar from './NavBar';

import Login from './User/Login';

function App() {
   const [hello, setHello] = useState('')

    useEffect(() => {
        axios.get('/api/user/test')
        .then(response => setHello(response.data))
        .catch(error => console.log(error))
    }, []);

    return (
      <BrowserRouter>
      <NavBar />
          <div>
            <Routes>
            <Route path="/login" element={<Login />} />
            </Routes>
          </div>
        </BrowserRouter>
    );
}

export default App;