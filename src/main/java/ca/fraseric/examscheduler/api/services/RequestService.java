package ca.fraseric.examscheduler.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.fraseric.examscheduler.api.entities.RequestEntity;
import ca.fraseric.examscheduler.api.repositories.RequestRepository;

@Service
public class RequestService {
    
    @Autowired
    private RequestRepository repo;

    public List<RequestEntity> getRequestsByInstructorId(String instructorId) {
        return repo.findByInstructorId(instructorId);
    }

    public RequestEntity getRequestById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public RequestEntity saveRequest(RequestEntity newRequest) {
        return repo.save(newRequest);
    }

    public void deleteRequestById(UUID id) {
        repo.deleteById(id);
    }
}
