import colorPalette, {showError, showInfo, showSuccess} from "./utils.js";
import 'bootstrap/dist/css/bootstrap.css';
import {Button, Form} from "react-bootstrap";
import './colors.scss'
import React, {useState} from "react";
import axios from "axios";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'

export default function Login() {
    const [email, setEmail] = useState("");

    function logIn() {
        if (email === "") {
            showInfo("Email can't be blank")
        } else if (!email.includes("@gmail.com")) {
            showInfo("Email must include @gmail.com")
        } else {
            axios.post("http://localhost:8080/user/login", email,
                {
                    headers: {
                        'Content-Type': 'text/plain'
                    }
                }).then(value => {
                showSuccess(value.data.message)
                localStorage.setItem("token", value.data.token)
                setTimeout(function () {
                    window.location.href = "http://localhost:3000/user/" + value.data.userId
                }, 2000)
            }).catch(reason => {
                console.log(reason.response.data)
                showError(reason.response.data.message)
            })
        }
    }

    return (
        <div className="d-flex flex-row vh-100" style={{backgroundColor: colorPalette.lightest}}>
            <div className="d-flex justify-content-center w-75 m-5 rounded rounded-3">
                <img className="img-fluid rounded rounded-3" src="/images/loginImage.jpg" alt={"login"}/>
            </div>
            <div className="w-25 m-5 rounded rounded-3 h-50" style={{backgroundColor: colorPalette.darkest}}>
                <div className="d-flex flex-column m-3">
                    <h1 className="title text-white ">Dental clinic</h1>
                    <Form className="mt-5">
                        <Form.Group className="my-3 mx-1" controlId="formBasicEmail">
                            <Form.Label className="text-white fs-4">Email address</Form.Label>
                            <Form.Control type="email" placeholder="Enter email"
                                          onInput={event => setEmail(event.target.value)}/>
                        </Form.Group>
                    </Form>
                    <div className="d-flex justify-content-center mt-1">
                        <Button variant={"outline-lightest"} className={"w-50"} onClick={logIn}>Log in</Button>
                    </div>
                    <div className="d-inline mb-auto">
                    </div>
                </div>
            </div>
            <ToastContainer
                position="bottom-center"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme={"colored"}
            />
        </div>
    )
}