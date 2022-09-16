import React, {useState} from 'react'
import {Button, Form, Modal} from "react-bootstrap";
import {emptyInput, showError, showSuccess} from "../utils";
import axios from "axios";

export default function CancelAppointment({handleClose, show, role, appointment, setAppointments}) {
    const options = {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'};
    const [cancelForPatient, setCancelForPatient] = useState(false)

    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})

    const findFormErrors = () => {
        const {doctorEmail, patientEmail} = form
        const newErrors = {}
        if (role === "Doctor") {
            if (!doctorEmail) newErrors.doctorEmail = emptyInput
            else if (!doctorEmail.includes('@gmail.com')) newErrors.doctorEmail = 'Must contain @gmail.com'
            if (cancelForPatient) {
                if (!patientEmail) newErrors.patientEmail = emptyInput
                else if (!patientEmail.includes('@gmail.com')) newErrors.patientEmail = 'Must contain @gmail.com'
            }
        }
        return newErrors
    }

    function cancelAppointmentAsPatient() {
        axios.post("http://localhost:8080/appointment/cancel", appointment.id.toString(), {
            headers: {
                Authorization: "Bearer " + localStorage.getItem('token'),
                'Content-Type': 'text/plain'
            }
        }).then(value => {
            setAppointments(value.data)
            handleClose()
            showSuccess("You successfully canceled appointment")
        })
    }

    function cancelAppointmentAsDoctor(dto) {
        axios.post("http://localhost:8080/appointment/cancelAsDoctor", dto, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem('token')
            }
        }).then(value => {
            setAppointments(value.data)
            handleClose()
            showSuccess("You successfully canceled appointment")
        }).catch(reason => {
            showError(reason.response.data)
        })
    }

    const handleSubmit = e => {
        e.preventDefault()
        const newErrors = findFormErrors()
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors)
        } else {
            const dto = {
                patientEmail: form.patientEmail,
                patientChecked: cancelForPatient,
                doctorEmail: form.doctorEmail,
                id: appointment.id
            }
            if (role === "Doctor") {
                cancelAppointmentAsDoctor(dto)
            } else
                cancelAppointmentAsPatient()
        }
    }
    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })
    }


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
                            <Form.Label>Enter your email</Form.Label>
                            <Form.Control type="email" placeholder="Enter email"
                                          onChange={event => setField("doctorEmail", event.target.value)}
                                          isInvalid={!!errors.doctorEmail}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.doctorEmail}
                            </Form.Control.Feedback>
                            <Form.Check
                                type="checkbox"
                                label="Cancel for patient"
                                checked={cancelForPatient}
                                onChange={event => {
                                    setCancelForPatient(!cancelForPatient)
                                }}
                            />
                            <Form.Control type="email" placeholder="Enter patient email"
                                          disabled={!cancelForPatient}
                                          onChange={event => setField("patientEmail", event.target.value)}
                                          isInvalid={!!errors.patientEmail}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.patientEmail}
                            </Form.Control.Feedback>
                        </div>
                    }
                    <Form.Label>Analysis type: {appointment.title} </Form.Label>
                    <Form.Label>Scheduled at: {new Date(Date.parse(appointment.start)).toLocaleString("en-US", options)}
                    </Form.Label>
                </div>
                <Modal.Footer>
                    <Button variant="lightest" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="darkest" onClick={handleSubmit}>Cancel reservation</Button>
                </Modal.Footer>
            </Modal.Body>
        </Modal>
    )
}