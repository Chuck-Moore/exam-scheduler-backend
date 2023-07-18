package ca.fraseric.examscheduler.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.fraseric.examscheduler.api.entities.CompositeCourseConstraintId;
import ca.fraseric.examscheduler.api.entities.CourseConstraintEntity;

import java.util.List;

public interface CourseConstraintRepository extends JpaRepository<CourseConstraintEntity, CompositeCourseConstraintId> {

    List<CourseConstraintEntity> findByIdCourseCode(String courseCode);

    List<CourseConstraintEntity> findByIdConstraintId(long constraintId);

    long deleteByIdCourseCode(String courseCode);

    long deleteByIdConstraintId(long constraintId);
}
