package com.personal.project;

import com.personal.project.dto.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import java.util.*;

public class Main {
    public static ObjectMapper mapper=new ObjectMapper();
    public static void main(String[] args){
        List<Person> people=new ArrayList<>();
        addPeopleToList(people);
        String text=generatedTextFromList(people);
        writeToFile(text);
    }
    private static void addPeopleToList(List<Person> people){
        for(int i=0;i<2;i++){
            Instructor instructor=new Instructor();
            people.add(instructor);
        }
        for(int i=0;i<8;i++){
            Student student=new Student();
            people.add(student);
        }
    }
    //turn the list into json format string
    private static String generatedTextFromList(List<Person> people){
        try{
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(people);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }



    }
    private static void writeToFile(String jsonText){
        try {
            File myObj = new File("person.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter=new FileWriter("person.txt");
            myWriter.write(jsonText);
            myWriter.close();
            System.out.println("File has been changed");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
