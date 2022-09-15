package com.example.project.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointment, Long> {

    @Query("FROM Appointment WHERE User.email = ?1")
    List<Appointment> getAppointmentByUserEmail(String email);
}
