package ca.fraseric.examscheduler.repoTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ca.fraseric.examscheduler.api.entities.ExamEntity;
import ca.fraseric.examscheduler.api.repositories.ExamRepository;

@DataJpaTest
public class ExamRepositoryTest {
    
    @Autowired
    private ExamRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByInstructorId_thenReturnExam() {
        ExamEntity exam = createExam();

        entityManager.persist(exam);
        entityManager.flush();

        assertThat(repo.findByInstructorId("123456789"))
            .contains(exam);
    }

    @Test
    public void findByStartDateTimeBetween_thenReturnExam() {
        ExamEntity exam = createExam();
        ExamEntity exam2 = createExam2();

        entityManager.persist(exam);
        entityManager.persist(exam2);
        entityManager.flush();

        List<ExamEntity> list = repo.findByStartDateTimeBetween(ZonedDateTime.parse("2020-12-01T07:00:00.000+00:00"), ZonedDateTime.parse("2020-12-01T09:00:00.000+00:00"));

        assertThat(list)
            .contains(exam);
        assertThat(list)
            .doesNotContain(exam2);
    }

    private ExamEntity createExam() {
        ExamEntity exam = new ExamEntity();
        exam.setCourseCode("CMPT276");
        exam.setStartDateTime(ZonedDateTime.parse("2020-12-01T08:00:00.000+00:00"));
        exam.setInstructorId("123456789");
        List<String> location = new ArrayList<String>();
        location.add("ABC123");
        exam.setLocation(location);
        exam.setIsoDuration(Duration.ofHours(1));
        return exam;
    }

    private ExamEntity createExam2() {
        ExamEntity exam = new ExamEntity();
        exam.setCourseCode("CMPT300");
        exam.setStartDateTime(ZonedDateTime.parse("2020-12-01T00:00:00.000+00:00"));
        exam.setInstructorId("123456789");
        List<String> location = new ArrayList<String>();
        location.add("ABC123");
        exam.setLocation(location);
        exam.setIsoDuration(Duration.ofHours(1));
        return exam;
    }
}
