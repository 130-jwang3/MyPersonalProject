package com.personal.project.service;

import com.personal.project.dao.*;
import com.personal.project.dao.impl.*;
import com.personal.project.dto.*;
import org.apache.logging.log4j.*;

public class CourseService {

    //private static StudentDAO studentDao = new StudentFileDAOImpl();
    //private static StudentDAO studentDao = new StudentMysqlDAOImpl();
    //private static StudentDAO studentDAO = StudentFactory.getStudentDaoByName(ConfigProperties.load("dataSource"));
    private static StudentDAO studentDAO = new StudentDAOMysqlFactory().getInstance();
    private static InstructorDAO instructorDAO = new InstructorFileDAOImpl();
    private static CourseDAO courseDAO=new CourseFileDAOImpl();
    private static Logger logger = LogManager.getLogger("mylog");

    public int addCourse(String courseName,int capacity,double credit) {
        int rtn = 0;
        try {
            if (courseDAO.findByName(courseName)==null) {
                Course newCourse = new Course(courseName,capacity,credit);
                courseDAO.addCourse(newCourse);
            } else {
                rtn = -2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rtn = -1;
        }
        return rtn;
    }

    public int enrollStudent(Long studentID, String courseName) {
        try {
            Course course = courseDAO.findByName(courseName);
            if (course.getTotalStudent() < course.getCapacity())
                course.getStudentEnrolled().add(new Course.CourseStudent(studentID, Course.StudentCourseState.ENROLLED));
            else
                course.getWaitList().add(new Course.CourseStudent(studentID, Course.StudentCourseState.WAITLISTED));
            courseDAO.update(course);
        }  catch(Exception e){
            return -1;
        }
        return 0;

    }
}
