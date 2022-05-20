package com.personal.project.dao;

import com.personal.project.dao.impl.*;
import com.personal.project.dto.*;

import java.util.*;

public interface CourseDAO  {
    public void addCourse(Course course) throws Exception;

    public Course findById(String courseId) throws Exception;

    public List<Course> findAll() throws Exception;

    public Course findByName(String name) throws Exception;

    public void deleteById(Long courseID) throws Exception;

    public Course update(Course course) throws Exception;
}
