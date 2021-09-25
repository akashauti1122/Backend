package com.app.service.serviceInterface;

import com.app.entity.dto.UserDto;
import com.app.entity.modal.User;

import java.util.List;

public interface IUserService {
	
	//register new patient
	User saveUser(UserDto user);
	
	//delete patient
//	String deletePatientById(Long patient_id);
	
	//getAll patients
	List<User> getAllPatients();

	//get specific patient
	User getPatientDetails(Long id);
}
