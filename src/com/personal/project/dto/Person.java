package com.personal.project.dto;

public abstract class Person {
    private String lastName;
    private String firstName;
    private String major;
    private String role;
    private long phoneNumber;
    private String email;

    //getters and setters for the above field
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person(){

    }

    public Person(String lastName, String firstName, String major, String role, long phoneNumber, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.major = major;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}