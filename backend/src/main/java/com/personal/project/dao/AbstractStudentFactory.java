package com.personal.project.dao;

public abstract class AbstractStudentFactory {

     private String type;
     public AbstractStudentFactory(){

     }
     
     public AbstractStudentFactory(String type){
          this.type = type;
     }
     public  abstract StudentDAO getInstance();

}
