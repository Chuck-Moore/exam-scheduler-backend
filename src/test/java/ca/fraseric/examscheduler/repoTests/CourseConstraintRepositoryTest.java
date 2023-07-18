package ca.fraseric.examscheduler.repoTests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ca.fraseric.examscheduler.api.entities.CompositeCourseConstraintId;
import ca.fraseric.examscheduler.api.entities.CourseConstraintEntity;
import ca.fraseric.examscheduler.api.repositories.CourseConstraintRepository;

@DataJpaTest
public class CourseConstraintRepositoryTest {
    @Autowired
    private CourseConstraintRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findById_thenReturnCourseConstraint() {
        CompositeCourseConstraintId id = new CompositeCourseConstraintId("CMPT276", 1);
        CourseConstraintEntity courseConstraint = new CourseConstraintEntity(id);
        entityManager.persist(courseConstraint);
        entityManager.flush();

        CourseConstraintEntity found = repo.findById(id).orElse(null);

        assertThat(found.getId())
            .isEqualTo(courseConstraint.getId());
        assertThat(found.getId().getCourseCode())
            .isEqualTo(courseConstraint.getId().getCourseCode());
        assertThat(found.getId().getConstraintId() == courseConstraint.getId().getConstraintId());
    }

    @Test
    public void findByIdCourseCode_thenReturnCourseConstraint() {
        CompositeCourseConstraintId id = new CompositeCourseConstraintId("CMPT276", 1);
        CourseConstraintEntity courseConstraint = new CourseConstraintEntity(id);
        entityManager.persist(courseConstraint);
        entityManager.flush();

        assertThat(repo.findByIdCourseCode("CMPT276"))
            .contains(courseConstraint);
    }
}
