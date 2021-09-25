package com.app.service.serviceImpl;

import java.util.List;

import com.app.service.serviceInterface.AdminServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.repository.AdminRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminServiceIntf {

	@Autowired
	private AdminRepository adminRepo;
	
	@Override
	public List<Admin> getListOfAdmin() {
		return adminRepo.findAll();
	}

}
