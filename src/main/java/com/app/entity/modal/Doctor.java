package com.app.entity.modal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.app.entity.dto.DoctorDto;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "doctor_tbl")
public class Doctor extends User {

    @Column(length = 30)
    @NotBlank(message = "Language must be supplied")
    private String languages;
    // hi, en
    // hi, en, ru
    // [doctorDetails.setLanguages(doctorDetails.getLanguages() + ", ru")]

    @Column(length = 30)
    @NotBlank(message = "specialization must be supplied")
    private String specialization;

    @Column(length = 30)
    @NotBlank(message = "qualification must be supplied")
    private String qualification;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate began_practice;

    private Integer fees;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private DoctorTimeTable timeSlot;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    //@JsonProperty(value = "appointement")
    private List<Appointment> appointement = new ArrayList<>();

    public Doctor() {
        System.out.println("In Doctor constr ");
    }

    public static Doctor createDoctor(DoctorDto dto) {
        return new Doctor(dto.getLanguages(),
                dto.getSpecialization(),
                dto.getQualification(),
                dto.getBegan_practice(),
                dto.getUsername(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getDob(),
                dto.getGender(),
                dto.getMobileNumber(),
                dto.getArea(),
                dto.getCity(),
                dto.getState(),
                dto.getFees());
    }

    public Doctor(@NotBlank(message = "Language must be supplied") String languages,
                  @NotBlank(message = "specialization must be supplied") String specialization,
                  @NotBlank(message = "qualification must be supplied") String qualification, LocalDate began_practice,
                  @NotBlank(message = "User name must be supplied") String username,
                  @NotBlank(message = "User first name must be supplied") String firstName,
                  @NotBlank(message = "User last name must be supplied") String lastName,
                  @NotBlank(message = "User email must be supplied") String email,
                  @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})", message = "Blank or Invalid password") String password,
                  LocalDate dob, Gender gender, @NotBlank(message = "User password required") String mobileNumber,
                  String area, String city, String state, Integer fees) {
        super(username, firstName, lastName, email, password, dob, gender, mobileNumber, area, city, state);
        this.languages = languages;
        this.specialization = specialization;
        this.qualification = qualification;
        this.began_practice = began_practice;
        this.fees = fees;
    }


    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public LocalDate getBegan_practice() {
        return began_practice;
    }

    public void setBegan_practice(LocalDate began_practice) {
        this.began_practice = began_practice;
    }

    public Integer getFees() {
        return fees;
    }

    public void setFees(Integer fees) {
        this.fees = fees;
    }

    //@JsonProperty // to ignore property during deserialization , but dont ignore during serialization
    public DoctorTimeTable getTimeSlot() {
        return timeSlot;
    }

    //@JsonIgnore // to ignore this property during serilization and deserialization
    public void setTimeSlot(DoctorTimeTable timeSlot) {
        this.timeSlot = timeSlot;
    }

    //@JsonProperty
    public List<Appointment> getAppointement() {
        return appointement;
    }

    //@JsonIgnore
    public void setAppointement(List<Appointment> appointement) {
        this.appointement = appointement;
    }

    @Override
    public String toString() {
        return "Doctor [languages=" + languages + ", specialization=" + specialization + ", qualification="
                + qualification + ", began_practice=" + began_practice + ", fees=" + fees + "]";
    }

}
