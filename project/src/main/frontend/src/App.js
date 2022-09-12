import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Login from "./Login";
import UserProfile from "./user/UserProfile";
import React from 'react'

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login/>}></Route>
                <Route path="/user/:id" element={<UserProfile/>}></Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
