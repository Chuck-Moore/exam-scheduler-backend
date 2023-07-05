package ca.fraseric.examscheduler.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import ca.fraseric.examscheduler.api.repositories.ExamRepository;

@RestController
public class ExamController {
    private final ExamRepository repo;
    public ExamController(ExamRepository repo) {
        this.repo = repo;
    }
}
