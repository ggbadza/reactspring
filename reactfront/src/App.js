import React, {useEffect, useState} from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Item from './Item/item';
import NavBar from './NavBar';

import Login from './User/Login';
import RegisterUser from './User/Register';
import SettingUser from './User/Setting';
import RegisterKakao from './User/RegisterKakao';
import LoginKakao from './User/LoginKakao';

function App() {
    return (
      <BrowserRouter>
      <NavBar />
          <div>
            <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<RegisterUser />} />
            <Route path="/item" element={<Item />} />
            <Route path="/setting" element={<SettingUser />} />
            <Route path="/registerkakao" element={<RegisterKakao />} />
            <Route path="/loginkakao" element={<LoginKakao />} />
            </Routes>
          </div>
        </BrowserRouter>
    );
}

export default App;