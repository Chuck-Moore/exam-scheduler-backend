package ca.fraseric.examscheduler.api.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CourseConstraint")
@Getter
@Setter
@NoArgsConstructor 
public class CourseConstraintEntity {
    @EmbeddedId
    private CompositeCourseConstraintId id;
}
