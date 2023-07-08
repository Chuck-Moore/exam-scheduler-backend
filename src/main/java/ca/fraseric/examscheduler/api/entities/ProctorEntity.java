package ca.fraseric.examscheduler.api.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate date;
    @NotNull
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;
    @NotNull
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;
    @ManyToMany
    private List<ExamEntity> exams;
}
