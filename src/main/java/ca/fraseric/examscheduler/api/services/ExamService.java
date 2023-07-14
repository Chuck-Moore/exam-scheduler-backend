package ca.fraseric.examscheduler.api.services;

import java.util.UUID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.fraseric.examscheduler.api.entities.ExamEntity;
import ca.fraseric.examscheduler.api.repositories.ExamRepository;

@Service
public class ExamService {
    
    @Autowired
    private ExamRepository repo;

    public ExamEntity getExamById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public List<ExamEntity> getExamsByInstructorId(String instructorId) {
        return repo.findByInstructorId(instructorId);
    }

    public List<ExamEntity> getExamsByCourseCode(String courseCode) {
        return repo.findByCourseCode(courseCode);
    }

    public ExamEntity saveExam(ExamEntity newExam) {
        return repo.save(newExam);
    }

    public void deleteExamById(UUID id) {
        repo.deleteById(id);
    }
}
