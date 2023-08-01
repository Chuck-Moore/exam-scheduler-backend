package ca.fraseric.examscheduler.api.services;

import java.util.UUID;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ca.fraseric.examscheduler.api.entities.ExamEntity;
import ca.fraseric.examscheduler.api.entities.RequestEntity;
import ca.fraseric.examscheduler.api.entities.RoomEntity;

import ca.fraseric.examscheduler.api.repositories.ExamRepository;

@Service
public class ExamService {
    
    @Autowired
    private ExamRepository repo;

    @Autowired
    private RequestService requestService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private CourseConstraintService courseConstraintService;

    public List<ExamEntity> getAllExams() {
        return repo.findAll();
    }

    public ExamEntity getExamById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public List<ExamEntity> getExamsByInstructorId(String instructorId) {
        return repo.findByInstructorId(instructorId);
    }

    public List<ExamEntity> getExamsByCourseCode(String courseCode) {
        return repo.findByCourseCode(courseCode);
    }

    public List<ExamEntity> getExamsByDate(LocalDate date) {
        ZoneId zone = ZoneId.of("America/Vancouver");
        return repo.findByStartDateTimeBetween(date.atStartOfDay(zone), date.plusDays(1).atStartOfDay(zone));
    }

    public ExamEntity saveExam(ExamEntity newExam) {
        return repo.save(newExam);
    }

    public void deleteExamById(UUID id) {
        repo.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return repo.existsById(id);
    }

    /**
     * Builds a schedule of exams based on the requests in the database.
     * 
     * @return list of scheduled exams
     */
    @Scheduled(cron = "0 29 22 28 07 ?")
    public List<ExamEntity> buildSchedule() {
        List<RequestEntity> requests = requestService.getAll();
        List<ExamEntity> examsScheduled = new ArrayList<>();

        for (int i = 0; i < requests.size(); i++) {
            RequestEntity request = requests.get(i);
            System.out.println("Current request: " + request.getCourseCode());

            List<ZonedDateTime> datePrefs = new LinkedList<>();
            datePrefs.addAll(request.getIsoDatePrefs());

            int studentCount = request.getStudentCount();

            // Search for requests with the same course code and combine their date preferences and student counts
            for (int j =i+1; j<requests.size(); j++) {
                RequestEntity request2 = requests.get(j);
                if (request.getCourseCode().equals(request2.getCourseCode())) {
                    datePrefs.addAll(request2.getIsoDatePrefs());
                    studentCount += request2.getStudentCount();
                    requests.remove(j--);
                }
            }

            ExamEntity exam = generateExam(request, datePrefs, studentCount, examsScheduled);
            if (exam != null) {
                examsScheduled.add(exam);
            }
        }

        examsScheduled.forEach(exam -> {
            this.saveExam(exam);
        });

        return examsScheduled;
    }

    /**
     * Gets an available room for a request. Helper method for buildSchedule().
     * 
     * @param request        the request to get a room for
     * @param datePrefs      list of date preferences for the request (combined with any other requests with the same course code)
     * @param studentCount   the number of students taking the exam
     * @param examsScheduled the exams that have already been scheduled
     * @return exam entity of the scheduled exam or null if it could not be scheduled
     */
    private ExamEntity generateExam(RequestEntity request, List<ZonedDateTime> datePrefs, int studentCount, List<ExamEntity> examsScheduled) {
        List<RoomEntity> rooms = new ArrayList<>();
        String courseCode = request.getCourseCode();

        //Get the course codes of all courses that have a constraint with the current course
        List<Long> constraintIds = new ArrayList<>();
        courseConstraintService.getConstraintsByCourseCode(request.getCourseCode()).forEach(constraint -> {
            constraintIds.add(constraint.getId().getConstraintId());
        });

        Set<String> courses = new HashSet<>();
        for (Long constraindId : constraintIds) {
            courseConstraintService.getConstraintsByConstraintId(constraindId).forEach(constraint -> {
                if (!constraint.getId().getCourseCode().equals(courseCode))
                    courses.add(constraint.getId().getCourseCode());
            });
        }

        //Check if the request have any conflicts with the exams that have already been scheduled
        for (int num = 0; num < datePrefs.size(); num++) {
            ZonedDateTime start = request.getIsoDatePrefs().get(num).minusMinutes(10);
            Duration duration = request.getIsoDuration().plusMinutes(20);

            if (checkCourseConflict(request.getIsoDatePrefs().get(num), courses, examsScheduled)) {
                continue;
            }

            RoomEntity.RoomType roomType;
            if (courseCode.startsWith("CMPT")) {
                roomType = RoomEntity.RoomType.CMPT;
            } else {
                roomType = RoomEntity.RoomType.STANDARD;
            }

            List<RoomEntity> availableRooms = roomService.getRoomsByType(roomType);

            //Remove rooms that are already booked
            for (ExamEntity exam : examsScheduled) {
                ZonedDateTime startTime = exam.getStartDateTime();
                ZonedDateTime endTime = startTime.plus(exam.getIsoDuration());
                if (((startTime.isAfter(start) && startTime.isBefore(start.plus(duration)))
                        || (endTime.isAfter(start) && endTime.isBefore(start.plus(duration))))
                        && exam.getLocations().get(0).getRoomType().equals(roomType)) {
                    for (RoomEntity r : exam.getLocations()) {
                        availableRooms.remove(r);
                    }
                }
                if (availableRooms.isEmpty()) {
                    break;
                }
            }
            
            //Check if there are enough rooms available
            int roomsNeeded = (int) Math.ceil(studentCount/23.0);
            if (roomsNeeded <= availableRooms.size()) {
                for (int i=0; i<roomsNeeded; i++) {
                    rooms.add(availableRooms.get(i));
                }
                ExamEntity exam = new ExamEntity();
                exam.setCourseCode(request.getCourseCode());
                exam.setInstructorId(request.getInstructorId());
                exam.setIsoDuration(request.getIsoDuration());
                exam.setStartDateTime(start.plusMinutes(10));
                exam.setLocations(rooms);
                return exam;
            }
        }
        return null;
    }

    /**
     * Checks if a course has a constraint conflict with any of the exams that have already been scheduled.
     * @param start the start time of the exam
     * @param courses the courses that have a constraint with the requested course
     * @param examsScheduled the exams that have already been scheduled
     * @return true if there is a conflict, false otherwise
     */
    private boolean checkCourseConflict(ZonedDateTime start, Set<String> courses, List<ExamEntity> examsScheduled) {
        for (String course : courses) {
                for (ExamEntity exam : examsScheduled) {
                    if (exam.getCourseCode().equals(course) && Duration.between(exam.getStartDateTime(), start).abs()
                                    .compareTo(Duration.ofHours(24)) <= 0) {
                        return true;
                    }
                }
            }
        return false;
    }
}
