package com.personal.project.display;

import com.personal.project.dto.*;
import com.personal.project.service.*;
import de.vandermeer.asciitable.*;
import org.apache.logging.log4j.*;

import java.util.*;

public class InstructorDisplay {
    private static CollegeHRService collegeHRService=new CollegeHRService();
    private static Scanner scanner=new Scanner(System.in);
    private static Logger logger=LogManager.getLogger("mylog");

    public static int instructorMenu() {
        int option=0;
        do{
            chooseMenu();
            option= scanner.nextInt();
            if(option<1||option>6){
                System.out.println("Invalid Input, try again");
                continue;
            }else{
                takeAction(option);
            }
        }while(option!=6);
        return 0;
    }

    private static void chooseMenu() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Choose from the following option");
        at.addRule();
        at.addRow("1. add instructor");
        at.addRule();
        at.addRow("2. change instructor");
        at.addRule();
        at.addRow("3. delete instructor");
        at.addRule();
        at.addRow("4. view all instructor");
        at.addRule();
        at.addRow("5. find by name");
        at.addRule();
        at.addRow("6. return to main menu");
        at.addRule();
        System.out.println(at.render());
    }

    private static void takeAction(int option){
        long employeeID=0;
        switch (option){
            case 1:
                addInstructor();
                break;
            case 2:
                do{
                    System.out.println("Please input valid employee ID:");
                    employeeID= scanner.nextLong();
                }while(collegeHRService.findByEmployeeID(employeeID)==null);
                changeInstructor(employeeID);
                break;
            case 3:
                do{
                    System.out.println("Please input valid Instructor ID:");
                    employeeID= scanner.nextLong();
                }while(collegeHRService.findByEmployeeID(employeeID)==null);
                int result=collegeHRService.deleteById(employeeID,"instructor");
                if(result==0){
                    System.out.println("delete successful");
                }else {
                    System.out.println("unexpected error");
                }
                break;
            case 4:
                generateInstructorList(collegeHRService.findAllInstructor());
                break;
            case 5:
                System.out.println("Please input the name you want to find:");
                String keyword=scanner.next();
                generatePersonList(collegeHRService.findAllHeadsByName(keyword));
                break;
            case 6:
                return;
        }
    }

    private static void addInstructor() {
        boolean isTitle = true, isMajor = true;
        String inputTitle = "", inputMajor = "";
        try {
            System.out.println("Input first name:");
            String firstName = scanner.next();
            System.out.println("Input last name:");
            String lastName = scanner.next();
            do {
                generateMajor();
                System.out.println("Input major:");
                inputMajor = scanner.next();
                isMajor = collegeHRService.testInputMajor(inputMajor);
            } while (isMajor == true);
            Person.Major major = Person.Major.valueOf(inputMajor);
            System.out.println("Input phone number:");
            String phoneNumber = scanner.next();
            System.out.println("Input gender:");
            String gender = scanner.next();
            System.out.println("Input employee ID:");
            long employeeID = scanner.nextLong();
            do {
                generateTitle();
                System.out.println("Input title:");
                inputTitle = scanner.next();
                isTitle = collegeHRService.testInputTitle(inputTitle);
            } while (isTitle == true);
            Instructor.Title title = Instructor.Title.valueOf(inputTitle);
            collegeHRService.addInstructor(firstName, lastName, major, phoneNumber, gender, employeeID, title);
            logger.info("new instructor added to file");
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    private static void generateMajor() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Choose Major");
        for (Person.Major major : Person.Major.values()) {
            at.addRule();
            at.addRow(major);
        }
        at.addRule();
        System.out.println(at.render());
    }

    private static void generateTitle() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Choose Title");
        for (Instructor.Title title : Instructor.Title.values()) {
            at.addRule();
            at.addRow(title);
        }
        at.addRule();
        System.out.println(at.render());
    }

    private static void changeInstructor(Long employeeID) {
        Instructor instructor = collegeHRService.findByEmployeeID(employeeID);
        int option = generateStat(1);
        if (option == 1) {
            System.out.println("Enter the new first name");
            instructor.setFirstName(scanner.next());
        } else if (option == 2) {
            System.out.println("Enter the new last name");
            instructor.setLastName(scanner.next());
        } else if (option == 3) {
            System.out.println("Enter the new phone number");
            instructor.setPhoneNumber(scanner.next());
        } else if (option == 4) {
            System.out.println("Enter the new email");
            instructor.setEmail(scanner.next());
        } else if (option == 5) {
            boolean isMajor = true;
            String inputMajor = "";
            do {
                generateMajor();
                System.out.println("Input major:");
                inputMajor = scanner.next();
                isMajor = collegeHRService.testInputMajor(inputMajor);
            } while (isMajor);
            Person.Major major = Person.Major.valueOf(inputMajor);
            instructor.setMajor(major);
        } else if (option == 6) {
            boolean isTitle = true;
            String inputTitle = "";
            do {
                generateTitle();
                System.out.println("Input major:");
                inputTitle = scanner.next();
                isTitle = collegeHRService.testInputTitle(inputTitle);
            } while (isTitle);
            Instructor.Title title=Instructor.Title.valueOf(inputTitle);
            instructor.setTitle(title);
        }
        collegeHRService.changeByEmployeeID(instructor);
    }

    private static int generateStat(int isInstructor) {
        int result = 0;
        int min = 1, max = 5;
        AsciiTable at = new AsciiTable();
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
        if (isInstructor == 1) {
            at.addRow("6.title");
            at.addRule();
            max = 6;
        }
        do {
            System.out.println(at.render());
            result = scanner.nextInt();
        } while (result < min || result > max);
        return result;
    }

    private static void generatePersonList(List<Person> personList) {
        try {
            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow("Name", "Role","Major");
            personList.forEach(person -> {
                        if(person.getRole().equals("Instructor")){
                            at.addRule();
                            at.addRow(person.getFirstName() + " " + person.getLastName(),
                                    person.getRole(),
                                    person.getMajor());
                        }
                    }
            );
            at.addRule();
            System.out.println(at.render());
        } catch (Exception e) {
            logger.warn(e);
        }

    }

    public static void generateInstructorList(List<Instructor> instructorList) {
        try {
            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow("Name", "Role", "Major", "Employee ID", "phone", "email", "title");
            instructorList.forEach(instructor -> {
                at.addRule();
                at.addRow(instructor.getFirstName() + " " + instructor.getLastName(),
                        instructor.getRole(),
                        instructor.getMajor(),
                        instructor.getEmployeeID(),
                        instructor.getPhoneNumber(),
                        instructor.getEmail(),
                        instructor.getTitle());
            });
            at.addRule();
            System.out.println(at.render());
        } catch (Exception e) {
            logger.warn(e);
        }
    }
}
