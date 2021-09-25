package com.app.entity.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name="appointment_tbl")
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	private LocalDateTime appointmentTime;

	private String appointmentType = "CLINIC_VISIT";

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="doctor_id", nullable =  false)

	private Doctor doctor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id", nullable =  false)
	private User patient;

	public Appointment(LocalDateTime appointmentTime, Doctor doctor, User patient) {
		super();
		this.appointmentTime = appointmentTime;
		this.doctor = doctor;
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentTime=" + appointmentTime + ", appointmentType=" + appointmentType +"]";
	}
}
