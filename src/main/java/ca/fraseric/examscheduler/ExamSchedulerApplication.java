package ca.fraseric.examscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ExamSchedulerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExamSchedulerApplication.class, args);
  }

}
