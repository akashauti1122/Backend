package com.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.dto.LoginRequest;
import com.app.entity.modal.DoctorTimeTable;
import com.app.service.serviceInterface.DoctorServiceIntf;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class DoctorController {
	@Autowired
	DoctorServiceIntf doctorService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateDoctor(@RequestBody @Valid LoginRequest request) {
		System.out.println("in auth doctor " + request);
		return new ResponseEntity<>(doctorService.authenticateDoctor(request), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/createAppointmentSlot/{doctor_id}")
	public List<LocalDateTime> createAppointmentSlots(@RequestBody DoctorTimeTable doctorTimeTable, @PathVariable Long doctor_id){
		System.out.println("in create appointment slot "+doctorTimeTable);
		return doctorService.createAvailableSlotsDetails(doctor_id, doctorTimeTable);
	}
}