package com.personal.project.service;

import com.personal.project.dao.*;
import com.personal.project.dao.impl.file.*;
import com.personal.project.dao.impl.mysql.*;
import com.personal.project.dto.*;
import org.apache.logging.log4j.*;

public class CourseService {

    //private static StudentDAO studentDao = new StudentFileDAOImpl();
    //private static StudentDAO studentDao = new StudentMysqlDAOImpl();
    //private static StudentDAO studentDAO = StudentFactory.getStudentDaoByName(ConfigProperties.load("dataSource"));
    private static InstructorDAO instructorDAO = new InstructorFileDAOImpl();
    private static CourseDAO courseDAO=new CourseMysqlDAOImpl();
    private static CourseStudentDAO courseStudentDAO=new CourseStudentMysqlDAOImpl();
    private static Logger logger = LogManager.getLogger("mylog");


}
