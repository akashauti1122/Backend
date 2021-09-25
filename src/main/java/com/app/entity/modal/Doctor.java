package com.app.entity.modal;

import com.app.entity.dto.DoctorDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "doctor_tbl")
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long doctorId;

    @Column(length = 30)
    @NotBlank(message = "Language must be supplied")
    private String languages;

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
    private List<Appointment> appointement = new ArrayList<>();

    public static Doctor createDoctor(DoctorDto dto) {
        return new Doctor(dto.getLanguages(),
                dto.getSpecialization(),
                dto.getQualification(),
                dto.getBegan_practice(),
                dto.getFees());
    }

    public Doctor(String languages, String specialization, String qualification, LocalDate began_practice, Integer fees) {
        this.languages = languages;
        this.specialization = specialization;
        this.qualification = qualification;
        this.began_practice = began_practice;
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "Doctor [languages=" + languages + ", specialization=" + specialization + ", qualification="
                + qualification + ", began_practice=" + began_practice + ", fees=" + fees + "]";
    }
}
