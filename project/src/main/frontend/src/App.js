import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Login from "./Login";
import UserProfile from "./UserProfile";

function App() {
    return (
        <Login></Login>
        // <BrowserRouter>
        //     <Routes>
        //         <Route path="/" element={<Login/>}></Route>
        //         <Route path="/user/:email" element={<UserProfile/>}></Route>
        //     </Routes>
        // </BrowserRouter>
    );
}

export default App;
