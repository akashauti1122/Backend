package com.app.service.serviceImpl;

import com.app.entity.modal.Appointment;
import com.app.entity.modal.Doctor;
import com.app.entity.modal.DoctorTimeTable;
import com.app.entity.modal.User;
import com.app.exception.customException.UserHandlingException;
import com.app.repository.AppointmentRepository;
import com.app.repository.DoctorTimeTableRepository;
import com.app.repository.UserRepository;
import com.app.service.serviceInterface.IAppointmentService;
import com.app.service.serviceInterface.IDoctorService;
import com.app.service.serviceInterface.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements IAppointmentService {
    private final AppointmentRepository appointmentRepo;
    private final DoctorTimeTableRepository doctorTimeTableRepo;
    private final UserRepository patientRepo;
    private final IDoctorService doctorService;
    private final IUserService userService;

    @Override
    public String cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() -> new UserHandlingException("appointment Id not found"));
        Doctor doctor = appointment.getDoctor();
        System.out.println("Doctor Id :: " + doctor.getId());
        LocalDateTime appointmentTime = appointment.getAppointmentTime();
        System.out.println("---------->>>" + doctor.getTimeSlot().getAvailableSlots());
        doctor.getTimeSlot().bookAvailableSlot(appointmentTime);
        appointmentRepo.deleteById(appointmentId);
        System.out.println("---------->>>" + doctor.getTimeSlot().getAvailableSlots());
        return "Appointment cancelled successfully(for " + appointmentId + ")...!!!";
    }

    @Override
    public List<Appointment> getAllPatientCurrentAppoitments(Long patientId) {
        return appointmentRepo.findByPatientAndAppointmentTimeAfter(userService.getPatientDetails(patientId), LocalDateTime.now());
    }

    @Override
    public List<Appointment> getAllPatientAppoitmentsHistory(Long patientId) {
        return appointmentRepo.findByPatientAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(userService.getPatientDetails(patientId), LocalDateTime.now());
    }

    @Override
    public List<Appointment> getAllCurrentAppoitmentsForDoctor(Long doctorId) {
        return appointmentRepo.findByDoctorAndAppointmentTimeAfter(doctorService.getDoctorDetails(doctorId), LocalDateTime.now());
    }

    @Override
    public List<Appointment> getPatientAppoitmentsHistoryForDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorService.getDoctorDetails(doctorId);
        User patient = userService.getPatientDetails(patientId);
        return appointmentRepo.findByDoctorAndPatientAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(doctor, patient, LocalDateTime.now());
    }

    @Override
    public List<Appointment> getAllAppoitmentsHistoryForDoctor(Long doctorId) {
        return appointmentRepo.findByDoctorAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(doctorService.getDoctorDetails(doctorId), LocalDateTime.now());
    }

//	@Override
//	public DoctorTimeTable generateTimeTableForDoctor(DoctorTimeTable timeTable, Long doctor_id) {
//		DoctorTimeTable dTimeTable = doctorTimeTableRepo.save(timeTable);
//		//dTimeTable.openSlots();
//		Doctor doctor = doctorRepo.findById(doctor_id).orElseThrow(() -> new UserHandlingException("Doctor not found"));
//		doctor.setTimeSlot(dTimeTable);
//		return dTimeTable;
//	}

    @Override
    public List<LocalDateTime> bookAppointmentForPatient(Long doctorId, Long patientId, LocalDateTime time) {
        System.out.println("\n#################");
        Doctor doctor = doctorService.getDoctorDetails(doctorId);
        User patient = userService.getPatientDetails(patientId);
        DoctorTimeTable timeTable = doctor.getTimeSlot();
        appointmentRepo.save(new Appointment(time, doctor, patient));

        System.out.println("#######Doctor#########" + doctor);
        System.out.println("Doctor List : " + doctor.getTimeSlot().getAvailableSlots());
        System.out.println("#######Patient#########" + patient);
        System.out.println("#######time slot : ########" + timeTable);

        List<LocalDateTime> availableSlotList = timeTable.bookAvailableSlot(time);
        System.out.println("*****Doctors appointment : " + doctor.getAppointement());
        return availableSlotList;
    }

    @Override
    public User getPatientByAppointmentId(Long appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment with id: " + appointmentId + " doesn't exist!!!"));
        User patient = appointment.getPatient();
        System.out.println("*****Patient from app id : " + patient);
        return patient;
    }

//    @Override
//    public List<Appointment> getParticularAppointment(long patientId, LocalDateTime dateTime) {
//        return null;
//    }
}
