package ca.fraseric.examscheduler.api.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.Duration;
import java.time.ZonedDateTime;
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
  @Range(min = 1, max = 1000)
  private int studentCount;
  @Column(nullable = false)
  @ElementCollection
  private List<ZonedDateTime> isoDatePrefs;
  @Column(nullable = false)
  @JsonFormat(pattern = "MILLIS")
  private Duration milliDuration;
}
