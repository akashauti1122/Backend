package com.app.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.app.entity.modal.Doctor;
import com.app.entity.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entity.modal.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
//	@Query("SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.appointmentTime > now()")
//	List<Appointment> getAllPatientCurrentAppoitments(Long patientId);

	List<Appointment> findByPatientAndAppointmentTimeAfter(User patient, LocalDateTime time);
	
//	@Query("SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.appointmentTime < now() ORDER BY a.appointmentTime DESC")
//	List<Appointment> getAllPatientAppoitmentsHistory(Long patientId);

	List<Appointment> findByPatientAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(User patient, LocalDateTime time);

//	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1 AND a.appointmentTime > now()")
//	List<Appointment> getAllCurrentAppoitmentsForDoctor(Long doctorId);

	List<Appointment> findByDoctorAndAppointmentTimeAfter(Doctor doctor, LocalDateTime time);

//	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1 AND a.patient.id = ?2 AND a.appointmentTime < now() ORDER BY a.appointmentTime DESC")
//	List<Appointment> getPatientAppoitmentsHistoryForDoctor(Long doctorId, Long patientId);

	List<Appointment> findByDoctorAndPatientAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(Doctor doctor, User patient, LocalDateTime time);

//	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1 AND a.appointmentTime < now() ORDER BY a.appointmentTime DESC") //limit is NOT ALLOWED
//	List<Appointment> getAllAppoitmentsHistoryForDoctor(Long doctorId);

	List<Appointment> findByDoctorAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(Doctor doctor, LocalDateTime time);

	@Query("SELECT a.id FROM Appointment a WHERE a.patient.id =?1")	
	List<Long> getAppointmentIdListForPatient(Long patientId);

//	@Query("select a from Appointment a where a.patient.id=?1 and a.appointmentTime in (?2)")
//	List<Appointment> findByPatientAndAppointmentDate(Long patientId, LocalDate searchDate);
}
