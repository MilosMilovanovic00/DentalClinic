import {Button, Card, Dropdown} from "react-bootstrap";
import FullCalendar from "@fullcalendar/react";
import React, {useState} from "react";
import 'bootstrap/dist/css/bootstrap.css';
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from "@fullcalendar/interaction";
import ScheduleAppointment from "./ScheduleAppointment";
import CancelAppointment from "./CancelAppointment";

export default function Calendar() {
    const calendarRef = React.createRef();
    const [appointments, setAppointments] = useState([
        {
            id: "0",
            title: "Initial Exam",
            start: "2022-09-15T12:30:00",
            end: "2022-09-15T13:00:00",
            doctorEmail:"doctor",
            patientEmail:"patient",
            analysisType:"type"
        }
    ])
    const [role, setRole] = useState("Patient")
    const [appointmentData, setAppointmentData] = useState("")

    const [showScheduling, setShowScheduling] = useState(false);
    const [showCanceling, setShowCanceling] = useState(false);

    const closeScheduling = () => setShowScheduling(false);
    const closeCanceling = () => setShowCanceling(false);
    const handleShowScheduling = () => setShowScheduling(true);
    const handleShowCanceling = () => setShowCanceling(true);

    const changeView = (view) => {
        calendarRef.current
            .getApi()
            .changeView(view);
    }
    return (
        <div style={{backgroundColor: "white"}} className="p-3 rounded-2">
            <div className="d-flex flex-row gap-2">
                <Dropdown>
                    <Dropdown.Toggle className="mb-3" variant="lightest" id="dropdown-basic">
                        Choose your view of calendar
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        <Dropdown.Item onClick={() => changeView('timeGridWeek')}>Weekly view</Dropdown.Item>
                        <Dropdown.Item onClick={() => changeView('timeGridDay')}>Daily view</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
                <Button variant="outline-darkest" className="mb-3" onClick={handleShowScheduling}>{role === 'Patient' ? "Make an appointment" :"Make an appointment for patient"}</Button>
            </div>
            <FullCalendar
                plugins={[dayGridPlugin, interactionPlugin, timeGridPlugin]}
                initialView="timeGridWeek"
                slotDuration='00:30'
                timeZone="local"
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
                locale="en"
                slotMinTime="09:00:00"
                slotMaxTime="17:00:00"
                allDaySlot={false}
                validRange={
                    {start: new Date()}
                }
                eventOverlap={false}
                height={"auto"}
                ref={calendarRef}
                eventClick={(arg) => {
                    for (const appointment of appointments) {
                        if (appointment.id === arg.event.id) {
                            setAppointmentData(appointment)
                            handleShowCanceling()
                        }
                    }
                }
                }
            />
            <ScheduleAppointment show={showScheduling} handleClose={closeScheduling} role={role}/>
            <CancelAppointment show={showCanceling} handleClose={closeCanceling} role={role} appointment={appointmentData}/>
        </div>
    )
}