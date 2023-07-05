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
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID examId;
    @NotBlank
    @Column(unique = true)
    private String courseCode;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date; // how to set valid range, maybe within the term?
    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;
    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;
    @NotBlank
    private String instructorId;
    @ElementCollection
    private List<String> location;
    @ManyToMany
    private List<ProctorEntity> proctors;
}
