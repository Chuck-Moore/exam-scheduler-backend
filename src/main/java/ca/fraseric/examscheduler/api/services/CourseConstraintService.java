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

    public List<CourseConstraintEntity> getConstraintsByCourseId(String courseId) {
        //TODO: implement
        return null;
    }

    //TODO: implement other methods
}
