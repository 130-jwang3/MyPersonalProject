package com.personal.project;

import com.personal.project.display.*;
import com.personal.project.dto.*;
import com.personal.project.service.*;
import de.vandermeer.asciitable.*;
import de.vandermeer.skb.interfaces.transformers.textformat.*;
import org.apache.logging.log4j.*;

import java.util.*;

import java.sql.*;


public class Main {
    public static CollegeHRService collegeHRService = new CollegeHRService();
    public static Scanner scanner = new Scanner(System.in);
    private static Logger logger = LogManager.getLogger("mylog");
    public static StudentDisplay studentDisplay=new StudentDisplay();
    public static CourseDisplay courseDisplay=new CourseDisplay();
    public static InstructorDisplay instructorDisplay=new InstructorDisplay();

    public static void main(String[] args) throws Exception{

        String menu = makeMenu();
        int option = 0;
        do {
            System.out.println(menu);
            option = scanner.nextInt();
            logger.info("user input service number");
            if (option == 4) {
                System.out.println("GoodBye!");
                break;
            } else if (option < 4 && option > 0) {
                takeAction(option);
            } else {
                System.out.println("not a valid option, choose again");
                logger.warn("user chose invalid service");
                continue;
            }
        } while (option != 4);

    }

    private static void takeAction(int option) {
        try {
            switch (option) {
                case 1:
                    studentDisplay.studentMenu();
                    break;
                case 2:
                    courseDisplay.courseMenu();
                    break;
                case 3:
                    instructorDisplay.instructorMenu();
                    break;
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static String makeMenu() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Welcome to College HR Service").setTextAlignment(TextAlignment.CENTER);
        at.addRule();
        at.addRow("Choose from the following:");
        at.addRule();
        at.addRow("1. student");
        at.addRule();
        at.addRow("2. course");
        at.addRule();
        at.addRow("3. Instructor");
        at.addRule();
        at.addRow("4. quit");
        at.addRule();
        return at.render();
    }


}
