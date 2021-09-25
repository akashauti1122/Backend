package com.app.entity.modal;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "User first name must be supplied")
	private String firstName;

	@Column(nullable = false)
	@NotBlank(message = "User last name must be supplied")
	private String lastName;

	@Email
	@Column(unique = true, nullable = false)
	@NotBlank(message = "User email must be supplied")
	private String email;

	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(length = 13, nullable = false, unique = true)
	@NotBlank(message = "User mobile required")
	private String mobileNumber;

	@Column(nullable = false)
	private String area;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Enumerated(EnumType.STRING)
	private BloodGroup bloodGroup;

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", dob=" + dob + ", gender=" + gender + ", mobileNumber=" + mobileNumber
				+ ", area=" + area + ", city=" + city + ", state=" + state + "]";
	}
}