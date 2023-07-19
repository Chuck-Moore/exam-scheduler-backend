package ca.fraseric.examscheduler.repoTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ca.fraseric.examscheduler.api.entities.RequestEntity;
import ca.fraseric.examscheduler.api.repositories.RequestRepository;

@DataJpaTest
public class RequestRepositoryTest {
    
    @Autowired
    private RequestRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findById_thenReturnRequest() {
        RequestEntity request = createRequest();

        entityManager.persist(request);
        entityManager.flush();

        RequestEntity found = repo.findById(request.getRequestId()).orElse(null);
        if (found == null) {
            assertFalse(true);
        }

        assertThat(found.getRequestId())
            .isEqualTo(request.getRequestId());
    }

    @Test
    public void findByInstructorId_thenReturnRequest() {
        RequestEntity request = createRequest();
        RequestEntity request2 = createRequest2();
        RequestEntity request3 = createRequest3();

        entityManager.persist(request);
        entityManager.persist(request2);
        entityManager.persist(request3);
        entityManager.flush();

        List<RequestEntity> list = repo.findByInstructorId("123456789");

        assertThat(list)
            .contains(request);
        assertThat(list)
            .contains(request2);
        assertThat(list)
            .doesNotContain(request3);
        assertThat(list.size() == 2);
    }

    private RequestEntity createRequest() {
        RequestEntity request = new RequestEntity();
        request.setCourseCode("CMPT276");
        request.setInstructorId("123456789");
        request.setStudentCount(150);
        List<ZonedDateTime> isoDatePrefs = new ArrayList<ZonedDateTime>();
        isoDatePrefs.add(ZonedDateTime.now());
        isoDatePrefs.add(ZonedDateTime.parse("2020-12-03T10:15:30+01:00"));
        isoDatePrefs.add(ZonedDateTime.parse("2020-12-04T10:15:30+01:00"));
        request.setIsoDatePrefs(isoDatePrefs);
        request.setIsoDuration(Duration.ofHours(2));
        return request;
    }

    private RequestEntity createRequest2() {
        RequestEntity request = new RequestEntity();
        request.setCourseCode("CMPT300");
        request.setInstructorId("123456789");
        request.setStudentCount(150);
        List<ZonedDateTime> isoDatePrefs = new ArrayList<ZonedDateTime>();
        isoDatePrefs.add(ZonedDateTime.now());
        isoDatePrefs.add(ZonedDateTime.parse("2020-12-03T10:15:30+01:00"));
        isoDatePrefs.add(ZonedDateTime.parse("2020-12-04T10:15:30+01:00"));
        request.setIsoDatePrefs(isoDatePrefs);
        request.setIsoDuration(Duration.ofHours(2));
        return request;
    }

    private RequestEntity createRequest3() {
        RequestEntity request = new RequestEntity();
        request.setCourseCode("CMPT276");
        request.setInstructorId("987654321");
        request.setStudentCount(150);
        List<ZonedDateTime> isoDatePrefs = new ArrayList<ZonedDateTime>();
        isoDatePrefs.add(ZonedDateTime.now());
        isoDatePrefs.add(ZonedDateTime.parse("2020-12-03T10:15:30+01:00"));
        isoDatePrefs.add(ZonedDateTime.parse("2020-12-04T10:15:30+01:00"));
        request.setIsoDatePrefs(isoDatePrefs);
        request.setIsoDuration(Duration.ofHours(2));
        return request;
    }
}
