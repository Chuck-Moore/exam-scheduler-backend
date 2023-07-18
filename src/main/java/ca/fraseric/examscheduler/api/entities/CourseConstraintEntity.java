package ca.fraseric.examscheduler.api.entities;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CourseConstraint")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class CourseConstraintEntity {
    @EmbeddedId
    @JsonUnwrapped
    private CompositeCourseConstraintId id;
}
