package com.example.project.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointment, Long> {
    @Query("select a from Appointment a where upper(a.patient.email) = upper(?1) or upper(a.doctor.email) = upper(?1)")
    List<Appointment> findAllWithEmail(String email);

    @Query("select a from Appointment a where ?1 > a.start and ?1 < a.end or ?2 > a.start and ?2 < a.end")
    List<Appointment> findAllOnDate(LocalDateTime startStart, LocalDateTime startEnd);


}
