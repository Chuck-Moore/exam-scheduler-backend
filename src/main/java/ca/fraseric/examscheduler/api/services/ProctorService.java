package ca.fraseric.examscheduler.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.fraseric.examscheduler.api.entities.ProctorEntity;
import ca.fraseric.examscheduler.api.repositories.ProctorRepository;

@Service
public class ProctorService {

    @Autowired
    private ProctorRepository repo;
    
    public ProctorEntity getProctorById(String id) {
        return repo.findById(id).orElse(null);
    }

    public ProctorEntity saveProctor(ProctorEntity newProctor) {
        return repo.save(newProctor);
    }

    public void deleteProctorById(String id) {
        repo.deleteById(id);
    }
}
