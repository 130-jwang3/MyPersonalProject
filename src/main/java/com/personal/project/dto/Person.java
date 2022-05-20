package com.personal.project.dto;

public abstract class Person {
    public enum Major{
        COMPUTER_SCIENCE,
        ENGINEER,
        MATH,
        PHYSICS,
        APPLIED_SCIENCE,
        ARTS
    }
    private String lastName;
    private String firstName;
    private String role;
    private Major major;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;
    private String phoneNumber;

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    public Person(String lastName, String firstName, Major major, String role, String phoneNumber, String gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.major = major;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}