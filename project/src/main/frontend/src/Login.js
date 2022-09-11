import colorPalette from "./index";
import 'bootstrap/dist/css/bootstrap.css';
import {Button, Form} from "react-bootstrap";
import './colors.scss'
import {useState} from "react";
import axios from "axios";

export default function Login() {
    const [email, setEmail] = useState();

    function logIn() {
        if(email===""){
            console.log('bzvz')
        }
        else if(!email.toString().includes("@gmail.com")) {
            console.log('bzvz')
        }
        else{
            axios.post("http://localhost:8080/user/login", email,
                {
                    headers: {
                        'Content-Type': 'text/plain'
                    }
                }).then(value => {
                console.log(value.data)
                //TODO postavi toast
            }).catch(reason => {
                console.log(reason.response.data)
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
                                          onInput={event => setEmail(event.target.value)} />
                        </Form.Group>
                    </Form>
                    <div className="d-flex justify-content-center mt-1">
                        <Button variant={"outline-lightest"} className={"w-50"} onClick={logIn}>Log in</Button>
                    </div>
                    <div className="d-inline mb-auto">
                    </div>
                </div>
            </div>
        </div>
    )
}