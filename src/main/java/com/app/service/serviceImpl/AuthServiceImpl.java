package com.app.service.serviceImpl;

import com.app.entity.dto.LoginRequest;
import com.app.entity.dto.LoginResponse;
import com.app.entity.modal.User;
import com.app.repository.DoctorRepository;
import com.app.repository.UserRepository;
import com.app.service.serviceInterface.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("User with email: " + request.getEmail() + " doesn't exist."));

//		// Hash login password and compare with hash that is stored in database
//		// Hashing -> 1 way technique (plain text ---> hash )
//		// hash --X-->   plain text
		if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return new LoginResponse(user.getId(), user.getFirstName(), user.getUserType().toString());
		}
		throw new RuntimeException("Invalid password!!!");
    }
}
