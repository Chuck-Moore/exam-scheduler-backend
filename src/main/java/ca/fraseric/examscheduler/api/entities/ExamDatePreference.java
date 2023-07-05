package ca.fraseric.examscheduler.api.entities;

import java.time.Instant;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExamDatePreference {
    private Instant startTime;
    private Instant endTime;
}
