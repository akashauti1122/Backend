package com.app.entity.dto;

import com.app.entity.modal.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dob;
    private Gender gender;
    private String mobileNumber;
    private String area;
    private String city;
    private String state;
    private String languages;
    private String specialization;
    private String qualification;
    private LocalDate began_practice;
    private Integer fees;
    private String userType;
}
