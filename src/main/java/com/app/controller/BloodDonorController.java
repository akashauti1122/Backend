package com.app.controller;

import java.util.List;

import com.app.entity.modal.BloodDonor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.serviceInterface.IBloodDonorService;

@RestController
@RequestMapping("/blood_donation")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class BloodDonorController {
	@Autowired
	private IBloodDonorService bloodDonorService;

	@GetMapping("/search/{city}/{blood_group}")
	public List<BloodDonor> getAllBloodDonorsByCityAndBloodGroup(@PathVariable String city,
																 @PathVariable String blood_group) {
		System.out.println("in  get all BloodDonors by City and BloodGroup : " + city + ", " + blood_group);
		return bloodDonorService.getAllBloodDonorsByCityAndBloodGroup(city, blood_group);
	}
}
