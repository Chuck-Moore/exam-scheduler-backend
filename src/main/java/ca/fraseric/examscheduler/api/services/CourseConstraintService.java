package ca.fraseric.examscheduler.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.fraseric.examscheduler.api.entities.CourseConstraintEntity;
import ca.fraseric.examscheduler.api.repositories.CourseConstraintRepository;

@Service
public class CourseConstraintService {
    
    @Autowired
    private CourseConstraintRepository repo;

    public List<CourseConstraintEntity> getAll() {
        return repo.findAll();
    }

    public List<CourseConstraintEntity> getConstraintsByCourseCode(String courseCode) {
        return repo.findByIdCourseCode(courseCode);
    }

    public List<CourseConstraintEntity> getConstraintsByConstraintId(long constraintId) {
        return repo.findByIdConstraintId(constraintId);
    }

    public CourseConstraintEntity saveConstraint(CourseConstraintEntity newConstraint) {
        return repo.save(newConstraint);
    }

    public List<CourseConstraintEntity> saveConstraintList(List<CourseConstraintEntity> constraintList) {
        return repo.saveAll(constraintList);
    }

    public void deleteConstraint(CourseConstraintEntity constraint) {
        repo.delete(constraint);
    }

    public void deleteByCourseCode(String courseCode) {
        repo.deleteByIdCourseCode(courseCode);
    }

    public void deleteByConstraintId(long constraintId) {
        repo.deleteByIdConstraintId(constraintId);
    }

}
