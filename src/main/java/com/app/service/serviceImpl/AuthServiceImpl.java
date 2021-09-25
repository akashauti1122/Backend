package com.app.service.serviceImpl;

import com.app.entity.dto.LoginRequest;
import com.app.entity.modal.User;
import com.app.repository.DoctorRepository;
import com.app.repository.PatientRepository;
import com.app.service.serviceInterface.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

//    @Autowired
//    public AuthServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository) {
//        this.doctorRepository = doctorRepository;
//        this.patientRepository = patientRepository;
//    }

    @Override
    public User loginUser(LoginRequest request) {
        return null;
    }
}
