import React, {useEffect, useState} from 'react'
import colorPalette from "../utils";
import {useParams} from "react-router-dom";
import {Button, FormText} from "react-bootstrap";
import {FiLogOut} from "react-icons/fi";
import axios from "axios";

export default function ProfileInfo() {
    const {id} = useParams()
    const [user, setUser] = useState([])


    useEffect(() => {
        axios.get("http://localhost:8080/user/data/" + id).then(value => {
            setUser(value.data)
        })
    }, [])
    const logOut = () => {
        localStorage.setItem("token", "")
        window.location.href = "http://localhost:3000/"
    }

    return (
        <div className="d-flex flex-column m-3 py-3 rounded rounded-3 w-25 "
             style={{backgroundColor: colorPalette.darkest}}>
            <div className="d-flex flex-column align-items-center">
                <img src="/images/loginImage.jpg" className="rounded-circle my-4 " width={150} height={150}/>
            </div>
            <div className="d-flex flex-column mx-4 gap-2">
                <div className="d-flex flex-row w-100">
                    <FormText className="text-white fs-5 w-50">Name</FormText>
                    <FormText className="text-white fs-5 w-50">{user.name}</FormText>
                </div>
                <div className="d-flex flex-row w-100">
                    <FormText className="text-white fs-5 w-50">Surname</FormText>
                    <FormText className="text-white fs-5 w-50">{user.surname}</FormText>
                </div>
                <div className="d-flex flex-row w-100">
                    <FormText className="text-white fs-5 w-50">Email</FormText>
                    <FormText className="text-white fs-5 w-50">{user.email}</FormText>
                </div>
                <div className="d-flex flex-row w-100">
                    <FormText className="text-white fs-5 w-50">Address</FormText>
                    <FormText className="text-white fs-5 w-50">{user.address}</FormText>
                </div>
                <div className="d-flex flex-row w-100">
                    <FormText className="text-white fs-5 w-50">Birth date</FormText>
                    <FormText className="text-white fs-5 w-50">{user.dateOfBirth}</FormText>
                </div>
            </div>
            <div className="d-flex flex-column mt-auto mx-5">
                <Button variant="lightest" onClick={logOut}><FiLogOut className="mx-1"/>Log out</Button>
            </div>
        </div>
    )
}