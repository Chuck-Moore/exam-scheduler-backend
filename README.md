# Project Abstract:

The **FIC Exam Scheduler** is a web application to streamline the process by which Fraser International College (FIC) schedules and manages exams. In addition to performing the necessary business logic to generate conflict-free schedules from a given set of constraints, this application will support discrete views and workflows for administrators, instructors, and proctors. Administrative users will have the ability to manage and oversee the generated schedule, while non-administrative users will be kept informed of all relevant scheduling decisions and updates via email notifications.

  

# Customer:

This project is being undertaken at the behest of the FIC administration, with the goal of simplifying and consolidating their current scheduling process while minimizing the potential for errors and miscommunication. Additional stakeholders are instructors and proctors/invigilators, who will expect a certain degree of consistency with their established workflows.

  

# Competitive Analysis:  
There are many existing solutions that attempt to simplify and consolidate the exam scheduling process for higher learning institutions. Most of these solutions seem to require extensive set-up and integration with existing student/institutional data, and are largely intended as ‘all in one’ solutions for managing cross-campus scheduling needs.

  

For example, Infosilem can leverage student registration data in order to generate final exam schedules. This approach may not be a great fit for the needs of FIC, who have a relatively small number of class sections and students to manage. The effort of integrating their student database to such a degree is not likely to be worth the benefit.

  

Other approaches use student self-registration as a core workflow, as seen in solutions such as AppointmentQuest. This model runs contrary to the stated workflow goals of FIC, and would require broader institutional reorganization in order to implement.

  

In general, there would seem to be a scale mismatch between the scope of most existing solutions and the current problem faced by FIC. This problem necessitates a highly specific optimization that must fit into existing institutional workflows without an excessive organizational cost of adoption or high learning curve. It is also important to note that the cost of many existing solutions (generally functioning on a monthly billing model) may not be desirable for FIC.

  
  

# Current Business Logic Model:

The current business flow is kickstarted by the FIC administration announcing an exam period, which requires every single instructor to contact the administration via email for their desired examination date for availability confirmation. Upon collecting all the requests, the administration manually creates a schedule considering course constraints and room conflicts.

  

Our web application aims to streamline and automate this operation, by allowing FIC administrators to define exam time slots in the calendar. From there, instructors will submit a form indicating the number of sections in their class and a priority list of preferred starting exam times and duration. Meanwhile, proctor candidates must fill in their availability to oversee exams.

  

Based on those limitations, an examination schedule draft will be generated. Administrators can make additional adjustments to timeslots and venues. Once the schedule is confirmed by the administration (or when any update is made), the system will send confirmation emails to all involved proctors and instructors.

  

# Features:

The FIC exam scheduler will consist of two main components, similar to database Data Definition Language (DDL) and Data Manipulation Language (DML). The DDL part will be used to specify the exam period and create available exam time slots within that window. On the other hand, the DML will allow for adding, editing, and deleting professor requests.

The system will be designed with the open-closed principle in mind. Users will have different permissions based on their status as an instructor, proctor, or administrator. Their respective functional interfaces will also be assigned accordingly. For instance, if an instructor is also a proctor then they would be able to access both the proctor and instructor interfaces.

Instructors will be able to request an exam time slot. This request will include the exam duration, class sections, and their top three choices for exam time slots. The system will perform a conflict check to ensure the requested time slots do not overlap with existing exams. Instructors should also be able to edit or delete these requests.

Administrators will have broader privileges. They can create exam slots, including the course name, section, start time, duration, number of rooms required, rooms assigned, and number of proctors needed. In addition, they can add or modify constraints on exam courses.

Proctors will have specific capabilities within the system. They can specify non-available dates, view their pending assignments (both agreed and rejected), and download a calendar file with their assigned duties. Proctors will also be assigned specific roles, either as hall monitors or room invigilators.

The system will also incorporate a notification feature, automatically sending emails (and potentially text messages) to proctors and instructors regarding exam schedules and assignments upon schedule confirmation or alteration. An API will be used to manage and facilitate these notifications, but additional research is required to determine what will best serve the needs of this project. Early discussions have involved the Gmail API, Amazon SeS, JavaMail, EmailLabs, and SuprSend.

The logic of exam scheduling will involve collecting the first, second, and third choices for preferred exam times from professors within the designated time frame. To prioritize scheduling, the system may maintain a queue of professors based on when they input their preferred exam time frames. The system will work through the queue, scheduling exams at the most preferred times possible.

To ensure fairness, the system will not schedule three exams of the same type within a 24-hour period. The specifics of which classes fall into the same type should be referred to in the live document.

The integration of a database will be crucial for the system's functionality. This database will include information about proctors, rooms, and potentially instructors, enabling efficient management of resources and easy visualization of proposed exam schedules. PostgreSQL will be used for this database, mainly for the easy integration that Spring Boot provides.

Finally, the chosen API for authentication will be the Zitadel authentication API. This API will facilitate user authentication and authorization within the system, ensuring secure access and data protection.


# User Stories:

There are three different primary user types for this application: administrators, instructors, and proctors. Outlines of what we expect some of these user stories to look like are as follows:

  

-   As an administrator, I want to finalize an exam so that the schedule can be updated and relevant parties notified.
    
-   As an instructor, I want to see the exam schedule to double check what room the final will be held in.
    
-   As a proctor, I want to specify my availability so that I am not working on Thursday.
    

  
# Constraints:  
There are a few constraints to keep in mind when implementing the scheduling system for the exams. If a course has multiple sections, there should be only one exam scheduled for the class. Also, courses that are likely to be taken in the same term cannot have an exam scheduled within 24 hours of one another. The administrators have the ability to add additional constraints for courses before generating the schedule.
