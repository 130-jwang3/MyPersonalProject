package com.personal.project.dto;

import java.util.*;

public class Student extends Person {
    private String[] lastNameCollection = {"Wang","Li","Zhang","Chen","Liu","Devi","Yang","Huang","Singh","Wu"};
    private String[] firstNameCollection = {"Maria","Nushi","Mohammed","Jose","Muhammad","Mohamed","Wei","Mohammad","Ahmed","Yan"};
    private String[] majorCollection = {"CMPT","ENSC"};
    private Random rand = new Random();
    private long studentNumber;
    private int numGrade;
    private String letterGrade;
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

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(int numGrade) {
        if(numGrade>=0&numGrade<50){
            this.letterGrade = "Fail";
        }else if(numGrade>=50&numGrade<54){
            this.letterGrade="D-";
        }else if(numGrade>=54&numGrade<57){
            this.letterGrade="D";
        }else if(numGrade>=57&numGrade<60){
            this.letterGrade="D+";
        }else if(numGrade>=60&numGrade<64){
            this.letterGrade="C-";
        }else if(numGrade>=64&numGrade<67){
            this.letterGrade="C";
        }else if(numGrade>=67&numGrade<70){
            this.letterGrade="C+";
        }else if(numGrade>=70&numGrade<74){
            this.letterGrade="B-";
        }else if(numGrade>=74&numGrade<77){
            this.letterGrade="B";
        }else if(numGrade>=77&numGrade<80){
            this.letterGrade="B+";
        }else if(numGrade>=80&numGrade<88){
            this.letterGrade="A-";
        }else if(numGrade>=88&numGrade<95){
            this.letterGrade="A";
        }else if(numGrade>=95&numGrade<100){
            this.letterGrade="A+";
        }

    }

    //range of random number for student number and phone number
    private long min = 6040000000L;
    private long max = 9999999999L;
    //randomly initialize a student with first name, last name, major, student number, phone number and email
    public Student() {
        String tempLName = lastNameCollection[rand.nextInt(10)];
        String tempFName = firstNameCollection[rand.nextInt(10)];
        String tempMajor = majorCollection[rand.nextInt(2)];
        long tempPhoneNumber = (long) Math.floor(Math.random() * (max - min + 1) + min);
        long tempStudentNumber = (long) Math.floor(Math.random() * (max - min + 1) + min);
        int tempNumGrade=rand.nextInt(101);
        String tempEmail = generateEmail(tempFName, tempLName);
        setLastName(tempLName);
        setFirstName(tempFName);
        setMajor(tempMajor);
        setRole("student");
        setNumGrade(tempNumGrade);
        setLetterGrade(tempNumGrade);
        setStudentNumber(tempStudentNumber);
        setPhoneNumber(tempPhoneNumber);
        setEmail(tempEmail);
    }
    //generate a random email according to instructor's first name and last name
    private String generateEmail(String fName, String lName) {
        return fName.substring(0,1) + lName.substring(0, 2) + (rand.nextInt(200)) + "@gmail.com";
    }
}