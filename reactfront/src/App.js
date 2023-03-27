import React, {useEffect, useState} from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Item from './Item/item';
import NavBar from './NavBar';

import Login from './User/Login';
import RegisterUser from './User/Register';

function App() {
    return (
      <BrowserRouter>
      <NavBar />
          <div>
            <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<RegisterUser />} />
            <Route path="/item" element={<Item />} />
            </Routes>
          </div>
        </BrowserRouter>
    );
}

export default App;