package ca.fraseric.examscheduler.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "Exam")
@Getter
@Setter
@NoArgsConstructor
public class ExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @Setter(AccessLevel.NONE)
    private UUID examId;
    @NotBlank
    @NotNull
    private String courseCode;
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate date; // how to set valid range, maybe within the term?
    @NotNull
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;
    @NotNull
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;
    @NotBlank
    private String instructorId;
    @ElementCollection
    private List<String> location;
    @ManyToMany
    private List<ProctorEntity> proctors;
}
