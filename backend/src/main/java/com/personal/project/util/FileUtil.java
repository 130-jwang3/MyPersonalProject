package com.personal.project.util;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.personal.project.dto.*;
import org.apache.logging.log4j.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileUtil {
    private static ObjectMapper mapper = new ObjectMapper();
    private static Logger logger=LogManager.getLogger("mylog");

    public static String readAllContent(String fileName) {
        String content = "";
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File has been created.");
            } else {
                content = new String(Files.readAllBytes(Paths.get(fileName)));
            }
        } catch (IOException e) {
            logger.fatal(e);
        }
        return content;
    }

    public static void overwriteFile(String content, String fileName) {
        try {
            File myObj1 = new File(fileName);
            if (myObj1.createNewFile()) {
                System.out.println("File created: " + myObj1.getName());
            } else {
                System.out.println("File already exists.");
                FileWriter myWriter1 = new FileWriter(fileName);
                myWriter1.write(content);
                myWriter1.close();
                System.out.println("File has been overwrite");
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            logger.fatal(e);
        }
    }

    public static void appendFile(String content, String fileName) {
        try {
            String allContent = readAllContent(fileName);
            if (fileName.equals("student.txt")) {
                List<Student> studentList = new ArrayList<>();
                if (!allContent.isEmpty()) {
                    studentList = mapper.readValue(allContent, new TypeReference<List<Student>>() {
                    });
                }
                studentList.add(mapper.readValue(content, Student.class));
                FileWriter writer = new FileWriter(fileName);
                writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(studentList));
                writer.close();
            } else if(fileName.equals("instructor.txt")){
                List<Instructor> instructorList = new ArrayList<>();
                if (!allContent.isEmpty()) {
                    instructorList = mapper.readValue(allContent, new TypeReference<List<Instructor>>() {
                    });
                }
                instructorList.add(mapper.readValue(content, Instructor.class));
                FileWriter writer = new FileWriter(fileName);
                writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(instructorList));
                writer.close();
            }else{

                List<Course> courseList = new ArrayList<>();
                if (!allContent.isEmpty()) {
                    courseList = mapper.readValue(allContent, new TypeReference<List<Course>>() {
                    });
                }
                courseList.add(mapper.readValue(content, Course.class));
                FileWriter writer = new FileWriter(fileName);
                writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(courseList));
                writer.close();
            }
        } catch (Exception e) {
            logger.fatal(e);
        }
    }

}
