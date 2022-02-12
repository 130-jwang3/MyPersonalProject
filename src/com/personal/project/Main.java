package com.personal.project;

import com.personal.project.dto.*;
import com.personal.project.service.*;
import de.vandermeer.asciitable.*;
import org.apache.logging.log4j.*;

import java.util.*;


public class Main {
    public static CollegeHRService collegeHRService = new CollegeHRService();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String menu = makeMenu();
        String personTypeMenu = makePersonTypeMenu();
        int option = 0;
        int optionPersonType = 0;
        do {
            System.out.println(menu);
            option = scanner.nextInt();
            if (option == 6) {
                System.out.println("GoodBye!");
                break;
            } else {
                if (option != 5) {
                    System.out.println(personTypeMenu);
                    optionPersonType = scanner.nextInt();
                }
                takeAction(option, optionPersonType);
            }

        } while (option != 6);
    }

    private static String makeMenu() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Choose from 5 options:");
        at.addRule();
        at.addRow("1. add person");
        at.addRule();
        at.addRow("2. delete person");
        at.addRule();
        at.addRow("3. view all person");
        at.addRule();
        at.addRow("4. change person");
        at.addRule();
        at.addRow("5. find person by name");
        at.addRule();
        at.addRow("6. quit");
        at.addRule();
        return at.render();
    }

    private static String makePersonTypeMenu() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Choose from 2 options:");
        at.addRule();
        at.addRow("1. Student");
        at.addRule();
        at.addRow("2. Instructor");
        at.addRule();
        return at.render();
    }

    private static void addStudent() {
        System.out.println("Input first name:");
        String firstName = scanner.next();
        System.out.println("Input last name:");
        String lastName = scanner.next();
        System.out.println("Input major:");
        String major = scanner.next();
        System.out.println("Input phone number:");
        long phoneNumber = scanner.nextLong();
        System.out.println("Input email:");
        String email = scanner.next();
        System.out.println("Input student number:");
        long studentID = scanner.nextLong();
        System.out.println("Input numerical grade:");
        int numGrade = scanner.nextInt();
        collegeHRService.addStudent(firstName, lastName, major, phoneNumber, email, studentID, numGrade);
    }

    private static void addInstructor() {
        System.out.println("Input first name:");
        String firstName = scanner.next();
        System.out.println("Input last name:");
        String lastName = scanner.next();
        System.out.println("Input major:");
        String major = scanner.next();
        System.out.println("Input phone number:");
        long phoneNumber = scanner.nextLong();
        System.out.println("Input email:");
        String email = scanner.next();
        System.out.println("Input employee ID:");
        long employeeID = scanner.nextLong();
        collegeHRService.addInstructor(firstName, lastName, major, phoneNumber, email, employeeID);
    }

    private static void takeAction(int option, int optionPersonType) {
        if (option == 1) {
            if (optionPersonType == 1) {
                addStudent();
            } else {
                addInstructor();
            }
        } else if (option == 2) {
            if (optionPersonType == 1) {
                System.out.println("enter student ID:");
                long studentID = scanner.nextInt();
                collegeHRService.deleteById(studentID, "Student");
            } else {
                System.out.println("enter employee ID:");
                long employeeID = scanner.nextInt();
                collegeHRService.deleteById(employeeID, "Instructor");
            }
        } else if (option == 3) {
            if(optionPersonType == 1){
                generateStudentList(collegeHRService.findAllStudent());
            }else{
                generateInstructorList(collegeHRService.findAllInstructor());
            }
        }else if (option == 4){
            if(optionPersonType == 1){
                System.out.println("enter student ID:");
                long studentID = scanner.nextInt();
                changeStudent(studentID);
            }else{
                System.out.println("enter employee ID:");
                long employeeID = scanner.nextInt();
                changeInstructor(employeeID);
            }
        }else if(option==5){
            System.out.println("please enter the name you want to find");
            String name = scanner.next();
            List<Person> personList=collegeHRService.findAllHeadsByName(name);
            generatePersonList(personList);
        }
    }

    private static void changeStudent(Long studentID){
        Student student=collegeHRService.findByStudentID(studentID);
        int option=generateStat();
        if(option==1){
            System.out.println("Enter the new first name");
            student.setFirstName(scanner.next());
        }else if(option==2){
            System.out.println("Enter the new last name");
            student.setLastName(scanner.next());
        }else if(option==3){
            System.out.println("Enter the new phone number");
            student.setPhoneNumber(scanner.nextLong());
        }else if(option==4){
            System.out.println("Enter the new email");
            student.setEmail(scanner.next());
        }else if(option==5){
            System.out.println("Enter the new major");
            student.setMajor(scanner.next());
        }
        collegeHRService.changeByStudentID(student);
    }

    private static void  changeInstructor(Long employeeID){
        Instructor instructor=collegeHRService.findByEmployeeID(employeeID);
        int option=generateStat();
        if(option==1){
            System.out.println("Enter the new first name");
            instructor.setFirstName(scanner.next());
        }else if(option==2){
            System.out.println("Enter the new last name");
            instructor.setLastName(scanner.next());
        }else if(option==3){
            System.out.println("Enter the new phone number");
            instructor.setPhoneNumber(scanner.nextLong());
        }else if(option==4){
            System.out.println("Enter the new email");
            instructor.setEmail(scanner.next());
        }else if(option==5){
            System.out.println("Enter the new major");
            instructor.setMajor(scanner.next());
        }
        collegeHRService.changeByEmployeeID(instructor);
    }

    private static int generateStat(){
        AsciiTable at=new AsciiTable();
        at.addRule();
        at.addRow("Choose 1 to change");
        at.addRule();
        at.addRow("1.First name");
        at.addRule();
        at.addRow("2.Last name");
        at.addRule();
        at.addRow("3.phone number");
        at.addRule();
        at.addRow("4.email");
        at.addRule();
        at.addRow("5.major");
        at.addRule();
        System.out.println(at.render());
        return scanner.nextInt();
    }

    private static void generatePersonList(List<Person> personList){
        AsciiTable at= new AsciiTable();
        at.addRule();
        at.addRow("Name","Role");
        personList.forEach(person -> {
            at.addRule();
            at.addRow(person.getFirstName()+" "+person.getLastName(),person.getRole());}
            );
        at.addRule();
        System.out.println(at.render());
    }

    private static void generateStudentList(List<Student> studentList){
        AsciiTable at=new AsciiTable();
        at.addRule();
        at.addRow("Name","Role","Major","Student ID","phone","email","numerical grade");
        studentList.forEach(student -> {
            at.addRule();
            at.addRow(student.getFirstName()+" "+student.getLastName(),
                    student.getRole(),
                    student.getMajor(),
                    student.getStudentNumber(),
                    student.getPhoneNumber(),
                    student.getEmail(),
                    student.getNumGrade());
        });
        at.addRule();
        System.out.println(at.render());
    }

    private static void generateInstructorList(List<Instructor> instructorList){
        AsciiTable at=new AsciiTable();
        at.addRule();
        at.addRow("Name","Role","Major","Employee ID","phone","email");
        instructorList.forEach(instructor -> {
            at.addRule();
            at.addRow(instructor.getFirstName()+" "+instructor.getLastName(),
                    instructor.getRole(),
                    instructor.getMajor(),
                    instructor.getEmployeeID(),
                    instructor.getPhoneNumber(),
                    instructor.getEmail());
        });
        at.addRule();
        System.out.println(at.render());
    }

}
