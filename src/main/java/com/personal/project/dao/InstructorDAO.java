package com.personal.project.dao;

import com.personal.project.dto.*;

import java.util.*;

public interface InstructorDAO {
    public void addInstructor(Instructor instructor) throws Exception;

    public Instructor findById(Long employeeId) throws Exception;

    public List<Instructor> findAll() throws Exception;

    public List<Instructor> findByName(String name) throws Exception;

    public void deleteById(Long employeeId) throws Exception;

    public Instructor update(Instructor instructor) throws Exception;
}
