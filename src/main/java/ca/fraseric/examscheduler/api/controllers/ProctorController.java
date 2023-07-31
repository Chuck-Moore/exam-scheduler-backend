package ca.fraseric.examscheduler.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ca.fraseric.examscheduler.api.entities.ProctorEntity;
import ca.fraseric.examscheduler.api.services.ProctorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
public class ProctorController {
    
    @Autowired
    private ProctorService service;

    @Secured({"ROLE_PROCTOR","ROLE_ADMIN"})
    @GetMapping("/proctor/{id}")
    public ProctorEntity getProctorById(@PathVariable String id) {
        return service.getProctorById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "request not found"));
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value="/proctor")
    public ProctorEntity postMethodName(@RequestBody ProctorEntity newProctor) {
        return service.saveProctor(newProctor);
    }
    
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/proctor/{id}")
    public void deleteProctorById(@PathVariable String id) {
        service.deleteProctorById(id);
    }
}
