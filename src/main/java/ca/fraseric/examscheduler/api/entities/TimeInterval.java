package ca.fraseric.examscheduler.api.entities;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
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
    @Column(name = "start_time")
    private ZonedDateTime start;
    @Column(name = "end_time")
    private ZonedDateTime end;
}
