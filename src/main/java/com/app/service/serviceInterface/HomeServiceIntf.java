package com.app.service.serviceInterface;

import com.app.entity.dto.LoginResponse;

public interface HomeServiceIntf {
	
	//authenticate patient
	LoginResponse authenticateUser(String email, String password);
		
	
}
