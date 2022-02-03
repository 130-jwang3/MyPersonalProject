package com.personal.project.dto;

import java.util.*;

public class Instructor extends Person {
    private String[] lastNameCollection = {"Wang","Li","Zhang","Chen","Liu","Devi","Yang","Huang","Singh","Wu"};
    private String[] firstNameCollection = {"Maria","Nushi","Mohammed","Jose","Muhammad","Mohamed","Wei","Mohammad","Ahmed","Yan"};
    private String[] majorCollection = {"CMPT","ENSC"};
    private Random rand = new Random();
    private long min = 6040000000L;
    private long max = 9999999999L;
    private long employeeID;
    //getter and setter for employeeID
    public long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
    }

    //randomly initialize an instructor with first name, last name, major, phone number and email
    public Instructor() {
        String tempLName = lastNameCollection[rand.nextInt(10)];
        String tempFName = firstNameCollection[rand.nextInt(10)];
        String tempMajor = majorCollection[rand.nextInt(2)];
        long tempPhoneNumber = (long) Math.floor(Math.random() * (max - min + 1) + min);
        long tempEmployeeNumber = (long) Math.floor(Math.random() * (max - min + 1) + min);
        String tempEmail = generateEmail(tempFName, tempLName);
        setLastName(tempLName);
        setFirstName(tempFName);
        setMajor(tempMajor);
        setRole("Instructor");
        setEmployeeID(tempEmployeeNumber);
        setPhoneNumber(tempPhoneNumber);
        setEmail(tempEmail);
    }
    //generate a random email according to instructor's first name and last name
    private String generateEmail(String fName, String lName) {
        return fName.substring(0,1) + lName.substring(0, 2) + rand.nextInt(200) + "@gmail.com";
    }
}