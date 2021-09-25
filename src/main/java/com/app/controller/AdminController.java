package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import com.app.entity.dto.DoctorDto;
import com.app.entity.dto.UserDto;
import com.app.entity.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.modal.Doctor;
import com.app.service.serviceInterface.IBloodDonorService;
import com.app.service.serviceInterface.IDoctorService;
import com.app.service.serviceInterface.IUserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class AdminController {
	private IDoctorService doctorService;
	private IUserService patientService;
	private IBloodDonorService bloodDonorService;

	@Autowired
	public AdminController(IDoctorService doctorService, IUserService patientService,
						   IBloodDonorService bloodDonorService) {
		this.doctorService = doctorService;
		this.bloodDonorService = bloodDonorService;
		this.patientService = patientService;
	}

	@PostMapping("/blood_donor")
	public ResponseEntity<?> saveDonor(@RequestBody @Valid com.app.entity.modal.BloodDonor donor) {
		System.out.println("in save donor : " + donor);
		return new ResponseEntity<>(bloodDonorService.saveBloodDonor(donor), HttpStatus.CREATED);
	}

	@GetMapping("/search_donors")
	public List<com.app.entity.modal.BloodDonor> getAllBloodDonors() {
		System.out.println("in  get all BloodDonors ");
		return bloodDonorService.getAllBloodDonors();
	}

	@PostMapping("/doctor")
	public ResponseEntity<?> saveDoctor(@RequestBody UserDto userDto) {
		System.out.println("in save doctor : " + userDto);
		try {
			return new ResponseEntity<>(doctorService.saveDoctor(userDto), HttpStatus.CREATED);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}

	@GetMapping("/getAllDoctors")
	public List<Doctor> getAllDoctorDetails() {
		System.out.println("in get all doctors detail ");
		return doctorService.getAllDoctors();
	}

//	@DeleteMapping("/removeDoctor/{doctor_id}")
//	public String deleteDoctor(@PathVariable Long doctor_id) {
//		System.out.println("in delete doctor : " + doctor_id);
//		return doctorService.deleteDoctorById(doctor_id);
//	}

	@GetMapping("/getAllPatients")
	public List<User> getAllPatientDetails() {
		System.out.println("in get all patients detail ");
		return patientService.getAllPatients();
	}

//	@DeleteMapping("removePatient/{patient_id}")
//	public String deletePatient(@PathVariable Long patient_id) {
//		System.out.println("in delete patient : " + patient_id);
//		return patientService.deletePatientById(patient_id);
//	}
}
