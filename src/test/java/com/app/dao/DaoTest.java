package com.app.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.app.entity.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.entity.dto.LoginResponse;
import com.app.entity.modal.Appointment;
import com.app.entity.modal.BloodDonor;
import com.app.entity.modal.BloodGroup;
import com.app.entity.modal.Doctor;
import com.app.entity.modal.DoctorTimeTable;
import com.app.entity.modal.Gender;
import com.app.entity.modal.Patient;
import com.app.repository.BloodDonorRepository;
import com.app.repository.DoctorRepository;
import com.app.repository.PatientRepository;
import com.app.service.serviceInterface.AppointmentServiceIntf;
import com.app.service.serviceInterface.DoctorServiceIntf;
import com.app.service.serviceInterface.HomeServiceIntf;
import com.app.service.serviceInterface.PatientServiceIntf;

@SpringBootTest
class DaoTest {

	@Autowired
	private PatientRepository patientRepo;

	@Autowired
	private DoctorRepository doctorRepo;

	@Autowired
	private DoctorServiceIntf doctorService;
	
	@Autowired
	private PatientServiceIntf patientService;

	@Autowired
	private AppointmentServiceIntf appointmentService;

	@Autowired
	private HomeServiceIntf homeService;
	
	@Autowired
	private BloodDonorRepository bloodDonorRepo;
	@Test
	void savePatient() {

		Patient p1 = new Patient("p3", "p3", "p3", "p3@gmail.com", "p3@123", LocalDate.parse("1995-11-01"), Gender.MALE, "48765", BloodGroup.A_POSITIVE, "Kalyan", "Mumbai", "MH"); 
		Patient p2 = new Patient("p4", "p4", "p4", "p4@gmail.com", "p4@123", LocalDate.parse("1995-11-01"), Gender.FEMALE, "48765", BloodGroup.B_POSITIVE, "Bhosari", "Pune", "MH"); 

		patientRepo.save(p1);
		patientRepo.save(p2);

		assertTrue(true);
	}

	@Test
	void TestGetSpecialization() {

		doctorRepo.getSpecializationsByCity("Pune").forEach(e -> System.out.println(e));
		assertTrue(true);
	}

	@Test
	void TestGetDoctorListBySpecialization() {

		doctorRepo.findAllBySpecializationAndCity("Dentist", "Pune").forEach(e -> System.out.println(e));
		assertTrue(true);
	}

	@Test
	void TestCreateTableTimeSlots() {
		DoctorTimeTable timeTable = new DoctorTimeTable(LocalDate.parse("2021-09-18"), LocalDate.parse("2021-09-27"), LocalTime.parse("07:00:00"), LocalTime.parse("15:00:00"), 30, LocalTime.parse("12:00:00"), new ArrayList<>(Arrays.asList("Saturday", "Sunday")));
		//timeTable.openSlots();
		List<LocalDateTime> list = doctorService.createAvailableSlotsDetails(4l, timeTable);
		System.out.println(list);
		//System.out.println(g.getAvailableSlots());
		assertTrue(true);
	}

	@Test
	void saveDoctor() {
		List<Doctor> doctors = Arrays.asList(
				new Doctor("english", "Dentist", "MBBS", LocalDate.parse("2018-12-12"), "d2", "d2", "d2", "d2@gmail.com", "d2@123", LocalDate.parse("1996-12-12"), Gender.MALE, "123455", "Pimpri", "Pune", "MH",800), 
				new Doctor("english", "Physician", "MBBS", LocalDate.parse("2018-12-12"), "d3", "d3", "d3", "d3@gmail.com", "d3@123", LocalDate.parse("1996-12-12"), Gender.MALE, "123455", "Pimpri", "Pune", "MH",500),
				new Doctor("english", "Dentist", "MBBS", LocalDate.parse("2018-12-12"), "d4", "d4", "d4", "d4@gmail.com", "d4@123", LocalDate.parse("1996-12-12"), Gender.MALE, "123455", "Pimpri", "Mumbai", "MH",650),
				new Doctor("english", "Covid", "MBBS", LocalDate.parse("2018-12-12"), "d5", "d5", "d5", "d5@gmail.com", "d5@123", LocalDate.parse("1996-12-12"), Gender.MALE, "123455", "Pimpri", "Pune", "MH",700));
		
		doctorRepo.saveAll(doctors);
		assertTrue(true);
	}

	@Test
	void getAllDoctorDetail() {
		List<Doctor> allDoctors = doctorService.getAllDoctors();
		allDoctors.forEach(System.out::println);
	}

	@Test
	void deleteDoctorTest() {
		System.out.println(doctorService.deleteDoctorById(3L));
		List<Doctor> allDoctors = doctorService.getAllDoctors();
		allDoctors.forEach(System.out::println);
	}

	@Test
	void authenticateDoctorTest() {
		LoginResponse rs = doctorService.authenticateDoctor(new LoginRequest("a1@gmail.com", "a1@123"));
		System.out.println(rs.getUserFirstName());
	}

	/*@Test
	void saveDoctorTimeTable() {

//		DoctorTimeTable tt = new DoctorTimeTable(LocalTime.parse("07:00:00"), LocalTime.parse("15:00:00"), 30,
//				LocalTime.parse("13:00:00"), 30);
		AppointmentSlot apt = new AppointmentSlot(LocalDate.parse("2021-09-23"), LocalDate.parse("2021-09-30"), LocalTime.parse("08:00:00"), LocalTime.parse("13:00:00"), 20, LocalTime.parse("11:00:00"), 30, new ArrayList<>(Arrays.asList("Saturday", "Sunday")));
		 List<LocalDateTime> list = doctorService.createAvailableSlotsDetails(4l, apt);
		System.out.println(list);
		//System.out.println(g.getAvailableSlots());
		assertTrue(true);
	}*/

	
	@Test
	void authenticateUserTest() {
		LoginResponse authenticateUser = homeService.authenticateUser("sa@gmail.com", "Ak@123");
		System.out.println(authenticateUser);
	}
	
	
	  @Test void bookAppointment() { 
		  LocalDateTime t1 = LocalDateTime.of(LocalDate.parse("2021-09-21"), LocalTime.parse("10:30:00"));
		  List<LocalDateTime> list = appointmentService.bookAppointmentForPatient(4l, 1l, t1);
		  list.forEach(System.out::println);  
	  }	 
	  
	  @Test
	  void getAllPatientCurrApp() {
		  List<Appointment> pApp = appointmentService.getAllPatientCurrentAppoitments(1l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }
	  
	  @Test
	  void getAllPatientAppHistory() {
		  List<Appointment> pApp = appointmentService.getAllPatientAppoitmentsHistory(1l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }

	  @Test
	  void getAllCurrAppForDoctor() {
		  List<Appointment> pApp = appointmentService.getAllCurrentAppoitmentsForDoctor(2l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }

	  @Test
	  void getAllAppHistoryForDoctor() {
		  List<Appointment> pApp = appointmentService.getAllAppoitmentsHistoryForDoctor(2l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }

	  @Test
	  void getAllAppHistoryForOfPatientDoctor() {
		  List<Appointment> pApp = appointmentService.getPatientAppoitmentsHistoryForDoctor(3l, 1l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }

	  @Test
	  void cancelAppointment() {
		  Long id = 1l;
		  System.out.println(appointmentService.cancelAppointment(id));
	  }
	  
	  @Test
	  void deletePatientTest() {
		 System.out.println(patientService.deletePatientById(2L));
	  }

	  @Test
	  void saveDonor() {
		  List<BloodDonor> bd = Arrays.asList(new BloodDonor("bd2", "bd2@gmail.com", "9809789898", BloodGroup.B_POSITIVE, 100, "Pune", "MH"),
				  new BloodDonor("bd3", "bd3@gmail.com", "9809789898", BloodGroup.A_POSITIVE, 150, "Pune", "MH"),
				  new BloodDonor("bd4", "bd4@gmail.com", "9809789898", BloodGroup.B_POSITIVE, 400, "Mumbai", "MH"),
				  new BloodDonor("bd5", "bd5@gmail.com", "9809789898", BloodGroup.A_NEGATIVE, 150, "Mumbai", "MH"));
		  
		  System.out.println(bloodDonorRepo.saveAll(bd));
	  }
	  
	  
	  
}	