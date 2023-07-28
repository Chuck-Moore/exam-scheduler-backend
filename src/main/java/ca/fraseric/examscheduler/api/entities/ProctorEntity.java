package ca.fraseric.examscheduler.api.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@jakarta.persistence.Entity
@Table(name = "ProctorAvailability")
@Getter
@Setter
@NoArgsConstructor
public class ProctorEntity {
    @Id
    @NotBlank
    @Setter(AccessLevel.NONE)
    private String proctorId;
    //private String priority;
    @ElementCollection
    private List<TimeInterval> availableTimes;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "proctorsRequested")
    @JsonIgnoreProperties({"proctorsRequested", "proctorsConfirmed"})
    private Set<ExamEntity> examsPending = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "proctorsConfirmed")
    @JsonIgnoreProperties({"proctorsRequested", "proctorsConfirmed"})
    private Set<ExamEntity> examsConfirmed = new HashSet<>();
}