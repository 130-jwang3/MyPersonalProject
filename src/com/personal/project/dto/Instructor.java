package com.personal.project.dto;

import java.util.*;

public class Instructor extends Person {
    private long employeeID;

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

    public Instructor(String lastName, String firstName, String major, String role, long phoneNumber, String email, long employeeID) {
        super(lastName, firstName, major, role, phoneNumber, email);
        this.employeeID = employeeID;
    }
}