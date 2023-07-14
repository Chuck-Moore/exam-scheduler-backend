package ca.fraseric.examscheduler.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.fraseric.examscheduler.api.entities.ProctorEntity;

public interface ProctorRepository extends JpaRepository<ProctorEntity, String>{
    
}
