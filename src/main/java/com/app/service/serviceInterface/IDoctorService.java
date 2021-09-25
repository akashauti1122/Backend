package com.app.service.serviceInterface;

import java.time.LocalDateTime;
import java.util.List;

import com.app.entity.dto.DoctorDto;
import com.app.entity.dto.LoginRequest;
import com.app.entity.dto.LoginResponse;
import com.app.entity.dto.UserDto;
import com.app.entity.modal.Doctor;
import com.app.entity.modal.DoctorTimeTable;

public interface IDoctorService {
	//register new doctor
	Doctor saveDoctor(UserDto doctor);
	
	//get list of specializations by city
	List<String> getSpecializationsByCity(String city);
	
	//get list of doctors by specialization and city
	List<Doctor> getAllDoctorsBySpecializationAndCity(String specialization,String city);	
	
	//cancel appointment [implemented in appointment services]
	//String cancelAppointment(Long doctorId, Long appointmentId);
	
	//get list of all doctors
	List<Doctor>getAllDoctors();
	
	//delete doctor by id
//	String deleteDoctorById(Long doctorId);

	Doctor getDoctorDetails(Long id);
	
	//Make available slots table for doctor from start date to end date
	List<LocalDateTime> createAvailableSlotsDetails(Long doctorId, DoctorTimeTable doctorTimeTable);
	
	//make slots available if we delete any patient
	void makeSlotsAvailable(Long appoitmentId);
}
