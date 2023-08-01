package ca.fraseric.examscheduler.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Request")
@Getter
@Setter
@NoArgsConstructor
public class RequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Setter(AccessLevel.NONE)
  private UUID requestId;
  @NotBlank
  private String courseCode;
  @NotBlank
  private String instructorId;
  @Range(min = 1, max = 1000)
  private int studentCount;
  @NotNull
  @OrderColumn
  @Size(min = 3, max = 3)
  @ElementCollection(fetch = FetchType.EAGER)
  private List<ZonedDateTime> isoDatePrefs;
  @NotNull
  private Duration isoDuration;
}
