package com.personal.project.dao.impl;

import com.personal.project.dao.*;

public class StudentDAOFileFactory extends AbstractStudentFactory {

    public StudentDAOFileFactory(){
        super("");
    }
    @Override
    public StudentDAO getInstance() {
        return new StudentFileDAOImpl();
    }
}
