package ca.fraseric.examscheduler.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.fraseric.examscheduler.api.entities.CourseConstraintEntity;

import java.util.UUID;

public interface CourseConstraintRepository extends JpaRepository<CourseConstraintEntity, UUID> {
}
