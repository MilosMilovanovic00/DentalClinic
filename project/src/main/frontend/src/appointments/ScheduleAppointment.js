import React, {useState} from 'react'
import {Button, Form, Modal} from "react-bootstrap";

export default function ScheduleAppointment({show, handleClose, role}) {
    const [patientEmails, setPatientEmails] = useState([])
    const [doctorEmails, setDoctorEmails] = useState([])
    const minTime = new Date()
    minTime.setHours(9)
    const maxTime = new Date()
    maxTime.setHours(17)

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Schedule an appointment</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3 w-100 d-flex flex-row gap-3" controlId="formBasicEmail">
                        <div className="w-50">
                            <Form.Label>Enter your email</Form.Label>
                            <Form.Control type="email" placeholder="Enter email"/>
                        </div>
                        <div className="w-50">
                            <Form.Label>{role === "Patient" ? "Choose doctors email" : "Choose patient email"}</Form.Label>
                            <Form.Select>

                                {role === "Patient" ? patientEmails.map((email, index) => {
                                    return <option key={index} value={email}>
                                        {email}
                                    </option>
                                }) : doctorEmails.map((email, index) => {
                                    return <option key={index} value={email}>
                                        {email}
                                    </option>
                                })}
                            </Form.Select>
                        </div>
                    </Form.Group>
                    <Form.Group className="mb-3 w-100 d-flex flex-row gap-3" controlId="formBasicPassword">
                        <div className="w-50">
                            <Form.Label>Analysis type</Form.Label>
                            <Form.Select>
                                <option value="InitialExam">Initial Exam</option>
                                <option value="DentalCheckup">Dental Checkup</option>
                                <option value="ComprehensiveExamination">Comprehensive Examination</option>
                                <option value="EmergencyCare">Emergency Care</option>
                            </Form.Select>
                        </div>
                        <div className="w-50">
                            <Form.Label>Choose date</Form.Label>
                            <Form.Control type="date" placeholder="Enter date"/>
                        </div>
                    </Form.Group>
                    <Form.Group className="mb-3 w-100 d-flex flex-row gap-3" controlId="formBasicPassword">
                        <div className="w-50">
                            <Form.Label>Choose start time</Form.Label>
                            <Form.Control type="time" placeholder="Enter time"/>
                        </div>
                        <div className="w-50">
                            <Form.Label>Choose end time</Form.Label>
                            <Form.Control type="time" placeholder="Enter time" min="09:00" max="17:00"/>
                        </div>
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="lightest" onClick={handleClose}>
                    Close
                </Button>
                <Button variant="darkest" onClick={handleClose}>
                    Reserve appointment
                </Button>
            </Modal.Footer>
        </Modal>
    )
}