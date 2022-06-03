package com.personal.project.dto;

import lombok.*;

import javax.persistence.*;
import java.util.*;
@Entity
public class Instructor extends Person {
    public enum Title{
        Assistant_professor,
        Associate_professor,
        professor
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long instructor_oid;
    private long employeeID;
    private Title title;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    //getter and setter for employeeID
    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Long getInstructor_oid() {
        return instructor_oid;
    }

    public void setInstructor_oid(Long instructor_oid) {
        this.instructor_oid = instructor_oid;
    }

    public Instructor() {
        setRole("Instructor");
    }

    public Instructor(String lastName, String firstName, String role, String phoneNumber, String gender, long employeeID, Title title) {
        super(lastName, firstName, role, phoneNumber, gender);
        this.employeeID = employeeID;
        this.title=title;
    }
}