package com.app.service.serviceInterface;

import java.util.List;

public interface PatientServiceIntf {
	
	//register new patient
	Patient savePatient(Patient user);
	
	//delete patient
	String deletePatientById(Long patient_id);
	
	//getAll patients
	List<Patient> getAllPatients();

	//get specific patient
	Patient getPatientDetails(Long id);
	
	//authenticate patient
	//LoginResponse authenticatePatient(String email, String password);
	
	//cancel appointment [implemented in appointment services]
	//String cancelAppointment(Long patientId, Long appointmentId);
	
	
	
}
