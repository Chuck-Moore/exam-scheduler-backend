package ca.fraseric.examscheduler.serviceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ca.fraseric.examscheduler.api.entities.CompositeCourseConstraintId;
import ca.fraseric.examscheduler.api.entities.CourseConstraintEntity;
import ca.fraseric.examscheduler.api.entities.ExamEntity;
import ca.fraseric.examscheduler.api.entities.RequestEntity;
import ca.fraseric.examscheduler.api.entities.RoomEntity;
import ca.fraseric.examscheduler.api.repositories.ExamRepository;
import ca.fraseric.examscheduler.api.services.CourseConstraintService;
import ca.fraseric.examscheduler.api.services.ExamService;
import ca.fraseric.examscheduler.api.services.RequestService;
import ca.fraseric.examscheduler.api.services.RoomService;

@SpringBootTest
public class ExamServiceTest {
    
    @Autowired
    private ExamService examService;

    @MockBean
    private ExamRepository repo;

    @MockBean
    private RequestService requestService;
    @MockBean
    private RoomService roomService;
    @MockBean
    private CourseConstraintService courseConstraintService;

    @Test
    public void whenTwoRequestsHaveSameCourseCode_thenScheduleOneExam() throws Exception {

        given(requestService.getAll()).willReturn(createRequestsSameCourseCode());
        given(roomService.getRoomsByType(RoomEntity.RoomType.CMPT)).willReturn(createRooms());
        given(courseConstraintService.getConstraintsByCourseCode("CMPT276")).willReturn(new ArrayList<CourseConstraintEntity>());

        List<ExamEntity> exams = examService.buildSchedule();

        assertThat(exams.size()).isEqualTo(1);
        assertThat(exams.get(0).getCourseCode()).isEqualTo("CMPT276");
        assertThat(exams.get(0).getLocations().size()).isEqualTo(4);
    }

    @Test
    public void whenCoursesHaveSameConstraint_thenScheduleExamsApart() throws Exception {
        given(requestService.getAll()).willReturn(createRequestsSameConstraint());
        given(roomService.getRoomsByType(RoomEntity.RoomType.CMPT)).willReturn(createRooms());
        given(courseConstraintService.getConstraintsByCourseCode("CMPT276")).willReturn(addConstraint());
        given(courseConstraintService.getConstraintsByCourseCode("CMPT200")).willReturn(addConstraint());
        given(courseConstraintService.getConstraintsByConstraintId(1)).willReturn(addConstraint());

        List<ExamEntity> exams = examService.buildSchedule();

        assertThat(exams.size()).isEqualTo(2);
        assertThat(exams.get(0).getCourseCode()).isNotEqualTo(exams.get(1).getCourseCode());
        assertThat(exams.get(0).getStartDateTime()).isNotEqualTo(exams.get(1).getStartDateTime());
    }

    private List<RequestEntity> createRequestsSameCourseCode() {
        List<RequestEntity> requests = new ArrayList<RequestEntity>();

        RequestEntity request = new RequestEntity();
        request.setCourseCode("CMPT276");
        request.setInstructorId("123456789");
        request.setStudentCount(40);
        request.setIsoDuration(Duration.ofHours(1));
        request.setIsoDatePrefs(Arrays.asList(ZonedDateTime.parse("2020-12-01T08:00:00.000+00:00"),
            ZonedDateTime.parse("2020-12-02T08:00:00.000+00:00"),
            ZonedDateTime.parse("2020-12-03T08:00:00.000+00:00")));
        
        RequestEntity request2 = new RequestEntity();
        request2.setCourseCode("CMPT276");
        request2.setInstructorId("123456789");
        request2.setStudentCount(40);
        request2.setIsoDuration(Duration.ofHours(1));
        request2.setIsoDatePrefs(Arrays.asList(ZonedDateTime.parse("2020-12-01T08:00:00.000+00:00"),
            ZonedDateTime.parse("2020-12-02T08:00:00.000+00:00"),
            ZonedDateTime.parse("2020-12-03T08:00:00.000+00:00")));

        requests.add(request);
        requests.add(request2);
        return requests;
    }

    private List<RequestEntity> createRequestsSameConstraint() {
        List<RequestEntity> requests = new ArrayList<RequestEntity>();

        RequestEntity request = new RequestEntity();
        request.setCourseCode("CMPT276");
        request.setInstructorId("123456789");
        request.setStudentCount(40);
        request.setIsoDuration(Duration.ofHours(1));
        request.setIsoDatePrefs(Arrays.asList(ZonedDateTime.parse("2020-12-01T08:00:00.000+00:00"),
            ZonedDateTime.parse("2020-12-02T08:00:00.000+00:00"),
            ZonedDateTime.parse("2020-12-03T08:00:00.000+00:00")));
        
        RequestEntity request2 = new RequestEntity();
        request2.setCourseCode("CMPT200");
        request2.setInstructorId("123456789");
        request2.setStudentCount(40);
        request2.setIsoDuration(Duration.ofHours(1));
        request2.setIsoDatePrefs(Arrays.asList(ZonedDateTime.parse("2020-12-01T08:00:00.000+00:00"),
            ZonedDateTime.parse("2020-12-02T08:00:00.000+00:00"),
            ZonedDateTime.parse("2020-12-03T08:00:00.000+00:00")));

        requests.add(request);
        requests.add(request2);
        return requests;
    }

    private List<RoomEntity> createRooms() throws Exception{
        List<RoomEntity> rooms = new ArrayList<>();

        RoomEntity room = new RoomEntity();
        Field field = room.getClass().getDeclaredField("roomName");
        field.setAccessible(true);
        field.set(room, "DIS1 2020");
        room.setRoomType(RoomEntity.RoomType.CMPT);
        RoomEntity room2 = new RoomEntity();
        field = room2.getClass().getDeclaredField("roomName");
        field.setAccessible(true);
        field.set(room2, "DIS2 2040");
        room2.setRoomType(RoomEntity.RoomType.CMPT);
        RoomEntity room3 = new RoomEntity();
        field = room3.getClass().getDeclaredField("roomName");
        field.setAccessible(true);
        field.set(room3, "DIS3 2060");
        room3.setRoomType(RoomEntity.RoomType.CMPT);
        RoomEntity room4 = new RoomEntity();
        field = room4.getClass().getDeclaredField("roomName");
        field.setAccessible(true);
        field.set(room4, "DIS4 2080");
        room4.setRoomType(RoomEntity.RoomType.CMPT);

        rooms.addAll(Arrays.asList(room, room2, room3, room4));
        return rooms;
    }

    private List<CourseConstraintEntity> addConstraint() {
        List<CourseConstraintEntity> constraints = new ArrayList<CourseConstraintEntity>();

        CourseConstraintEntity constraint = new CourseConstraintEntity();
        CompositeCourseConstraintId id = new CompositeCourseConstraintId();
        id.setCourseCode("CMPT276");
        id.setConstraintId(1);
        constraint.setId(id);

        CourseConstraintEntity constraint2 = new CourseConstraintEntity();
        CompositeCourseConstraintId id2 = new CompositeCourseConstraintId();
        id2.setCourseCode("CMPT200");
        id2.setConstraintId(1);
        constraint2.setId(id2);

        constraints.add(constraint);
        constraints.add(constraint2);
        return constraints;
    }
}
