import {Button, Dropdown} from "react-bootstrap";
import FullCalendar from "@fullcalendar/react";
import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.css';
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from "@fullcalendar/interaction";
import ScheduleAppointment from "./ScheduleAppointment";
import CancelAppointment from "./CancelAppointment";
import {showInfo} from "../utils";
import {ToastContainer} from "react-toastify";
import axios from "axios";

export default function Calendar() {
    const calendarRef = React.createRef();
    const [appointments, setAppointments] = useState([])
    const [role, setRole] = useState("")
    const [appointmentData, setAppointmentData] = useState("")

    const [showScheduling, setShowScheduling] = useState(false);
    const [showCanceling, setShowCanceling] = useState(false);

    const closeScheduling = () => setShowScheduling(false);
    const closeCanceling = () => setShowCanceling(false);
    const handleShowScheduling = () => setShowScheduling(true);
    const handleShowCanceling = () => setShowCanceling(true);

    function getLoggedUserRole() {
        axios.get("http://localhost:8080/user/role", {
            headers: {
                Authorization: "Bearer " + localStorage.getItem('token')
            }
        }).then(value => {
            setRole(value.data)
        })
    }

    function getLoggedUserAppointments() {
        axios.get("http://localhost:8080/appointment/loggedUser", {
            headers: {
                Authorization: "Bearer " + localStorage.getItem('token')
            }
        }).then(value => {
            setAppointments(value.data)
        })
    }

    useEffect(() => {
        getLoggedUserRole()
        getLoggedUserAppointments()
    }, [])

    const changeView = (view) => {
        calendarRef.current
            .getApi()
            .changeView(view);
    }
    return (
        <div style={{backgroundColor: "white"}} className="p-3 rounded-2">
            <div className="d-flex flex-row gap-2">
                <Dropdown>
                    <Dropdown.Toggle className="mb-2" variant="lightest" id="dropdown-basic">
                        Choose your view of calendar
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        <Dropdown.Item onClick={() => changeView('timeGridWeek')}>Weekly view</Dropdown.Item>
                        <Dropdown.Item onClick={() => changeView('timeGridDay')}>Daily view</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
                <Button variant="outline-darkest" className="mb-2"
                        onClick={handleShowScheduling}>{role === 'Patient' ? "Make an appointment" : "Make an appointment for patient"}</Button>
            </div>
            <FullCalendar
                plugins={[dayGridPlugin, interactionPlugin, timeGridPlugin]}
                initialView="timeGridWeek"
                slotDuration='00:30'
                selectable={true}
                nowIndicator={true}
                events={appointments}
                titleFormat={
                    {
                        month: 'long',
                        day: 'numeric',
                        year: 'numeric'
                    }
                }
                slotMinTime="09:00:00"
                slotMaxTime="17:00:00"
                allDaySlot={false}
                validRange={
                    {start: new Date()}
                }
                // eventOverlap={false}
                height={"auto"}
                ref={calendarRef}
                eventClick={(arg) => {
                    for (const appointment of appointments) {
                        const tomorrow = new Date()
                        const date = tomorrow.getDate()
                        tomorrow.setDate(date + 1)
                        if (Date.parse(appointment.start) < tomorrow) {
                            showInfo("You can't cancel appointment 24 hours")
                        } else if (appointment.id.toString()=== arg.event.id) {
                            setAppointmentData(appointment)
                            handleShowCanceling()
                        }
                    }
                }
                }
            />
            <ScheduleAppointment show={showScheduling} handleClose={closeScheduling} role={role}
                                 setAppointments={setAppointments}/>
            <CancelAppointment show={showCanceling} handleClose={closeCanceling} role={role}
                               appointment={appointmentData} setAppointments={setAppointments}/>
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