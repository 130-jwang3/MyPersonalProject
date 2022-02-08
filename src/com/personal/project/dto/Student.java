package com.personal.project.dto;

import java.util.*;

public class Student extends Person {
    private long studentNumber;
    private int numGrade;
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

    public Student(String lastName, String firstName, String major, String role, long phoneNumber, String email, long studentNumber, int numGrade) {
        super(lastName, firstName, major, role, phoneNumber, email);
        this.studentNumber = studentNumber;
        this.numGrade = numGrade;
    }
}