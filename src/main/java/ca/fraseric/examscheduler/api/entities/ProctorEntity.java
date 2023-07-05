package ca.fraseric.examscheduler.api.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;
    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;
    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;
    @ManyToMany
    private List<ExamEntity> exams;
}