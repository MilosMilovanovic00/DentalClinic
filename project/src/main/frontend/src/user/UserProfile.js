import React, {useEffect, useState} from 'react'
import {useParams} from "react-router-dom";
import axios from "axios";
import colorPalette from "../utils";
import {FormText} from "react-bootstrap";
import ProfileInfo from "./ProfileInfo";
import Tabs from "../appointments/Tabs";

export default function UserProfile() {

    return (
        <div className="d-flex flex-column vh-100" style={{backgroundColor: colorPalette.lightest}}>
            <FormText className="d-flex justify-content-center fs-1 text-darkest m-0 fw-bold fst-italic my-2"> Dental
                Clinic</FormText>
            <div className="d-flex flex-row h-100 ">
                <ProfileInfo></ProfileInfo>
                <Tabs></Tabs>
            </div>
        </div>
    )
}