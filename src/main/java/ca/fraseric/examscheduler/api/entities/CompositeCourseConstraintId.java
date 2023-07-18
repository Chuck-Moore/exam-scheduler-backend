package ca.fraseric.examscheduler.api.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class CompositeCourseConstraintId implements Serializable{
    @NotNull
    private String courseCode;
    @NotNull
    private long constraintId;
}
