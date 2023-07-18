package ca.fraseric.examscheduler.api.repositories;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.fraseric.examscheduler.api.entities.ExamEntity;

public interface ExamRepository extends JpaRepository<ExamEntity, UUID>{
    
    List<ExamEntity> findByInstructorId(String instructorId);

    List<ExamEntity> findByCourseCode(String courseCode);

    List<ExamEntity> findByStartDateTimeBetween(ZonedDateTime start, ZonedDateTime end);
}
