package ca.fraseric.examscheduler.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ca.fraseric.examscheduler.api.entities.CourseConstraintEntity;
import ca.fraseric.examscheduler.api.services.CourseConstraintService;

@RestController
public class CourseConstraintController {

    @Autowired
    private CourseConstraintService service;
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/courseConstraint/{courseCode}")
    public List<CourseConstraintEntity> getCourseConstraints(@PathVariable String courseCode) {
        return service.getConstraintsByCourseCode(courseCode);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/courseConstraint")
    public List<CourseConstraintEntity> getCoursesByConstarintId(@RequestParam String constraintId) {
        return service.getConstraintsByConstraintId(Long.parseLong(constraintId));
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/courseConstraint")
    public CourseConstraintEntity postCourseConstraint(@RequestBody CourseConstraintEntity newCourseConstraint) {
        return service.saveConstraint(newCourseConstraint);
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/courseConstraint", params = "multiple")
    public List<CourseConstraintEntity> postCourseConstraintList(@RequestBody List<CourseConstraintEntity> newCourseConstraints, @RequestParam String multiple) {
        if(multiple == null || !multiple.equals("true")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "multiple must be true");
        }
        return service.saveConstraintList(newCourseConstraints);
    }

    @Secured ("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/courseConstraint")
    public void deleteCourseConstraint(@RequestBody CourseConstraintEntity courseConstraint) {
        service.deleteConstraint(courseConstraint);
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/courseConstraint/{courseCode}")
    public void deleteCourseConstraintsByCourseCode(@PathVariable String courseCode) {
        service.deleteByCourseCode(courseCode);
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/courseConstraint", params = "constraintId")
    public void deleteCourseConstraintsByConstraitId(@RequestParam String constraintId) {
        service.deleteByConstraintId(Long.parseLong(constraintId));
    }

}
