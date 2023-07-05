package ca.fraseric.examscheduler.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "Request")
@Getter
@Setter
@NoArgsConstructor
public class RequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false)
  @Setter(AccessLevel.NONE)
  private UUID requestId;
  @NotBlank
  private String courseCode;
  @NotBlank
  private String instructorId;
  @Range(min = 0, max = 300)
  private int studentCount;
  @Column(nullable = false)
  @ElementCollection
  private List<ExamDatePreference> examDatePreferences;
}
