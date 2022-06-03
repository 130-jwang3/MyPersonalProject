package com.personal.project.service;

import com.personal.project.dao.*;
import com.personal.project.dao.impl.mysql.*;
import com.personal.project.dto.*;
import org.apache.commons.lang3.tuple.*;
import org.apache.logging.log4j.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;


/*
 * 1. business logic combination
 * 2. validation
 * 3. exception handler
 */
@Service
public class CollegeHRService {

    private static StudentDAO studentDao = new StudentMysqlDAOImpl();
    private static InstructorDAO instructorDAO = new InstructorMysqlDAOImpl();
    private static CourseDAO courseDAO = new CourseMysqlDAOImpl();
    private static CourseStudentDAO courseStudentDAO = new CourseStudentMysqlDAOImpl();
    private static Logger logger = LogManager.getLogger("mylog");

    //Both student&instructor
    public List<Person> findAllHeadsByName(String keyword) {
        List<Person> result = new ArrayList<Person>();
        try {
            if (studentDao.findByName(keyword) != null) {
                result.addAll(studentDao.findByName(keyword));
            }
            if (instructorDAO.findByName(keyword) != null) {
                result.addAll(instructorDAO.findByName(keyword));
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
        return result;
    }

    public int deleteById(Long id, String type) {
        int rtn = 0;
        try {
            if ("Student".equals(type)) {
                if (studentDao.findById(id) != null) {
                    studentDao.deleteById(id);
                } else {
                    return -1;
                }
            } else if ("Instructor".equals(type)) {
                if (instructorDAO.findById(id) != null) {
                    instructorDAO.deleteById(id);
                } else {
                    return -1;
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return rtn;
    }

    //student
    public int addStudent(Student student) {
        int rtn = 0;
        try {
            studentDao.addStudent(student);
        } catch (Exception e) {
            logger.error(e);
            rtn = -1;
        }
        return rtn;
    }

    public void deleteStudentsWithAllInvolvedCourses(Long studentID) {
        List<Course> courses = findAllCourses();
        Long oid = findByStudentID(studentID).getStudentOID();
        courses=courses.stream().filter(course -> findCourseStudentByID(oid, course)!=null).collect(Collectors.toList());
        courses.forEach(course -> {
            CourseStudent student = findCourseStudentByID(oid, course);
            if (student.getState() == Course.StudentCourseState.ENROLLED) {
                student.setState(Course.StudentCourseState.FAILED);
                student.setScore(0.0);
                updateCourseStudent(student);
                if (!course.getWaitList().isEmpty()) {
                    CourseStudent courseStudent = course.getWaitList().peek();
                    courseStudent.setState(Course.StudentCourseState.ENROLLED);
                    updateCourseStudent(courseStudent);
                }
            }

        });
    }

    public Map<String, Double> getStudentCreditAndCGPA(Long studentId) {
        List<Course> courses = findAllCourses();
        Map<String, Double> studentScoreMap = new HashMap<String, Double>();
        Long oid=findByStudentID(studentId).getStudentOID();
        courses = courses.stream().filter(course -> findCourseStudentByID(oid, course)!=null&&(findCourseStudentByID(oid, course).getState() == Course.StudentCourseState.FINISHED ||
                findCourseStudentByID(oid, course).getState() == Course.StudentCourseState.FAILED)).collect(Collectors.toList());
        int totalCredit = 0;
        List<Double> scores = new ArrayList<>();
        for (Course course : courses) {
            scores.add(findCourseStudentByID(oid, course).getScore());
            totalCredit += course.getCredit();
        }
        studentScoreMap.put("credit", (double) totalCredit);
        double totalGPA = 0;
        for (Double score : scores) {
            if (score <= 50) {
                totalGPA += 0;
            } else if (score > 50 && score <= 60) {
                totalGPA += 1.15;
            } else if (score > 60 && score <= 70) {
                totalGPA += 2;
            } else if (score > 70 && score <= 80) {
                totalGPA += 3;
            } else if (score > 80 && score <= 100) {
                totalGPA += 4;
            }
        }
        studentScoreMap.put("cGPA", totalGPA);
        return studentScoreMap;
    }

    public List<Student> findAllStudentByName(String keyword) {
        List<Student> students = new ArrayList<>();
        try {
            if (studentDao.findByName(keyword) != null) {
                students.addAll(studentDao.findByName(keyword));
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
        return students;
    }

    public List<Student> findAllStudent() {
        try {
            return studentDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student findByStudentID(Long studentID) {
        try {
            return studentDao.findById(studentID);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public Student findByStudentOID(Long studentOID) {
        try {
            return studentDao.findByOid(studentOID);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public void updateByStudentID(Student student) {
        try {
            studentDao.update(student);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    //instructor
    public int addInstructor(Instructor instructor) {
        int rtn = 0;
        try {
            instructorDAO.addInstructor(instructor);
        } catch (Exception e) {
            e.printStackTrace();
            rtn = -1;
        }
        return rtn;
    }

    public List<Instructor> findAllInstructorByName(String keyword) {
        List<Instructor> instructors = new ArrayList<>();
        try {
            if (instructorDAO.findByName(keyword) != null) {
                instructors.addAll(instructorDAO.findByName(keyword));
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
        return instructors;
    }

    public List<Instructor> findAllInstructor() {
        try {
            return instructorDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Instructor findByEmployeeID(Long employeeID) {
        try {
            return instructorDAO.findById(employeeID);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public void updateByEmployeeID(Instructor instructor) {
        try {
            instructorDAO.update(instructor);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    //course
    public int addCourse(Course course) {
        int rtn = 0;
        try {
            if (courseDAO.findByName(course.getName()) == null) {
                courseDAO.addCourse(course);
            } else {
                rtn = -2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rtn = -1;
        }
        return rtn;
    }

    public int deleteCourseStudentByName(String name, Long student_id) {
        int rtn = 0;
        try {
            if (courseDAO.findByName(name) != null) {
                courseDAO.deleteStudentByName(name, student_id);
            } else {
                rtn = -1;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return rtn;
    }

    public Course findByCourseName(String courseName) {
        try {
            return courseDAO.findByName(courseName);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public List<Course> findAllCourses() {
        try {
            return courseDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCourse(Course course) {
        try {
            courseDAO.update(course);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    //course student
    public int enrollStudent(Long studentOID, String courseID) {
        try {
            Course course = courseDAO.findById(courseID);
            CourseStudent courseStudent = null;
            if (course.getTotalStudent() < course.getCapacity())
                courseStudent = new CourseStudent(studentOID, courseDAO.findOid(courseID), Course.StudentCourseState.ENROLLED, 0.0, "Student");
            else
                courseStudent = new CourseStudent(studentOID, courseDAO.findOid(courseID), Course.StudentCourseState.WAITLISTED, 0.0, "Student");
            courseStudentDAO.addCourseStudent(courseStudent);
        } catch (Exception e) {
            return -1;
        }
        return 0;

    }

    public int addTA(CourseStudent courseStudent) {
        int rtn = 0;
        try {
            courseStudentDAO.addCourseStudent(courseStudent);
        } catch (Exception e) {
            e.printStackTrace();
            rtn = -1;
        }
        return rtn;
    }

    public CourseStudent findCourseStudentByID(Long studentOID, Course course) {
        CourseStudent result = course.getStudentEnrolled().stream().filter(courseStudent -> courseStudent.getStudentOid().equals(studentOID)).findFirst().orElse(null);
        if (result == null) {
            result = course.getWaitList().stream().filter(courseStudent -> courseStudent.getStudentOid().equals(studentOID)).findFirst().orElse(null);
        }
        if (result == null) {
            result = course.getFinishedStudent().stream().filter(courseStudent -> courseStudent.getStudentOid().equals(studentOID)).findFirst().orElse(null);
        }
        return result;
    }

    public void dropCourse(Course course, Long studentOID) {
        CourseStudent courseStudent = findCourseStudentByID(studentOID, course);

        if (courseStudent.getState() == Course.StudentCourseState.ENROLLED) {
            courseStudent.setState(Course.StudentCourseState.DROPPED);
            if (!course.getWaitList().isEmpty()) {
                CourseStudent courseStudent1 = course.getWaitList().peek();
                courseStudent1.setState(Course.StudentCourseState.ENROLLED);
                updateCourseStudent(courseStudent1);
            }
        } else if (courseStudent.getState() == Course.StudentCourseState.WAITLISTED) {
            deleteCourseStudent(courseStudent);
        } else {
            System.out.println("The student has finished or failed this course");
        }
        updateCourseStudent(courseStudent);
    }

    public void updateCourseStudent(CourseStudent courseStudent) {
        try {
            courseStudentDAO.updateCourseStudent(courseStudent);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void deleteCourseStudent(CourseStudent courseStudent){
        try{
            courseStudentDAO.deleteCourseStudent(courseStudent.getCourseOid(),courseStudent.getStudentOid());
        }catch (Exception e){
            logger.error(e);
        }
    }

    public void assignGrade(Course course, Long studentOID, Double grade) {
        CourseStudent student = findCourseStudentByID(studentOID, course);
        student.setScore(grade);
        if (grade > 50) {
            student.setState(Course.StudentCourseState.FINISHED);
        } else {
            student.setState(Course.StudentCourseState.FAILED);
        }
        if (!course.getWaitList().isEmpty()) {
            CourseStudent courseStudent = course.getWaitList().peek();
            courseStudent.setState(Course.StudentCourseState.ENROLLED);
            updateCourseStudent(courseStudent);
        }
        updateCourseStudent(student);
    }

    //other
    public boolean testInputMajor(String major) {
        boolean result = true;
        try {
            EnumSet<Person.Major> except = EnumSet.of(Person.Major.COMPUTER_SCIENCE, Person.Major.MATH, Person.Major.ENGINEER, Person.Major.PHYSICS, Person.Major.APPLIED_SCIENCE, Person.Major.ARTS);
            return !except.contains(Person.Major.valueOf(major));
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }

    public boolean testInputTitle(String title) {
        boolean result = true;
        try {
            EnumSet<Instructor.Title> except = EnumSet.of(Instructor.Title.Assistant_professor, Instructor.Title.Associate_professor, Instructor.Title.professor);
            return !except.contains(Instructor.Title.valueOf(title));
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }

    public boolean testInputType(String type) {
        boolean result = true;
        try {
            EnumSet<Student.Type> except = EnumSet.of(Student.Type.Graduate, Student.Type.Undergraduate);
            return !except.contains(Student.Type.valueOf(type));
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }

    public boolean checkStudentExist(Long studentOID, Course course) {
        long result = course.getStudentEnrolled().stream().filter(courseStudent -> courseStudent.getStudentOid().equals(studentOID)).count();
        result += course.getWaitList().stream().filter(courseStudent -> courseStudent.getStudentOid().equals(studentOID)).count();
        System.out.println(result);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

}
