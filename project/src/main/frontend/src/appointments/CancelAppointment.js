import React, {useState} from 'react'
import {Button, Form, Modal} from "react-bootstrap";

export default function CancelAppointment({handleClose, show, role, appointment}) {
    const options = {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'};

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Cancel an appointment</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="d-flex flex-column gap-1 w-100">
                    {role === "Patient" ?
                        <div className="d-flex flex-column gap-1 w-100">
                            <Form.Label>Your email: {appointment.patientEmail}</Form.Label>
                            <Form.Label>Your doctor: {appointment.doctorEmail}</Form.Label>
                        </div> :
                        <div className="d-flex flex-column gap-1 w-100">
                            <Form.Label>Enter your email:{appointment.patientEmail}</Form.Label>
                            <Form.Control type="email" placeholder="Enter email"/>
                        </div>
                    }
                    <Form.Label>Analysis type: {appointment.analysisType} </Form.Label>
                    <Form.Label>Scheduled at:
                        {appointment.start}
                        {/*{appointment.start.toLocaleDateString("en-US", options)}*/}
                    </Form.Label>
                </div>
                <Modal.Footer>
                    <Button variant="lightest" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="darkest" onClick={handleClose}>Cancel reservation</Button>
                </Modal.Footer>
            </Modal.Body>
        </Modal>
    )
}