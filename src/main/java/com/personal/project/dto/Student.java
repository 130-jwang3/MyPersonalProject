package com.personal.project.dto;

import java.util.*;

public class Student extends Person {
    public enum Type{
        Undergraduate,
        Graduate
    }
    private Type type;
    private long studentNumber;
    private int numGrade=0;
    //getter setter for student number/student ID, numerical grade and letter grade
    public long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getNumGrade() {
        return numGrade;
    }

    public void setNumGrade(int numGrade) {
        this.numGrade = numGrade;
    }

    public Student() {
        setRole("Student");
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Student(String lastName, String firstName, Major major, String role, String phoneNumber, String gender, long studentNumber, Type type) {
        super(lastName, firstName, major, role, phoneNumber, gender);
        this.studentNumber = studentNumber;
        this.type=type;
    }
}