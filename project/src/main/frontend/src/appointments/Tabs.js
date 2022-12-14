import colorPalette from "../utils";
import React from "react";
import 'bootstrap/dist/css/bootstrap.css';
import Calendar from "./Calendar";

export default function Tabs() {
    return (
        <div className="d-flex flex-row m-3 rounded rounded-3 w-75"
             style={{backgroundColor: colorPalette.mediumDark}}>
            <div className="m-4">
                <Calendar/>
            </div>
        </div>
    )
}