package ca.fraseric.examscheduler.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.fraseric.examscheduler.api.entities.ExamEntity;

public interface ExamRepository extends JpaRepository<ExamEntity, UUID>{
    
}
