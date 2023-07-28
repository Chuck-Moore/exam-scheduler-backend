package ca.fraseric.examscheduler.controllerTests;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ca.fraseric.examscheduler.api.controllers.ExamController;
import ca.fraseric.examscheduler.api.entities.ExamEntity;
import ca.fraseric.examscheduler.api.entities.Room;
import ca.fraseric.examscheduler.api.services.ExamService;

@WebMvcTest(ExamController.class)
public class ExamControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExamService service;

    @Test
    @WithMockUser(username = "123456789", roles = "PROF")
    public void getExams_thenReturnJsonArray() throws Exception {
        ExamEntity exam = createExam();
        List<ExamEntity> allExams = Arrays.asList(exam);

        given(service.getExamsByInstructorId("123456789")).willReturn(allExams);

        mvc.perform(get("/exams")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].courseCode", is(exam.getCourseCode())))
            .andExpect(jsonPath("$[0].instructorId", is(exam.getInstructorId())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getExamsByInstructorId_thenReturnJsonArray() throws Exception {
        ExamEntity exam = createExam();
        List<ExamEntity> allExams = Arrays.asList(exam);

        given(service.getExamsByInstructorId("123456789")).willReturn(allExams);

        mvc.perform(get("/exams").param("instructorId", "123456789")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].courseCode", is(exam.getCourseCode())))
            .andExpect(jsonPath("$[0].instructorId", is(exam.getInstructorId())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getExamsByDate_thenReturnJsonArray() throws Exception {
        ExamEntity exam = createExam();
        List<ExamEntity> allExams = Arrays.asList(exam);

        given(service.getExamsByDate(LocalDate.parse("2020-12-01"))).willReturn(allExams);

        mvc.perform(get("/exams").param("date", "2020-12-01")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].locations[0]", is("DIS1 2020")));
    }

    private ExamEntity createExam() {
        ExamEntity exam = new ExamEntity();
        exam.setCourseCode("CMPT276");
        exam.setStartDateTime(ZonedDateTime.parse("2020-12-01T08:00:00.000+00:00"));
        exam.setInstructorId("123456789");
        List<Room> locations = new ArrayList<Room>();
        locations.add(Room.DIS1_2020);
        exam.setLocations(locations);
        exam.setIsoDuration(Duration.ofHours(1));
        return exam;
    }
}
