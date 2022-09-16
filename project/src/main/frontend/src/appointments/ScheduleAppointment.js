import React, {useEffect, useState} from 'react'
import {Button, Form, Modal} from "react-bootstrap";
import axios from "axios";
import {emptyInput, showError, showSuccess} from "../utils";

export default function ScheduleAppointment({show, handleClose, role, setAppointments}) {
    const [emails, setEmails] = useState([])
    const findFormErrors = () => {
        const {analysisType, date, startTime, endTime, email, chosenEmail} = form
        const newErrors = {}
        if (!email) newErrors.email = emptyInput
        else if (!email.includes('@gmail.com')) newErrors.email = 'Must contain @gmail.com'
        if (!chosenEmail) newErrors.chosenEmail = emptyInput
        if (!analysisType) newErrors.analysisType = emptyInput

        if (!date) newErrors.date = emptyInput
        else if (new Date(date).getDate() <= new Date().getDate()) newErrors.date = "You can't reserve in past or today"
        const propStart = checkTime(startTime)
        const propEnd = checkTime(endTime)
        if (propStart !== "")
            newErrors.startTime = propStart
        if (propEnd !== "")
            newErrors.endTime = propEnd
        if (!newErrors.startTime && !newErrors.endTime) {
            const [startHour, startMinutes] = startTime.toString().split(":")
            const [endHour, endMinutes] = endTime.toString().split(":")
            const start = new Date(date)
            start.setHours(startHour)
            start.setMinutes(startMinutes)
            const end = new Date(date)
            end.setHours(endHour)
            end.setMinutes(endMinutes)
            form.start = start
            form.end = end
            const value = parseInt(endHour) * 60 + parseInt(endMinutes) - parseInt(startHour) * 60 - parseInt(startMinutes)
            if (value !== 30 && value !== 60) {
                newErrors.startTime = "Time slot can be 30 or 60 minutes"
                newErrors.endTime = "Time slot can be 30 or 60 minutes"
            }
        }
        if (role === 'Patient') {
            form.patientEmail = email
            form.doctorEmail = chosenEmail
        } else {
            form.doctorEmail = email
            form.patientEmail = chosenEmail
        }

        return newErrors

    }

    function checkTime(time) {
        if (!time) {
            return emptyInput
        } else {
            const [hour, minutes] = time.toString().split(":")
            if (parseInt(minutes) !== 0 && parseInt(minutes) !== 30) return "You can only schedule an slots of half an hour or hour"
            else if (parseInt(hour) > 17 || parseInt(hour) < 8) return "You can only schedule an appointment between 9 and 17 hours"
            else return ""
        }
    }


    function getEmails() {
        if (role !== "")
            axios.get("http://localhost:8080/user/emails/" + role).then(value => {
                setEmails(value.data)
            })

    }

    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})
    const handleSubmit = e => {
        e.preventDefault()
        const newErrors = findFormErrors()
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors)
        } else {
            form.start.setHours(form.start.getHours() - form.start.getTimezoneOffset() / 60)
            form.end.setHours(form.end.getHours() - form.end.getTimezoneOffset() / 60)
            const dto = {
                patientEmail: form.patientEmail,
                doctorEmail: form.doctorEmail,
                start: form.start.toISOString().split(".")[0],
                end: form.end.toISOString().split(".")[0],
                analysisType: form.analysisType,
            }
            scheduleAppointment(dto)
            setErrors({})
        }
    }
    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })
    }

    useEffect(() => {
        getEmails()
    }, [role])


    function scheduleAppointment(dto) {
        axios.post("http://localhost:8080/appointment/schedule", dto, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        }).then(value => {
            setAppointments(value.data)
            showSuccess("You successfully scheduled an appointment")
            handleClose()
        }).catch(reason => {
            showError(reason.response.data)
        })
    }

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
                            <Form.Control type="email" placeholder="Enter email"
                                          onInput={e => setField('email', e.target.value)}
                                          isInvalid={!!errors.email}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.email}
                            </Form.Control.Feedback>
                        </div>
                        <div className="w-50">
                            <Form.Label>{role === "Patient" ? "Choose doctors email" : "Choose patient email"}</Form.Label>
                            <Form.Select onChange={e => setField('chosenEmail', e.target.value)}
                                         isInvalid={!!errors.chosenEmail}>
                                <option/>
                                {emails.map((email, index) => {
                                    return <option key={index} value={email}>
                                        {email}
                                    </option>
                                })}
                            </Form.Select>
                            <Form.Control.Feedback type='invalid'>
                                {errors.chosenEmail}
                            </Form.Control.Feedback>
                        </div>
                    </Form.Group>
                    <Form.Group className="mb-3 w-100 d-flex flex-row gap-3" controlId="formBasicPassword">
                        <div className="w-50">
                            <Form.Label>Analysis type</Form.Label>
                            <Form.Select onChange={e => setField('analysisType', e.target.value)}
                                         isInvalid={!!errors.analysisType}>
                                <option/>
                                <option value="InitialExam">Initial Exam</option>
                                <option value="DentalCheckup">Dental Checkup</option>
                                <option value="ComprehensiveExamination">Comprehensive Examination</option>
                                <option value="EmergencyCare">Emergency Care</option>
                            </Form.Select>
                            <Form.Control.Feedback type='invalid'>
                                {errors.analysisType}
                            </Form.Control.Feedback>
                        </div>
                        <div className="w-50">
                            <Form.Label>Choose date</Form.Label>
                            <Form.Control type="date" placeholder="Enter date"
                                          onChange={e => setField('date', e.target.value)}
                                          isInvalid={!!errors.date}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.date}
                            </Form.Control.Feedback>
                        </div>
                    </Form.Group>
                    <Form.Group className="mb-3 w-100 d-flex flex-row gap-3" controlId="formBasicPassword">
                        <div className="w-50">
                            <Form.Label>Choose start time</Form.Label>
                            <Form.Control type="time" placeholder="Enter time"
                                          onChange={e => setField('startTime', e.target.value)}
                                          isInvalid={!!errors.startTime}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.startTime}
                            </Form.Control.Feedback>
                        </div>
                        <div className="w-50">
                            <Form.Label>Choose end time</Form.Label>
                            <Form.Control type="time" placeholder="Enter time"
                                          onChange={e => setField('endTime', e.target.value)}
                                          isInvalid={!!errors.endTime}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.endTime}
                            </Form.Control.Feedback>
                        </div>
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="lightest" onClick={handleClose}>
                    Close
                </Button>
                <Button variant="darkest" onClick={handleSubmit}>
                    Reserve appointment
                </Button>
            </Modal.Footer>
        </Modal>
    )
}