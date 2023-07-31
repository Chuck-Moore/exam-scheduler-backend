package ca.fraseric.examscheduler.api.services;

import java.util.UUID;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.fraseric.examscheduler.api.entities.ExamEntity;
import ca.fraseric.examscheduler.api.repositories.ExamRepository;

@Service
public class ExamService {
    
    @Autowired
    private ExamRepository repo;

    public List<ExamEntity> getAllExams() {
        return repo.findAll();
    }

    public ExamEntity getExamById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public List<ExamEntity> getExamsByInstructorId(String instructorId) {
        return repo.findByInstructorId(instructorId);
    }

    public List<ExamEntity> getExamsByCourseCode(String courseCode) {
        return repo.findByCourseCode(courseCode);
    }

    public List<ExamEntity> getExamsByDate(LocalDate date) {
        ZoneId zone = ZoneId.of("America/Vancouver");
        return repo.findByStartDateTimeBetween(date.atStartOfDay(zone), date.plusDays(1).atStartOfDay(zone));
    }

    public ExamEntity saveExam(ExamEntity newExam) {
        return repo.save(newExam);
    }

    public void deleteExamById(UUID id) {
        repo.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return repo.existsById(id);
    }
}
