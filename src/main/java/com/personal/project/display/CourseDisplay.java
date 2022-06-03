package com.personal.project.display;

import com.personal.project.dto.*;
import com.personal.project.error.*;
import com.personal.project.service.*;
import com.personal.project.util.*;
import de.vandermeer.asciitable.*;
import org.apache.logging.log4j.*;

import java.util.*;
import java.util.stream.*;

public class CourseDisplay {
    private static Scanner scanner = new Scanner(System.in);
    private static CollegeHRService collegeHRService = new CollegeHRService();
    private static CourseService courseService = new CourseService();
    private static Logger logger = LogManager.getLogger("mylog");

    public static int courseMenu() {
        int option = 0;
        do {
            chooseMenu();
            option = scanner.nextInt();
            if (option < 1 || option > 11) {
                System.out.println("Invalid Input, try again");
                continue;
            } else {
                takeAction(option);
            }
        } while (option != 11);
        return 0;
    }

    private static void chooseMenu() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Choose from the following option");
        at.addRule();
        at.addRow("1. add course");
        at.addRule();
        at.addRow("2. assign instructor");
        at.addRule();
        at.addRow("3. add TA");
        at.addRule();
        at.addRow("4. add Student");
        at.addRule();
        at.addRow("5. view student");
        at.addRule();
        at.addRow("6. drop course");
        at.addRule();
        at.addRow("7. view all course");
        at.addRule();
        at.addRow("8. mark final grade");
        at.addRule();
        at.addRow("9. change final grade");
        at.addRule();
        at.addRow("10. view grade distribution");
        at.addRule();
        at.addRow("11. return to main menu");
        at.addRule();
        System.out.println(at.render());
    }

    private static void takeAction(int actionId) {
        String courseName = "";
        //List<Course> courses = collegeHRService.findAllCourses();
        //List<String> courseNames = courses.stream().map(course->course.getName()).collect(Collectors.toList());
        MenuAction menuAction=MenuAction.findByID(actionId);
        switch (menuAction) {
            case ADD_COURSE:
                addCourse();
                break;
            case ASSIGN_INSTRUCTOR:
                do {
                    System.out.println("Please input valid course name:");
                    courseName = scanner.next();

                } while (collegeHRService.findByCourseName(courseName)==null);
                assignCourseInstructor(courseName);
                break;
            case ASSIGN_TA:
                do {
                    System.out.println("Please input valid course name:");
                    courseName = scanner.next();
                } while (collegeHRService.findByCourseName(courseName)==null);
                addCourseTA(courseName);
                break;
            case ENROLL_STUDENT:
                do {
                    System.out.println("Please input valid course name:");
                    courseName = scanner.next();
                } while (collegeHRService.findByCourseName(courseName)==null);
                addCourseStudent(courseName);
                break;
            case VIEW_ALL_STUDENT:
                do {
                    System.out.println("Please input valid course name:");
                    courseName = scanner.next();
                } while (collegeHRService.findByCourseName(courseName)==null);
                generateStudentList(collegeHRService.findByCourseName(courseName));
                break;
            case DROP_COURSE:
                do {
                    System.out.println("Please input valid course name:");
                    courseName = scanner.next();
                } while (collegeHRService.findByCourseName(courseName)==null);
                dropCourse(collegeHRService.findByCourseName(courseName));
                break;
            case VIEW_ALL_COURSES:
                generateCourseList(collegeHRService.findAllCourses());
                break;
            case MARK_FINAL_GRADE:
                do {
                    System.out.println("Please input valid course name:");
                    courseName = scanner.next();
                } while (collegeHRService.findByCourseName(courseName)==null);
                assignGrade(collegeHRService.findByCourseName(courseName));
                break;
            case CHANGE_FINAL_GRADE:
                do {
                    System.out.println("Please input valid course name:");
                    courseName = scanner.next();
                } while (collegeHRService.findByCourseName(courseName)==null);
                changeStudentGrade(collegeHRService.findByCourseName(courseName));
                break;
            case VIEW_GRADE_DISTRIBUTION:
                do {
                    System.out.println("Please input valid course name:");
                    courseName = scanner.next();
                } while (collegeHRService.findByCourseName(courseName)==null);
                gradeDistribution(collegeHRService.findByCourseName(courseName));
                break;
            case RETURN:
                return;
        }
    }

    private static void addCourse() {
        try {
            //Input
            System.out.println("Input course name:");
            String courseName = scanner.next();
            System.out.println("Input capacity of class:");
            int capacity = scanner.nextInt();
            System.out.println("Input credit of class:");
            double credit = scanner.nextDouble();
            long instructorID = 0;
            List<Instructor> instructors = collegeHRService.findAllInstructor();
            InstructorDisplay.generateInstructorList(instructors);
            Instructor instructor = null;
            do {
                System.out.println("Assign Valid New Instructor(input employee ID):");
                instructorID = scanner.nextLong();
                final long confirmedInstructorID = instructorID;
                instructor = instructors.stream().filter(ins->confirmedInstructorID==ins.getEmployeeID()).findFirst().orElse(null);
            } while (instructor==null);
            Course course=new Course(courseName,capacity,credit,instructor.getInstructor_oid());
            //Call Service
            int rtn = collegeHRService.addCourse(course);
            //Output
            System.out.println(rtn);
            if (rtn == 0)
                logger.info("new course added to file");
            else {
                CourseServiceError.getError(rtn);
                logger.error("new course adding failure");
            }
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    private static void assignCourseInstructor(String courseName) {
        Course course = collegeHRService.findByCourseName(courseName);
        long instructorID = 0;
        List<Instructor> instructors = collegeHRService.findAllInstructor();
        InstructorDisplay.generateInstructorList(instructors);
        Instructor instructor = null;
        do {
            System.out.println("Assign Valid New Instructor(input employee ID):");
            instructorID = scanner.nextLong();
            final long confirmedInstructorID = instructorID;
            instructor = instructors.stream().filter(ins->confirmedInstructorID==ins.getEmployeeID()).findFirst().orElse(null);
        } while (instructor==null);
        course.setAssignedInstructor(instructor.getInstructor_oid());
        collegeHRService.updateCourse(course);
    }

    private static void addCourseTA(String courseName) {
        Course course = collegeHRService.findByCourseName(courseName);
        AsciiTable at = new AsciiTable();
        long TAID = 0;
        at.addRule();
        at.addRow("Name", "Major", "ID");
        collegeHRService.findAllStudent().forEach(TA -> {
            if (TA.getType() == Student.Type.Graduate) {
                at.addRule();
                at.addRow(TA.getFirstName() + " " + TA.getLastName(),
                        TA.getMajor(),
                        TA.getStudentNumber());
            }
        });
        at.addRule();
        System.out.println(at.render());
        do {
            System.out.println("Add new TA(input ID):");
            TAID = scanner.nextLong();
        } while (collegeHRService.findByStudentID(TAID) == null ||
                collegeHRService.findByStudentID(TAID).getType() == Student.Type.Undergraduate);
        Long student_oid=collegeHRService.findByStudentID(TAID).getStudentOID();
        Long course_oid= course.getCourseOID();
        CourseStudent courseStudent=new CourseStudent(student_oid,course_oid,Course.StudentCourseState.ENROLLED,-1.0,"TA");
        collegeHRService.addTA(courseStudent);
    }

    private static void addCourseStudent(String courseName) {
        Course course = collegeHRService.findByCourseName(courseName);
        List<CourseStudent> courseStudents = course.getStudentEnrolled();
        AsciiTable at = new AsciiTable();
        long studentID = 0;
        at.addRule();
        at.addRow("Name", "Major", "Student number");
        collegeHRService.findAllStudent().forEach(stu -> {
            if (!course.getTeacherAssists().contains(stu.getStudentNumber())) {
                at.addRule();
                at.addRow(stu.getFirstName() + " " + stu.getLastName(),
                        stu.getMajor(),
                        stu.getStudentNumber());
            }
        });
        at.addRule();
        System.out.println(at.render());
        do {
            System.out.println("Add new Student(input ID):");
            studentID = scanner.nextLong();
        } while (collegeHRService.findByStudentID(studentID) == null);
        collegeHRService.enrollStudent(studentID,courseName);
    }

    private static void generateStudentList(Course course) {
        try {
            List<CourseStudent> pair = course.getStudentEnrolled();
            List<CourseStudent> pairs=Stream.concat(pair.stream(),course.getFinishedStudent().stream()).collect(Collectors.toList());
            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow("Student name", "Major", "Numerical grade", "Status in course");
            pair.forEach(courseStudent -> {
                at.addRule();
                Student student = collegeHRService.findByStudentOID(courseStudent.getStudentOid());
                at.addRow(student.getFirstName() + " " + student.getLastName(), student.getMajor(), courseStudent.getScore(), courseStudent.getState());
            });
            at.addRule();
            System.out.println(at.render());
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    private static void dropCourse(Course course) {
        generateAllStudent(course);
        Long studentID;
        do {
            System.out.println("Please input studentID:");
            studentID = scanner.nextLong();
        } while (collegeHRService.findByStudentID(studentID) == null || !collegeHRService.checkStudentExist(studentID, course));
        Student student= collegeHRService.findByStudentID(studentID);
        CourseStudent courseStudent = collegeHRService.findCourseStudentByID(student.getStudentOID(), course);
        courseStudent.setState(Course.StudentCourseState.DROPPED);
        if (courseStudent.getState() == Course.StudentCourseState.ENROLLED) {
            List<CourseStudent> courseStudents = course.getStudentEnrolled();
            courseStudents.remove(courseStudent);
            course.setStudentEnrolled(courseStudents);
            if(course.getStudentEnrolled()==null){
                System.out.println("1");
            }
            if (!course.getWaitList().isEmpty()) {
                CourseStudent courseStudent1 = course.getWaitList().poll();
                courseStudent1.setState(Course.StudentCourseState.ENROLLED);
                courseStudents.add(courseStudent1);
            }
        } else if (courseStudent.getState() == Course.StudentCourseState.WAITLISTED) {
            course.getWaitList().remove(courseStudent);
        } else {
            System.out.println("The student has finished or failed this course");
        }
        collegeHRService.deleteCourseStudentByName(course.getName(), studentID);
    }

    private static void generateAllStudent(Course course) {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Student name", "student ID", "Student status");
        course.getStudentEnrolled().forEach(courseStudent -> {
            Student student = collegeHRService.findByStudentOID(courseStudent.getStudentOid());
            at.addRule();
            at.addRow(student.getFirstName() + " " + student.getLastName(), collegeHRService.findByStudentOID(student.getStudentOID()).getStudentNumber(), courseStudent.getState());
        });
        at.addRule();
        System.out.println(at.render());
    }

    private static void generateCourseList(List<Course> courseList) {
        try {
            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow("Course Name", "Instructor", "Total student", "courseID");
            courseList.forEach(course -> {
                at.addRule();
                at.addRow(course.getName(),
                        course.getAssignedInstructor(),
                        course.getTotalStudent(),
                        course.getCourseID());
            });
            at.addRule();
            System.out.println(at.render());
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    private static void assignGrade(Course course) {
        generateAllStudent(course);
        Long studentID;
        do {
            System.out.println("Please input studentID:");
            studentID = scanner.nextLong();
        } while (collegeHRService.findByStudentID(studentID) == null || collegeHRService.findCourseStudentByID(studentID, course).getState() != Course.StudentCourseState.ENROLLED);
        System.out.println("Please input student numerical grade:");
        double grade = scanner.nextDouble();
        CourseStudent student = collegeHRService.findCourseStudentByID(studentID, course);
        student.setScore(grade);
        List<CourseStudent> courseStudents = course.getFinishedStudent();
        List<CourseStudent> studentSet = course.getStudentEnrolled();
        if (grade > 50) {
            student.setState(Course.StudentCourseState.FINISHED);
        } else {
            student.setState(Course.StudentCourseState.FAILED);
        }
        courseStudents.add(student);
        course.setFinishedStudent(courseStudents);
        studentSet.remove(student);
        if (!course.getWaitList().isEmpty()) {
            CourseStudent courseStudent = course.getWaitList().poll();
            courseStudent.setState(Course.StudentCourseState.ENROLLED);
            studentSet.add(courseStudent);
        }
        course.setStudentEnrolled(studentSet);
        collegeHRService.updateCourse(course);
    }

    private static void changeStudentGrade(Course course) {
        generateAllFinishedStudent(course);
        Long studentID;
        do {
            System.out.println("Please input studentID:");
            studentID = scanner.nextLong();
        } while (collegeHRService.findByStudentID(studentID) == null ||
                (collegeHRService.findCourseStudentByID(studentID, course).getState() != Course.StudentCourseState.FINISHED &&
                        collegeHRService.findCourseStudentByID(studentID, course).getState() != Course.StudentCourseState.FAILED));
        System.out.println("Please input student numerical grade:");
        double grade = scanner.nextDouble();
        CourseStudent courseStudent = collegeHRService.findCourseStudentByID(studentID, course);
        if (grade > 50) {
            courseStudent.setState(Course.StudentCourseState.FINISHED);
        } else {
            courseStudent.setState(Course.StudentCourseState.FAILED);
        }
        courseStudent.setScore(grade);
        collegeHRService.updateCourse(course);
    }

    private static void generateAllFinishedStudent(Course course) {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Name", "Student ID", "Status", "Grade");
        course.getFinishedStudent().forEach(courseStudent -> {
            Student student = collegeHRService.findByStudentOID(courseStudent.getStudentOid());
            at.addRule();
            at.addRow(student.getFirstName() + " " + student.getLastName(), collegeHRService.findByStudentOID(student.getStudentOID()).getStudentNumber(), courseStudent.getState(), courseStudent.getScore());
        });
        at.addRule();
        System.out.println(at.render());
    }

    private static void gradeDistribution(Course course) {
        List<CourseStudent> courseStudents = course.getFinishedStudent();
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Letter Grade", "Numerical Grade", "GPA", "Student");
        Collections.sort(courseStudents, new Course.SortByGrade());
        at.addRule();
        at.addRow("F", "0-50", "0.0", "");
        courseStudents.forEach(courseStudent -> {
            if (courseStudent.getScore() <= 50) {
                Student student = collegeHRService.findByStudentOID(courseStudent.getStudentOid());
                at.addRule();
                at.addRow("F", courseStudent.getScore(), "0.0", student.getFirstName() + " " + student.getLastName());
            }
        });
        at.addRule();
        at.addRow("D", "51-60", "1.15", "");
        courseStudents.forEach(courseStudent -> {
            if (courseStudent.getScore() > 50 && courseStudent.getScore() <= 60) {
                Student student = collegeHRService.findByStudentOID(courseStudent.getStudentOid());
                at.addRule();
                at.addRow("D", courseStudent.getScore(), "1.15", student.getFirstName() + " " + student.getLastName());
            }
        });
        at.addRule();
        at.addRow("C", "61-70", "2", "");
        courseStudents.forEach(courseStudent -> {
            if (courseStudent.getScore() > 60 && courseStudent.getScore() <= 70) {
                Student student = collegeHRService.findByStudentOID(courseStudent.getStudentOid());
                at.addRule();
                at.addRow("D", courseStudent.getScore(), "2", student.getFirstName() + " " + student.getLastName());
            }
        });
        at.addRule();
        at.addRow("B", "71-80", "3", "");
        courseStudents.forEach(courseStudent -> {
            if (courseStudent.getScore() > 70 && courseStudent.getScore() <= 80) {
                Student student = collegeHRService.findByStudentOID(courseStudent.getStudentOid());
                at.addRule();
                at.addRow("B", courseStudent.getScore(), "3", student.getFirstName() + " " + student.getLastName());
            }
        });
        at.addRule();
        at.addRow("A", "81-100", "4", "");
        courseStudents.forEach(courseStudent -> {
            if (courseStudent.getScore() > 80 && courseStudent.getScore() <= 100) {
                Student student = collegeHRService.findByStudentOID(courseStudent.getStudentOid());
                at.addRule();
                at.addRow("A", courseStudent.getScore(), "4", student.getFirstName() + " " + student.getLastName());
            }
        });
        at.addRule();
        System.out.println(at.render());
    }
}
