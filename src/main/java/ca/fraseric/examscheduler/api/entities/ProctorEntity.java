package ca.fraseric.examscheduler.api.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @ManyToMany
    private List<ExamEntity> examsPending;
    @ManyToMany
    private List<ExamEntity> examsConfirmed;
}