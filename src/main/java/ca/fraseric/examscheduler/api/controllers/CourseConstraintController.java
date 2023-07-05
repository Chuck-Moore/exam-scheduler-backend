package ca.fraseric.examscheduler.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import ca.fraseric.examscheduler.api.repositories.CourseConstraintRepository;

@RestController
public class CourseConstraintController {
    private final CourseConstraintRepository repo;
    public CourseConstraintController(CourseConstraintRepository repo) {
        this.repo = repo;
    }
    
}
