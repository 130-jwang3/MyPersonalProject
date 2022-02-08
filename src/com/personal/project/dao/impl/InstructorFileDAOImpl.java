package com.personal.project.dao.impl;

import com.fasterxml.jackson.databind.*;
import com.personal.project.dao.*;
import com.personal.project.dto.*;
import com.personal.project.util.*;

import java.util.*;
import java.util.stream.*;

public class InstructorFileDAOImpl implements InstructorDAO {

    public static final String INSTRUCTOR_FILENAME = "instructor.txt";
    public static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void addInstructor(Instructor instructor) throws Exception {
        FileUtil.appendFile(mapper.writeValueAsString(instructor), INSTRUCTOR_FILENAME);
    }

    @Override
    public Instructor findById(Integer employeeId) throws Exception {
        List<Instructor> instructorList = findAll();
        if (instructorList != null && instructorList.size() > 0) {
            return instructorList.stream().filter(stu -> stu.getEmployeeID() == employeeId).findFirst().get();
        } else {
            return null;
        }
    }

    @Override
    public List<Instructor> findAll() throws Exception {
        String content = FileUtil.readAllContent(INSTRUCTOR_FILENAME);
        List<Instructor> instructorList = new ArrayList<Instructor>();
        if(!content.isEmpty()){
            instructorList = Arrays.asList(mapper.readValue(content, Instructor[].class));
        }
        return instructorList;
    }

    @Override
    public List<Instructor> findByName(String name) throws Exception {
        List<Instructor> instructorList = findAll();
        if (instructorList != null && instructorList.size() > 0) {
            return instructorList.stream().
                    filter(stu -> stu.getFirstName().toLowerCase().contains(name)
                            || stu.getLastName().toLowerCase().contains(name))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Integer employeeId) throws Exception {
        List<Instructor> instructorList = findAll();
        if (instructorList != null && instructorList.size() > 0) {
            instructorList = instructorList.stream().
                    filter(stu -> stu.getEmployeeID() != employeeId)
                    .collect(Collectors.toList());
            FileUtil.overwriteFile(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(instructorList),INSTRUCTOR_FILENAME);
        } else {
            return;
        }
    }

    @Override
    public Instructor update(Instructor instructor) {
        return null;
    }
}
