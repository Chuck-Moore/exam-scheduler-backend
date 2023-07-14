package ca.fraseric.examscheduler.api.entities;

import java.time.ZonedDateTime;

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
public class TimeInterval {
    private ZonedDateTime start;
    private ZonedDateTime end;
}
