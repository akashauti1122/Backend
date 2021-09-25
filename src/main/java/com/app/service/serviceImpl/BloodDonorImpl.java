package com.app.service.serviceImpl;

import java.util.List;

import com.app.service.serviceInterface.IBloodDonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.modal.BloodGroup;
import com.app.repository.BloodDonorRepository;

@Service
@Transactional
public class BloodDonorImpl implements IBloodDonorService {

	@Autowired
	private BloodDonorRepository bloodDonorRepo;
	
	@Override
	public com.app.entity.modal.BloodDonor saveBloodDonor(com.app.entity.modal.BloodDonor donor) {
		return  bloodDonorRepo.save(donor);
	}

	@Override
	public List<com.app.entity.modal.BloodDonor> getAllBloodDonors() {
		return bloodDonorRepo.findAll();
	}

	@Override
	public List<com.app.entity.modal.BloodDonor> getAllBloodDonorsByCityAndBloodGroup(String city, String bloodGroup) {
		BloodGroup gp = BloodGroup.valueOf(bloodGroup);
		return bloodDonorRepo.findByCityAndBloodGroup(city, gp);
	}
	
}
