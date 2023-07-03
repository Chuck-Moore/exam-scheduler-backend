package ca.fraseric.examscheduler.api;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  @GetMapping("/test")
  public ResponseEntity<List<String>> testMessage() {
    return ResponseEntity.ok(Arrays.asList("hello", "test"));
  }

  @GetMapping("/admin/1")
  @Secured("ROLE_ADMIN")
  public ResponseEntity<List<String>> testSchedule(
    @AuthenticationPrincipal BearerTokenAuthentication auth
  ) {
    return ResponseEntity.ok(
      Arrays.asList(
        "schedule",
        "test",
        ((OAuth2AuthenticatedPrincipal) auth.getPrincipal()).getName()
      )
    );
  }
}
