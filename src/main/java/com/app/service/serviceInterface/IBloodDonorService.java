package com.app.service.serviceInterface;

import java.util.List;

import com.app.entity.modal.BloodDonor;

public interface IBloodDonorService {
	
	//Save new donor
	BloodDonor saveBloodDonor(com.app.entity.modal.BloodDonor donor);
	
	//get list of all donors
	List<BloodDonor> getAllBloodDonors();
	
	//get all donors by city and blood group
	List<BloodDonor> getAllBloodDonorsByCityAndBloodGroup(String city, String bloodGroup);
	


}
