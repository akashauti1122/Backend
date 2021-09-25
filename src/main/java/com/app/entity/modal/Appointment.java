package com.app.entity.modal;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="appointment_tbl")
public class Appointment extends BaseEntity{

	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	private LocalDateTime appointmentTime;

	private String appointmentType = "CLINIC_VISIT";

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="doctor_id", nullable =  false)
//	@JsonIgnore
	//@JsonProperty(value = "doctor")
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id", nullable =  false)
//	@JsonIgnore
	private Patient patient;
	
	public Appointment() {
		System.out.println("In Appointment constr");
	}

	public Appointment(LocalDateTime appointmentTime, Doctor doctor, Patient patient) {
		super();
		this.appointmentTime = appointmentTime;
		this.doctor = doctor;
		this.patient = patient;
	}

	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getAppointmentType() {
		return appointmentType;
	}
	
	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}

	//@JsonProperty
	public Doctor getDoctor() {
		return doctor;
	}

	//@JsonIgnore
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	//@JsonProperty
	public Patient getPatient() {
		return patient;
	}
	
	//@JsonIgnore
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentTime=" + appointmentTime + ", appointmentType=" + appointmentType +"]";
	}

	


}
