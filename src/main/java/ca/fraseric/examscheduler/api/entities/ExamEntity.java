package ca.fraseric.examscheduler.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.Duration;
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
    @NotNull
    private ZonedDateTime startDateTime;
    @NotNull
    private Duration isoDuration;
    @NotBlank
    private String instructorId;
    @ElementCollection
    private List<String> location;
    @ManyToMany
    private List<ProctorEntity> proctorsRequested;
    @ManyToMany
    private List<ProctorEntity> proctorsConfirmed;
}
