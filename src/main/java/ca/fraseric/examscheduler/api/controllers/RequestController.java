package ca.fraseric.examscheduler.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ca.fraseric.examscheduler.api.entities.RequestEntity;
import ca.fraseric.examscheduler.api.repositories.RequestRepository;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@CrossOrigin()
@RestController
public class RequestController {
    private final RequestRepository repo;
    public RequestController(RequestRepository repo) {
        this.repo = repo;
    }

    @Secured("ROLE_PROF")
    @GetMapping("/examRequest")
    public List<RequestEntity> getExamRequests(Authentication auth) {
        return repo.findByInstructorId(auth.getName());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/examRequest/{id}")
    public RequestEntity getExamRequestById(@PathVariable String id) {
        return repo.findById(UUID.fromString(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "request not found"));
    }

    @Secured("ROLE_PROF")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/examRequest")
    public RequestEntity postExamRequest(@RequestBody RequestEntity newRequest, Authentication auth) {
        if(newRequest.getInstructorId() == null) {
            newRequest.setInstructorId(auth.getName());
        }
        else if(!newRequest.getInstructorId().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong instructorId");
        }

        return repo.save(newRequest);
    }

    @Secured({"ROLE_PROF","ROLE_ADMIN"})
    @PutMapping("/examRequest/{id}")
    public ResponseEntity<RequestEntity> putExamRequest(@RequestBody RequestEntity updatededEntity, @PathVariable String id) {
        if(!id.equals(updatededEntity.getRequestId().toString())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id mismatch");
        }
        if(!repo.existsById(UUID.fromString(id))) {
            URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(updatededEntity.getRequestId()).toUri();
            return ResponseEntity.created(location).body(repo.save(updatededEntity));
        }

        return ResponseEntity.ok(repo.save(updatededEntity));
    }

    @Secured({"ROLE_PROF","ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/examRequest/{id}")
    public void deleteExamRequest(@PathVariable String id) {
        repo.deleteById(UUID.fromString(id));
    }
}
