package com.app.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.app.entity.dto.UserDto;
import com.app.entity.modal.User;
import com.app.entity.modal.UserType;
import com.app.service.serviceInterface.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.app.exception.customException.UserHandlingException;
import com.app.repository.AppointmentRepository;
import com.app.repository.UserRepository;

import static com.app.util.UtilityClass.getNullPropertyNames;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
	private final UserRepository userRepository;
	private final DoctorServiceImpl doctorService;
	private final AppointmentRepository appointmentRepo;
	
	@Override
	public User saveUser(UserDto userDto) {
		User newUser = new User();
		BeanUtils.copyProperties(userDto, newUser, getNullPropertyNames(userDto));
		newUser.setUserType(UserType.PATIENT);
		return userRepository.save(newUser);
	}

//	@Override
//	public String deletePatientById(Long patient_id) {
//		List<Long> appoitments = appointmentRepo.getAppointmentIdListForPatient(patient_id);
//		Long appointmentId = null;
//		for(int i=0;i<appoitments.size();i++) {
//			appointmentId = appoitments.get(0);
//			doctorService.makeSlotsAvailable(appointmentId);
//		}
//
//		userRepository.deleteById(patient_id);
//		return "Successfully Deleted Patient with id : "+patient_id;
//	}


	@Override
	public List<User> getAllPatients() {
		return userRepository.findAll()
				.stream()
				.filter(user -> user.getUserType().equals(UserType.PATIENT))
				.collect(Collectors.toList());
	}

	@Override
	public User getPatientDetails(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserHandlingException("Invalid patient ID..."));
		if(user.getUserType() == UserType.PATIENT)
			return user;
		throw new RuntimeException("Given id: " + id + " doesn't belong to a patient!!!");
	}
}
