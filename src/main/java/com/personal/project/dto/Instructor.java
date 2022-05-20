package com.personal.project.dto;

import java.util.*;

public class Instructor extends Person {
    public enum Title{
        Assistant_professor,
        Associate_professor,
        professor
    }
    private long employeeID;
    private Title title;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    //getter and setter for employeeID
    public long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
    }

    public Instructor() {
        setRole("Instructor");
    }

    public Instructor(String lastName, String firstName, Major major, String role, String phoneNumber, String gender, long employeeID, Title title) {
        super(lastName, firstName, major, role, phoneNumber, gender);
        this.employeeID = employeeID;
        this.title=title;
    }
}