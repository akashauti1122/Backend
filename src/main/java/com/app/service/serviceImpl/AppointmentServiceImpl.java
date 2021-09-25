package com.app.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.app.service.serviceInterface.AppointmentServiceIntf;
import com.app.service.serviceInterface.PatientServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.modal.Appointment;
import com.app.entity.modal.Doctor;
import com.app.entity.modal.DoctorTimeTable;
import com.app.entity.modal.Patient;
import com.app.exception.customException.UserHandlingException;
import com.app.repository.AppointmentRepository;
import com.app.repository.DoctorRepository;
import com.app.repository.DoctorTimeTableRepository;
import com.app.repository.PatientRepository;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentServiceIntf {

    @Autowired
    AppointmentRepository appointmentRepo;

    @Autowired
    DoctorTimeTableRepository doctorTimeTableRepo;

    @Autowired
    PatientRepository patientRepo;

    @Autowired
    DoctorRepository doctorRepo;

    @Autowired
    private PatientServiceIntf patientService;

    @Override
    public String cancelAppointment(Long appointmentId) {
        System.out.println("Hello aalo me");
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
        //List<Appointment> appointments = appointmentRepo.getAllPatientCurrentAppoitments(patientId);
        return appointmentRepo.findByPatientAndAppointmentTimeAfter(patientService.getPatientDetails(patientId), LocalDateTime.now());
    }

    @Override
    public List<Appointment> getAllPatientAppoitmentsHistory(Long patientId) {
//        List<Appointment> appointments =  appointmentRepo.getAllPatientAppoitmentsHistory(patientId);
        List<Appointment> appointments = appointmentRepo.findByPatientAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(patientService.getPatientDetails(patientId), LocalDateTime.now());
//        appointments.forEach(System.out::println);
        return appointments;
    }

    @Override
    public List<Appointment> getAllCurrentAppoitmentsForDoctor(Long doctorId) {
        return appointmentRepo.getAllCurrentAppoitmentsForDoctor(doctorId);
    }

    @Override
    public List<Appointment> getPatientAppoitmentsHistoryForDoctor(Long doctorId, Long patientId) {
        return appointmentRepo.getPatientAppoitmentsHistoryForDoctor(doctorId, patientId);
    }

    @Override
    public List<Appointment> getAllAppoitmentsHistoryForDoctor(Long doctorId) {
        return appointmentRepo.getAllAppoitmentsHistoryForDoctor(doctorId);
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
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new UserHandlingException("Doctor not found...!!!"));
        System.out.println("#######Doctor#########" + doctor);

        System.out.println("Doctor List : " + doctor.getTimeSlot().getAvailableSlots());

        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new UserHandlingException("Patient not found...!!!"));
        System.out.println("#######Patient#########" + patient);

        DoctorTimeTable timeTable = doctor.getTimeSlot();
        System.out.println("#######time slot : ########" + timeTable);

        Appointment appointment = new Appointment(time, doctor, patient);
        appointmentRepo.save(appointment);

        List<LocalDateTime> availableSlotList = timeTable.bookAvailableSlot(time);

        System.out.println("*****Doctors appointment : " + doctor.getAppointement());
        System.out.println("*****Patients appointment : " + patient.getAppointement());


        return availableSlotList;
    }

    @Override
    public Patient getPatientByAppointmentId(Long appointmentId) {

        Appointment appointment = appointmentRepo.findById(appointmentId).get();
        Patient patient = appointment.getPatient();
        System.out.println("*****Patient from app id : " + patient);
        return patient;
    }
}
