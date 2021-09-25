package com.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.modal.Appointment;
import com.app.service.serviceInterface.IAppointmentService;
import com.app.service.serviceInterface.IDoctorService;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class AppointmentController {
	private IAppointmentService appointmentService;
	private IDoctorService doctorService;

	@Autowired
	public AppointmentController(IAppointmentService appointmentService, IDoctorService doctorService) {
		this.appointmentService = appointmentService;
		this.doctorService = doctorService;
	}

	@GetMapping("/patient/{appointmentId}")
	public ResponseEntity<?> getPatientByAppointmentId(@PathVariable Long appointmentId) {
		System.out.println("in get getPatientByAppointmentId ");
		return ResponseEntity.ok(appointmentService.getPatientByAppointmentId(appointmentId));
	}

	@GetMapping("/specialization/{city}")
	public ResponseEntity<?> getSpecializationByCity(@PathVariable String city) {
		System.out.println("in get doctor's specializations ");
		return ResponseEntity.ok(doctorService.getSpecializationsByCity(city));
	}

	@GetMapping("/search/{specialization}/{city}")
	public ResponseEntity<?> getDoctorsBySpecializationAndCity(@PathVariable String specialization,
			@PathVariable String city) {
		System.out.println("in get doctor's specializations ");
		return ResponseEntity.ok(doctorService.getAllDoctorsBySpecializationAndCity(specialization, city));
	}

	@GetMapping("/currAppointmentP/{patient_id}")
	public List<Appointment> getAllCurrentAppoinments(@PathVariable Long patient_id) {
		System.out.println("in get all appointments of patient " + patient_id);
		return appointmentService.getAllPatientCurrentAppoitments(patient_id);
	}

	@GetMapping("/appointementHistoryP/{patient_id}")
	public List<Appointment> getAllAppoinmentsHistory(@PathVariable Long patient_id) {
		System.out.println("in get all appointment history " + patient_id);
		return appointmentService.getAllPatientAppoitmentsHistory(patient_id);
	}

	@GetMapping("/currAppointmentD/{doctor_id}")
	public List<Appointment> getAllCurrentAppoinmentsForDoctor(@PathVariable Long doctor_id) {
		System.out.println("in get all appointments for doctor " + doctor_id);
		return appointmentService.getAllCurrentAppoitmentsForDoctor(doctor_id);
	}

	@GetMapping("/appointementHistoryD/{doctor_id}/{patient_id}")
	public List<Appointment> getAppoinmentsHistoryOfPatientForDoctor(@PathVariable Long doctor_id,
			@PathVariable Long patient_id) {
		System.out.println("in get appointment history of patient for doctor " + doctor_id + ", " + patient_id);
		return appointmentService.getPatientAppoitmentsHistoryForDoctor(doctor_id, patient_id);
	}

	@GetMapping("/appointementHistoryD/{doctor_id}")
	public List<Appointment> getAllAppoinmentsHistoryForDoctor(@PathVariable Long doctor_id) {
		System.out.println("in get all appointment history for doctor " + doctor_id);
		return appointmentService.getAllAppoitmentsHistoryForDoctor(doctor_id);
	}

	@GetMapping("/bookAppointment/{doctor_id}/{patient_id}/{time}")
	public List<LocalDateTime> bookAppointmentForPatient(@PathVariable Long doctor_id, @PathVariable Long patient_id,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time) {
		System.out.println("**************in book appointment : " + doctor_id + ", " + patient_id + ", " + time);
		return appointmentService.bookAppointmentForPatient(doctor_id, patient_id, time);
	}

	@DeleteMapping("/cancelAppointment/{appointment_id}")
	public void cancelAppointment(@PathVariable Long appointment_id) {
		System.out.println("in cancel appointemnt : " + appointment_id);
		System.out.println(appointmentService.cancelAppointment(appointment_id));
	}
}