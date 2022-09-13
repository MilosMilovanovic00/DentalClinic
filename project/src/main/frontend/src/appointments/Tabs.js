import colorPalette from "../utils";
import React, {useState} from "react";
import 'bootstrap/dist/css/bootstrap.css';
import Calendar from "./Calendar";

export default function Tabs() {
    const [textColor, setTextColor] = useState('white')
    const changeColor = () => {
        setTextColor(colorPalette.darkest)
    }
    return (
        <div className="d-flex flex-row m-3 rounded rounded-3 w-75"
             style={{backgroundColor: colorPalette.mediumDark}}>
            <div className="m-4">
                <Calendar/>
            </div>
        </div>
    )
}