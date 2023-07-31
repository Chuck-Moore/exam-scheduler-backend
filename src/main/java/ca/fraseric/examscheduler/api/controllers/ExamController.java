package ca.fraseric.examscheduler.api.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ca.fraseric.examscheduler.api.entities.ExamEntity;
import ca.fraseric.examscheduler.api.services.ExamService;

@RestController
public class ExamController {

    @Autowired
    private ExamService service;

    @Secured("ROLE_PROF")
    @GetMapping("/exams")
    public List<ExamEntity> getAllExamsProf(Authentication auth) {
        return service.getExamsByInstructorId(auth.getName());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/exams" , params = "getAll")
    public List<ExamEntity> getAllExams() {
        return service.getAllExams();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/exams", params = "instructorId")
    public List<ExamEntity> getExamsByInstructorId(@RequestParam String instructorId) {
        return service.getExamsByInstructorId(instructorId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/exams", params = "courseCode")
    public List<ExamEntity> getExamsByCourseCode(@RequestParam String courseCode) {
        return service.getExamsByCourseCode(courseCode);
    }

    @Secured({"ROLE_PROF","ROLE_ADMIN","ROLE_PROCTOR"})
    @GetMapping(value = "/exams", params = "date")
    public List<ExamEntity> getExamsByDate(@RequestParam String date) {
        return service.getExamsByDate(LocalDate.parse(date));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/exams/{id}")
    public ExamEntity getExamById(@PathVariable String id) {
        return service.getExamById(UUID.fromString(id));
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/exams")
    public ExamEntity postExam(@RequestBody ExamEntity newExam) {
        return service.saveExam(newExam);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/exams/{id}")
    public ResponseEntity<ExamEntity> updateExam(@PathVariable String id, @RequestBody ExamEntity newExam) {
        if(!id.equals(newExam.getExamId().toString())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id mismatch");
        }
        if(!service.existsById(UUID.fromString(id))) {
            URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newExam.getExamId()).toUri();
            return ResponseEntity.created(location).body(service.saveExam(newExam));
        }
        return ResponseEntity.ok(service.saveExam(newExam));
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/exams/{id}")
    public void deleteExam(@PathVariable String id) {
        service.deleteExamById(UUID.fromString(id));
    }
}
