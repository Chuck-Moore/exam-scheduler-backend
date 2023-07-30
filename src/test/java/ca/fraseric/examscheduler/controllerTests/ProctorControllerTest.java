package ca.fraseric.examscheduler.controllerTests;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ca.fraseric.examscheduler.api.controllers.ProctorController;
import ca.fraseric.examscheduler.api.entities.ProctorEntity;
import ca.fraseric.examscheduler.api.entities.TimeInterval;
import ca.fraseric.examscheduler.api.services.ProctorService;

@WebMvcTest(ProctorController.class)
public class ProctorControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProctorService service;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getProctorById_thenReturnProctor() throws Exception {
        ProctorEntity proctor = createProctor();

        given(service.getProctorById("123456789")).willReturn(Optional.of(proctor));

        mvc.perform(get("/proctor/123456789")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.availableTimes[0].start").value(proctor
            .getAvailableTimes().get(0).getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))));
    }


    private ProctorEntity createProctor() throws Exception{
        ProctorEntity proctor = new ProctorEntity();
        Field field = proctor.getClass().getDeclaredField("proctorId");
        field.setAccessible(true);
        field.set(proctor, "123456789");
        List<TimeInterval> availableTimes = Arrays.asList(new TimeInterval(
            ZonedDateTime.parse("2020-12-01T08:00:00.000+00:00"),
            ZonedDateTime.parse("2020-12-02T08:00:00.000+00:00")));
        proctor.setAvailableTimes(availableTimes);
        return proctor;
    }
}
