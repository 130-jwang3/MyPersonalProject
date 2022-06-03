package com.personal.project.dao;

import com.personal.project.dto.*;

import java.util.*;

public interface CourseDAO  {
    void addCourse(Course course) throws Exception;

    Long findOid(String courseId) throws Exception;

    Course findById(String courseId) throws Exception;

    List<Course> findAll() throws Exception;

    Course findByName(String name) throws Exception;

    void deleteById(String courseID) throws Exception;

    void deleteStudentByName(String course,Long student_id) throws Exception;

    Course update(Course course) throws Exception;
}
