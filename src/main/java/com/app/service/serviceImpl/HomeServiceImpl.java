package com.app.service.serviceImpl;

import com.app.service.serviceInterface.HomeServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.dto.LoginResponse;
import com.app.repository.AdminRepository;
import com.app.repository.PatientRepository;

@Service
@Transactional
public class HomeServiceImpl implements HomeServiceIntf {

	@Autowired 
	AdminRepository adminRepo;
	
	@Autowired
	PatientRepository patientRepo;
	
	@Override
	public LoginResponse authenticateUser(String email, String password) {
		try {
			Admin admin = adminRepo.findByEmailAndPassword(email,password).orElseThrow(() -> new RuntimeException("Auth Failed"));
			return new LoginResponse(admin.getId(), admin.getName(), "admin");
		} catch (Exception e) {
			Patient patient = patientRepo.findByEmailAndPassword(email,password).orElseThrow(() -> new RuntimeException("Auth Failed"));
			return new LoginResponse(patient.getId(), patient.getFirstName(), "patient");
		}
	}

	
}
