package com.personal.project.dao;

import com.personal.project.dao.impl.*;

public class StudentFactory {
    public static StudentDAO getStudentDaoByName(String type){
        switch (type){
            case  "mysql" :
             return new StudentMysqlDAOImpl();
            case "file":
            default:
             return new StudentFileDAOImpl();
        }
    }
}
