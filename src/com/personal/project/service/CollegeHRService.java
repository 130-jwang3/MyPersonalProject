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

    private static StudentDAO studentDao = new StudentFileDAOImpl();
    private static InstructorDAO instructorDAO = new InstructorFileDAOImpl();
    private static Logger logger = LogManager.getLogger(CollegeHRService.class);
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
            rtn = -1;
        }
        return rtn;
    }

    public int addStudent(String firstName, String lastName, String major, long phoneNumber, String email, long studentID, int numGrade) {
        int rtn = 0;
        Student newStudent = new Student(lastName, firstName, major, "Student", phoneNumber, email, studentID, numGrade);
        try {
            studentDao.addStudent(newStudent);
        } catch (Exception e) {
            logger.error(e);
            rtn = -1;
        }
        return rtn;
    }

    public int addInstructor(String firstName, String lastName, String major, long phoneNumber, String email, long employeeID) {
        int rtn = 0;
        Instructor newInstructor = new Instructor(lastName, firstName, major, "Instructor", phoneNumber, email, employeeID);
        try {
            instructorDAO.addInstructor(newInstructor);
        } catch (Exception e) {
            e.printStackTrace();
            rtn = -1;
        }
        return rtn;
    }

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
}
