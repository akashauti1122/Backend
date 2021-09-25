package com.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.app.entity.modal.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entity.modal.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
//	@Query("SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.appointmentTime > now()")
//	List<Appointment> getAllPatientCurrentAppoitments(Long patientId);

	List<Appointment> findByPatientAndAppointmentTimeAfter(Patient patient, LocalDateTime time);
	
//	@Query("SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.appointmentTime < now() ORDER BY a.appointmentTime DESC")
//	List<Appointment> getAllPatientAppoitmentsHistory(Long patientId);

	List<Appointment> findByPatientAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(Patient patient, LocalDateTime time);

	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1 AND a.appointmentTime > now()")
	List<Appointment> getAllCurrentAppoitmentsForDoctor(Long doctorId);
	
	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1 AND a.patient.id = ?2 AND a.appointmentTime < now() ORDER BY a.appointmentTime DESC")
	List<Appointment> getPatientAppoitmentsHistoryForDoctor(Long doctorId, Long patientId);
	
	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1 AND a.appointmentTime < now() ORDER BY a.appointmentTime DESC") //limit is NOT ALLOWED
	List<Appointment> getAllAppoitmentsHistoryForDoctor(Long doctorId);

	@Query("SELECT a.id FROM Appointment a WHERE a.patient.id =?1")	
	List<Long> getAppointmentIdListForPatient(Long patientId);
	
}
