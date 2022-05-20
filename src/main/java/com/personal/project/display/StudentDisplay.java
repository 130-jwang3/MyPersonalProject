package com.personal.project.display;

import com.personal.project.dto.*;
import com.personal.project.service.*;
import de.vandermeer.asciitable.*;
import org.apache.logging.log4j.*;

import java.util.*;
import java.util.stream.*;

public class StudentDisplay {
    private static CollegeHRService collegeHRService=new CollegeHRService();
    private static Scanner scanner=new Scanner(System.in);
    private static Logger logger=LogManager.getLogger("mylog");

    public static int studentMenu() {
        int option=0;
        do{
            chooseMenu();
            option= scanner.nextInt();
            if(option<1||option>8){
                System.out.println("Invalid Input, try again");
                continue;
            }else{
                takeAction(option);
            }
        }while(option!=8);
        return 0;
    }

    private static void chooseMenu() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Choose from the following option");
        at.addRule();
        at.addRow("1. add student");
        at.addRule();
        at.addRow("2. change student");
        at.addRule();
        at.addRow("3. delete student");
        at.addRule();
        at.addRow("4. view all student");
        at.addRule();
        at.addRow("5. find by name");
        at.addRule();
        at.addRow("6. leave school");
        at.addRule();
        at.addRow("7. display grade");
        at.addRule();
        at.addRow("8. return to main menu");
        at.addRule();
        System.out.println(at.render());
    }

    private static void takeAction(int option){
        long studentID=0;
        switch (option){
            case 1:
                addStudent();
                break;
            case 2:
                do{
                    System.out.println("Please input valid student ID:");
                    studentID= scanner.nextLong();
                }while(collegeHRService.findByStudentID(studentID)==null);
                changeStudent(studentID);
                break;
            case 3:
                do{
                    System.out.println("Please input valid student ID:");
                    studentID= scanner.nextLong();
                }while(collegeHRService.findByStudentID(studentID)==null);
                int result=collegeHRService.deleteById(studentID,"Student");
                if(result==0){
                    System.out.println("delete successful");
                }else {
                    System.out.println("unexpected error");
                }
                break;
            case 4:
                generateStudentList(collegeHRService.findAllStudent());
                break;
            case 5:
                System.out.println("Please input the name you want to find:");
                String keyword=scanner.next();
                generatePersonList(collegeHRService.findAllHeadsByName(keyword));
                break;
            case 6:
                do{
                    System.out.println("Please input valid student ID:");
                    studentID= scanner.nextLong();
                }while(collegeHRService.findByStudentID(studentID)==null);
                leaveSchool(studentID);
                break;
            case 7:
                do{
                    System.out.println("Please input valid student ID:");
                    studentID= scanner.nextLong();
                }while(collegeHRService.findByStudentID(studentID)==null);
                displayGrade(studentID);
                break;
            case 8:
                return;

        }
    }

    private static void addStudent(){
        boolean isMajor = true;
        String inputMajor = "";
        boolean isType=true;
        String inputType="";
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
            } while (isMajor);
            Person.Major major = Person.Major.valueOf(inputMajor);
            System.out.println("Input phone number:");
            String phoneNumber = scanner.next();
            System.out.println("Input gender");
            String gender=scanner.next();
            System.out.println("Input student number:");
            long studentID = scanner.nextLong();
            do {
                generateType();
                System.out.println("Input type:");
                inputType = scanner.next();
                isType = collegeHRService.testInputType(inputType);
            } while (isType);
            Student.Type type = Student.Type.valueOf(inputType);
            Student student=new Student(lastName,firstName,major,"Student",phoneNumber,gender,studentID,type);
            collegeHRService.addStudent(student);
            logger.info("new student added to file");
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

    private static void generateType(){
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Choose Type");
        for (Student.Type type : Student.Type.values()) {
            at.addRule();
            at.addRow(type);
        }
        at.addRule();
        System.out.println(at.render());
    }

    private static void changeStudent(Long studentID) {
        try {
            Student student = collegeHRService.findByStudentID(studentID);
            int option = generateStat();
            if (option == 1) {
                System.out.println("Enter the new first name");
                student.setFirstName(scanner.next());
            } else if (option == 2) {
                System.out.println("Enter the new last name");
                student.setLastName(scanner.next());
            } else if (option == 3) {
                System.out.println("Enter the new phone number");
                student.setPhoneNumber(scanner.next());
            } else if (option == 4) {
                System.out.println("Enter the new email");
                student.setEmail(scanner.next());
            } else if (option == 5) {
                boolean isMajor;
                String inputMajor;
                do {
                    generateMajor();
                    System.out.println("Input major:");
                    inputMajor = scanner.next();
                    isMajor = collegeHRService.testInputMajor(inputMajor);
                } while (isMajor);
                Person.Major major = Person.Major.valueOf(inputMajor);
                student.setMajor(major);
            }
            collegeHRService.changeByStudentID(student);
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    private static int generateStat() {
        int result = 0;
        int min = 1, max = 6;
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
            at.addRow("6.title");
            at.addRule();
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
                        if(person.getRole().equals("Student")){
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

    private static void generateStudentList(List<Student> studentList) {
        try {
            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow("Name", "Role", "Major", "Student ID", "phone" ,"type");
            studentList.forEach(student -> {
                at.addRule();
                at.addRow(student.getFirstName() + " " + student.getLastName(),
                        student.getRole(),
                        student.getMajor(),
                        student.getStudentNumber(),
                        student.getPhoneNumber(),
                        student.getType());
            });
            at.addRule();
            System.out.println(at.render());
        } catch (Exception e) {
            logger.warn(e);
        }

    }

    private static void leaveSchool(Long studentID){
        List<Course> courses=collegeHRService.findAllCourses();
        courses=courses.stream().filter(course -> collegeHRService.findCourseStudentByID(studentID,course)!=null).collect(Collectors.toList());
        courses.forEach(course -> {Course.CourseStudent student=collegeHRService.findCourseStudentByID(studentID,course);
        if(student.getState()== Course.StudentCourseState.ENROLLED){
            student.setState(Course.StudentCourseState.FAILED);
            student.setScore(0.0);
            course.getFinishedStudent().add(student);
            Set<Course.CourseStudent> courseStudents=course.getStudentEnrolled();
            courseStudents.remove(student);
            if(!course.getWaitList().isEmpty()){
                Course.CourseStudent courseStudent=course.getWaitList().poll();
                courseStudent.setState(Course.StudentCourseState.ENROLLED);
                courseStudents.add(courseStudent);
                Queue<Course.CourseStudent> studentQueue=course.getWaitList();
                studentQueue.poll();
                course.setWaitList(studentQueue);
            }
            course.setStudentEnrolled(courseStudents);
        }else if(student.getState()== Course.StudentCourseState.WAITLISTED){
            Queue<Course.CourseStudent> courseStudents=course.getWaitList();
            courseStudents.remove(student);
            course.setWaitList(courseStudents);
        }
        collegeHRService.updateCourse(course);
        });
        collegeHRService.deleteById(studentID,"Student");
    }

    public static void displayGrade(Long studentID){
        List<Course> courses=collegeHRService.findAllCourses();
        courses=courses.stream().filter(course -> collegeHRService.findCourseStudentByID(studentID,course).getState()== Course.StudentCourseState.FINISHED||
                collegeHRService.findCourseStudentByID(studentID,course).getState()== Course.StudentCourseState.FAILED).collect(Collectors.toList());
        int totalCredit=0;
        List<Double> scores=new ArrayList<>();
        for (Course course : courses) {
            scores.add(collegeHRService.findCourseStudentByID(studentID, course).getScore());
            totalCredit += course.getCredit();
        }
        double totalGPA=0;
        for(Double score:scores){
            if(score<=50){
                totalGPA+=0;
            }else if(score>50&&score<=60){
                totalGPA+=1.15;
            }else if(score>60&&score<=70){
                totalGPA+=2;
            }else if(score>70&&score<=80){
                totalGPA+=3;
            }else if(score>80&&score<=100){
                totalGPA+=4;
            }
        }
        AsciiTable at=new AsciiTable();
        at.addRule();
        at.addRow("Name","total credit achieved","CGPA");
        at.addRule();
        at.addRow(collegeHRService.findByStudentID(studentID).getFirstName()+" "+collegeHRService.findByStudentID(studentID).getLastName(),totalCredit,totalGPA/totalCredit);
    }
}
