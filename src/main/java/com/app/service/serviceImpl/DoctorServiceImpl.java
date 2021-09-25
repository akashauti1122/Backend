package com.app.service.serviceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.app.entity.dto.DoctorDto;
import com.app.entity.dto.LoginRequest;
import com.app.entity.dto.UserDto;
import com.app.entity.modal.*;
import com.app.repository.UserRepository;
import com.app.service.serviceInterface.IDoctorService;
import com.app.service.serviceInterface.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entity.dto.LoginResponse;
import com.app.exception.customException.UserHandlingException;
import com.app.repository.AppointmentRepository;
import com.app.repository.DoctorRepository;
import com.app.repository.DoctorTimeTableRepository;

import static com.app.util.UtilityClass.getNullPropertyNames;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements IDoctorService {
	private	final PasswordEncoder passwordEncoder;
	private	final DoctorRepository doctorRepo;
	private	final IUserService userService;
	private	final AppointmentRepository appointmentRepo;
	private	final DoctorTimeTableRepository doctorTimeTableRepo;

	@Override
	public Doctor saveDoctor(UserDto dto) {
		User newUser = new User();
		BeanUtils.copyProperties(dto, newUser, getNullPropertyNames(dto));
		Doctor newDoctor = new Doctor();
		BeanUtils.copyProperties(dto, newDoctor, getNullPropertyNames(dto));
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setUserType(UserType.DOCTOR);
		newUser = userService.persistUser(newUser);
		if(null != newUser) {
			newDoctor.setDoctorId(newUser.getId());
			return doctorRepo.save(newDoctor);
		}
		throw new RuntimeException("Something went wrong!!!, Doctor cannot be registered.");
	}
	
	@Override
	public List<String> getSpecializationsByCity(String city){
		return userService.getDoctorsByCity(city)
				.stream()
				.map(doctor -> this.getDoctorDetails(doctor.getId()))
				.map(Doctor::getSpecialization)
				.distinct().collect(Collectors.toList());
	}

	@Override
	public List<Doctor> getAllDoctorsBySpecializationAndCity(String specialization, String city) {
		return userService.getDoctorsByCity(city)
				.stream()
				.filter(user -> user.getCity().equalsIgnoreCase(city))
				.map(doctor -> this.getDoctorDetails(doctor.getId()))
				.filter(doctor -> doctor.getSpecialization().equalsIgnoreCase(specialization))
				.collect(Collectors.toList());
	}
	
//	@Override
//	public LoginResponse authenticateDoctor(LoginRequest request) {
//		Doctor doctor = doctorRepo.findByEmail(request.getEmail())
//				.orElseThrow(() -> new RuntimeException("User with email: " + request.getEmail() + " doesn't exist."));
//
//		// Hash login password and compare with hash that is stored in database
//		// Hashing -> 1 way technique (plain text ---> hash )
//		// hash --X-->   plain text
//		if(passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
//			return new LoginResponse(doctor.getId(), doctor.getFirstName(), "doctor");
//		}
//		throw new RuntimeException("Invalid password!!!");
//	}

	@Override
	public List<Doctor> getAllDoctors() {
		return doctorRepo.findAll();
	}

//	@Override
//	public String deleteDoctorById(Long doctorId) {
//		doctorRepo.deleteById(doctorId);
//		return "Successfully Deleted doctor with id : "+doctorId;
//	}

	@Override
	public List<LocalDateTime> createAvailableSlotsDetails(Long doctorId, DoctorTimeTable appointmentSlot) {
		Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found...!!!"));
		
		// free / booked slots : true / false
		Map<LocalDateTime, Boolean> slotMap = new HashMap<>();
		
		List<LocalDateTime> slotList = new ArrayList<>();
		
		LocalDate startDate = appointmentSlot.getStartDate();
		LocalDate endDate = appointmentSlot.getEndDate();
		LocalTime startTime = appointmentSlot.getStartTime();
		LocalTime endTime = appointmentSlot.getEndTime();
		LocalTime breakTime = appointmentSlot.getBreakTime();
		int slotDuration = appointmentSlot.getSlotDuration();
		//int breatDuration = appointmentSlot.getBreakDuration();

		List<String> holidays = new ArrayList<>(appointmentSlot.getHolidays());

		for(int i=0;i<holidays.size();i++) {
			String str = holidays.get(i);
			holidays.set(i, str.toUpperCase());
		}

//		int size = holidays.size();
//		System.out.println("Holidays size "+holidays.size()+" Holidays : "+holidays);
		
		Period period = Period.between(startDate, endDate);
		//get count of total days [start to end]
		int days = period.getDays();
		System.out.println("*****Days before : "+days);

		days = days +1; //last date is excluded so add 1 later
		System.out.println("*****Days : "+days);
		
		int totalMinutes = (int)ChronoUnit.MINUTES.between(startTime, endTime);
		int slots = (totalMinutes / slotDuration);
		System.out.println("*****Slots : "+slots);
				
		//get total count of slots
		int totalSlots = slots * days;
		System.out.println("******Total slots : "+totalSlots);
		
		long addDate = 0;
		long addTime = 0;
		
		System.out.println("******Slot duration : "+slotDuration);
		
		System.out.println("*****break time : "+breakTime);
		for(int i = 0; i<totalSlots ; i++) {
		
			if(i % (slots) == 0 && i!=0) {
				addDate++;
				addTime = 0;
			}
			
			LocalDate date = startDate.plusDays(addDate);
			DayOfWeek dayOfWeek = date.getDayOfWeek();
			System.out.println("DAY : "+dayOfWeek);
			
			if(holidays.contains(dayOfWeek.toString())) {
				if(i==0) {
					addDate++;
				}
				i--;
				totalSlots = totalSlots - slots;
				continue;
			}
						
			LocalTime t = startTime.plusMinutes(slotDuration * (addTime++));
			//System.out.println("t : "+t);
			if(t.equals(breakTime)) {
				slotMap.put(LocalDateTime.of(startDate.plusDays(addDate),t) , false);				
			} else {
				slotMap.put(LocalDateTime.of(startDate.plusDays(addDate),t) , true);
				slotList.add(LocalDateTime.of(startDate.plusDays(addDate),t));
			}
		}
	
		System.out.println("#MAP : ");
		for(Map.Entry<LocalDateTime, Boolean> mapEntry : slotMap.entrySet()) {
			System.out.println("Key : "+mapEntry.getKey()+" , val : "+ mapEntry.getValue() );
		}

		System.out.println("#List : ");
		slotList.forEach(System.out::println);
		
		//DoctorTimeTable timeTable = new DoctorTimeTable(startTime, endTime, slotDuration, breakTime, breatDuration);
		//(doctor.getTimeSlot()).setAvailableSlots(slotMap);
		appointmentSlot.setAvailableSlots(slotMap);
		DoctorTimeTable timeTable = doctorTimeTableRepo.save(appointmentSlot);
		doctor.setTimeSlot(timeTable);
		
		return slotList;
	}

	public void makeSlotsAvailable(Long appoitmentId) {
		
		//get appointment from appointmentId
		Appointment appointment = appointmentRepo.findById(appoitmentId).orElseThrow(() -> new UserHandlingException("Invalid appointment Id..."));
	
		//get appointmentTime
		LocalDateTime time = appointment.getAppointmentTime();
		
		//get doctor data w.r.t appointmentId
		Doctor doctor = appointment.getDoctor();
		
		//get availableSlots list of doctor
		Map<LocalDateTime, Boolean> availableSlots = doctor.getTimeSlot().getAvailableSlots();
		//System.out.println("******* slots : "+availableSlots);
		
		//System.out.println("!!!!!!!!!!!!!! Slot before updating (true/false) : "+availableSlots.get(time));
		
		//make that slot available again before deleting patient
		availableSlots.put(time, true);
		
		//System.out.println("******* slots : "+availableSlots);
		//System.out.println("!!!!!!!!!!!!!! Slot after updating (true/false) : "+availableSlots.get(time));
		
	}

	@Override
	public Doctor getDoctorDetails(Long id) {
		return doctorRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctor with id: " + id + " doesn't exist."));
	}
}