package ca.fraseric.examscheduler.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import ca.fraseric.examscheduler.api.repositories.ProctorRepository;

@RestController
public class ProctorController {
    private final ProctorRepository repo;
    public ProctorController(ProctorRepository repo) {
        this.repo = repo;
    }
}
