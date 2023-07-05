package ca.fraseric.examscheduler.api.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.fraseric.examscheduler.api.entities.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, UUID>{

    List<RequestEntity> findByInstructorId(String instructorId);
    
}
