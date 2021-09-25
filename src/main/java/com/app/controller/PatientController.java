package com.app.controller;

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

import com.app.service.serviceInterface.PatientServiceIntf;

@RestController
@RequestMapping("/patient")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class PatientController {
	@Autowired
	private PatientServiceIntf patientService;

	@PostMapping("/sign-up")
	public ResponseEntity<?> savePatient(@RequestBody @Valid Patient patient) {
		System.out.println("In class : " + getClass().getName());
		System.out.println("User : " + patient);
		return new ResponseEntity<>(patientService.savePatient(patient), HttpStatus.CREATED);
	}

	@PostMapping("/getPatient/{id}")
	public ResponseEntity<?> getPatientDetails(@PathVariable Long id) {
		System.out.println("In class : " + getClass().getName());
		System.out.println("PID : " + id);
		return new ResponseEntity<>(patientService.getPatientDetails(id), HttpStatus.OK);
	}
}