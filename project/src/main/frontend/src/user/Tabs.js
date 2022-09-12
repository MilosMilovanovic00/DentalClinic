import colorPalette from "../utils";
import React, {useState} from "react";
import {Nav} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.css';

export default function Tabs() {
    const [textColor, setTextColor] = useState('white')
    const changeColor = () => {
        setTextColor(colorPalette.darkest)
    }

    return (
        <div className="d-flex flex-column m-3 rounded rounded-3 w-75"
             style={{backgroundColor: colorPalette.mediumDark}}>

        </div>
    )
}