package com.personal.project.service;

import com.personal.project.dao.*;
import com.personal.project.dao.impl.*;
import com.personal.project.dto.*;
import org.apache.logging.log4j.*;

import java.util.*;


/*
 * 1. business logic combination
 * 2. validation
 * 3. exception handler
 */
public class CollegeHRService {

    private static StudentDAO studentDao = new StudentMysqlDAOImpl();
    private static InstructorDAO instructorDAO = new InstructorFileDAOImpl();
    private static CourseDAO courseDAO=new CourseFileDAOImpl();
    private static Logger logger = LogManager.getLogger("mylog");
    //private static InstructorDAO instructorDao = new xxxx;

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

    public Student findByStudentID(Long studentID) {
        try {
            return studentDao.findById(studentID);
        } catch (Exception e) {
            logger.error(e);
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

    public Course findByCourseID(String courseID) {
        try {
            return courseDAO.findById(courseID);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public Course findByCourseName(String courseName) {
        try {
            return courseDAO.findByName(courseName);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
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
            } else {
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

    public int addInstructor(String firstName, String lastName, Person.Major major, String phoneNumber, String email, long employeeID, Instructor.Title title) {
        int rtn = 0;
        Instructor newInstructor = new Instructor(lastName, firstName, major, "Instructor", phoneNumber, email, employeeID,title);
        try {
            instructorDAO.addInstructor(newInstructor);
        } catch (Exception e) {
            e.printStackTrace();
            rtn = -1;
        }
        return rtn;
    }

    /*public int addCourse(String courseName,int capacity,double credit) {
        int rtn = 0;
        Course newCourse = new Course(courseName,capacity,credit);
        try {
            courseDAO.addCourse(newCourse);
        } catch (Exception e) {
            e.printStackTrace();
            rtn = -1;
        }
        return rtn;
    }*/

    public List<Student> findAllStudent() {
        try {
            return studentDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Instructor> findAllInstructor() {
        try {
            return instructorDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void changeByStudentID(Student student){
        try{
            studentDao.update(student);
        }catch (Exception e){
            logger.error(e);
        }

    }

    public void changeByEmployeeID(Instructor instructor){
        try{
            instructorDAO.update(instructor);
        }catch (Exception e){
            logger.error(e);
        }

    }

    public void updateCourse(Course course){
        try{
            courseDAO.update(course);
        }catch (Exception e){
            logger.error(e);
        }
    }



    public boolean testInputMajor(String major){
        boolean result=true;
        try{
            EnumSet<Person.Major> except=EnumSet.of(Person.Major.COMPUTER_SCIENCE, Person.Major.MATH, Person.Major.ENGINEER, Person.Major.PHYSICS,Person.Major.APPLIED_SCIENCE,Person.Major.ARTS);
            return !except.contains(Person.Major.valueOf(major));
        }catch(Exception e){
            logger.error(e);
        }
        return result;
    }

    public boolean testInputTitle(String title){
        boolean result=true;
        try{
            EnumSet<Instructor.Title> except=EnumSet.of(Instructor.Title.Assistant_professor, Instructor.Title.Associate_professor, Instructor.Title.professor);
            return !except.contains(Instructor.Title.valueOf(title));
        }catch(Exception e){
            logger.error(e);
        }
        return result;
    }

    public boolean testInputType(String type){
        boolean result=true;
        try{
            EnumSet<Student.Type> except=EnumSet.of(Student.Type.Graduate,Student.Type.Undergraduate);
            return !except.contains(Student.Type.valueOf(type));
        }catch(Exception e){
            logger.error(e);
        }
        return result;
    }

    public boolean checkStudentExist(Long studentID, Course course){
        long result=course.getStudentEnrolled().stream().filter(courseStudent -> courseStudent.getStudentId().equals(studentID)).count();
        result+=course.getWaitList().stream().filter(courseStudent -> courseStudent.getStudentId().equals(studentID)).count();
        System.out.println(result);
        if(result==1){
            return true;
        }else{
            return false;
        }
    }

    public Course.CourseStudent findCourseStudentByID(Long studentID, Course course){
        Course.CourseStudent result=course.getStudentEnrolled().stream().filter(courseStudent -> courseStudent.getStudentId().equals(studentID)).findFirst().orElse(null);
        if(result==null){
            result=course.getWaitList().stream().filter(courseStudent -> courseStudent.getStudentId().equals(studentID)).findFirst().orElse(null);
        }
        if(result==null){
            result=course.getFinishedStudent().stream().filter(courseStudent -> courseStudent.getStudentId().equals(studentID)).findFirst().orElse(null);
        }
        return result;
    }

}
