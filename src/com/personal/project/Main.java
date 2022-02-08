package com.personal.project;

import com.personal.project.dto.*;
import com.personal.project.service.*;
import de.vandermeer.asciitable.*;

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
            if (option == 4) {
                System.out.println("GoodBye!");
                break;
            } else {
                if (option != 3) {
                    System.out.println(personTypeMenu);
                    optionPersonType = scanner.nextInt();
                }
                takeAction(option, optionPersonType);
            }

        } while (option != 4);
    }

    private static String makeMenu() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Choose from 4 options:");
        at.addRule();
        at.addRow("1. add person");
        at.addRule();
        at.addRow("2. delete person");
        at.addRule();
        at.addRow("3. find person by name");
        at.addRule();
        at.addRow("4. quit");
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
                int studentID = scanner.nextInt();
                collegeHRService.deleteById(studentID, "Student");
            } else {
                System.out.println("enter employee ID:");
                int employeeID = scanner.nextInt();
                collegeHRService.deleteById(employeeID, "Instructor");
            }
        } else if (option == 3) {
            System.out.println("please enter the name you want to find");
            String name = scanner.next();
            List<Person> personList=collegeHRService.findAllHeadsByName(name);
            generatePersonList(personList);
        }
    }
    private static void generatePersonList(List<Person> personList){
        AsciiTable at= new AsciiTable();
        at.addRule();
        at.addRow("Name","Role");
        personList.forEach(person -> {
            at.addRule();
            at.addRow(person.getFirstName()+person.getLastName(),person.getRole());}
            );
        at.addRule();
        System.out.println(at.render());
    }
}
