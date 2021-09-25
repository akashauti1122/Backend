package com.app.controller;

import com.app.entity.dto.LoginRequest;
import com.app.entity.dto.UserDto;
import com.app.service.serviceInterface.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.serviceInterface.IUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(value = "*", allowedHeaders = "*")
public class UserController {
	private final IUserService userService;
	private final IAuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) {
		System.out.println("in auth user " + request);
		return ResponseEntity.ok(authService.loginUser(request));
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> savePatient(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.CREATED);
	}

	@PostMapping("/getPatient/{id}")
	public ResponseEntity<?> getPatientDetails(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getPatientDetails(id));
	}
}