package ca.fraseric.examscheduler.api.controller;

import java.util.Arrays;
import java.util.List;

import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/test")
    public ResponseEntity<List<String>> testMessage() {
        return ResponseEntity.ok(Arrays.asList("hello","test"));
    }

    @GetMapping("/admin/1")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<String>> testSchedule(Principal principal) {
        return ResponseEntity.ok(Arrays.asList("schedule", "test", principal.getName()));
    }
}
