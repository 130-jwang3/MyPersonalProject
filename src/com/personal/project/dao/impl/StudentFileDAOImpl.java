package com.personal.project.dao.impl;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.personal.project.dao.*;
import com.personal.project.dto.*;
import com.personal.project.util.*;
import org.apache.logging.log4j.*;

import java.util.*;
import java.util.stream.*;

public class StudentFileDAOImpl implements StudentDAO {

    public static final String STUDENT_FILENAME = "student.txt";
    public static ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger=LogManager.getLogger(StudentFileDAOImpl.class);
    @Override
    public void addStudent(Student student) throws Exception {
        //append to student.txt
        if(findById(student.getStudentNumber())==null){
            FileUtil.appendFile(mapper.writeValueAsString(student), STUDENT_FILENAME);
        }
    }

    @Override
    public Student findById(Long studentId) throws Exception {
        List<Student> studentList = findAll();
        if (studentList != null && studentList.size() > 0) {
            return studentList.stream().filter(stu -> stu.getStudentNumber() == studentId).findFirst().get();
        } else {
            return null;
        }
    }

    @Override
    public List<Student> findAll() throws Exception {
        String content = FileUtil.readAllContent(STUDENT_FILENAME);
        List<Student> studentList = new ArrayList<Student>();
        if(!content.isEmpty()){
            studentList = Arrays.asList(mapper.readValue(content, Student[].class));
        }
        return studentList;
    }

    @Override
    public List<Student> findByName(String name) throws Exception {
        List<Student> studentList = findAll();
        if (studentList != null && studentList.size() > 0) {
            return studentList.stream().
                    filter(stu -> (stu.getFirstName().toLowerCase().contains(name)
                            || stu.getLastName().toLowerCase().contains(name)))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long studentId) throws Exception {
        List<Student> studentList = findAll();
        if (studentList != null && studentList.size() > 0) {
            studentList = studentList.stream().
                    filter(stu -> stu.getStudentNumber() != studentId)
                    .collect(Collectors.toList());
            FileUtil.overwriteFile(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(studentList),STUDENT_FILENAME);
        } else {
            return;
        }
    }

    @Override
    public Student update(Student student) throws Exception{
        List<Student> studentList = findAll();
        if (studentList != null && studentList.size() > 0) {
            for(int i=0;i<studentList.size();i++){
                if(studentList.get(i).getStudentNumber()==student.getStudentNumber()){
                    studentList.set(i,student);
                }
            }
            FileUtil.overwriteFile(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(studentList),STUDENT_FILENAME);
        } else {
            return null;
        }
        return student;
    }
}
