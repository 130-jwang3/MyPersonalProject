package com.personal.project.dao.impl.mysql;

import com.personal.project.dao.*;
import com.personal.project.dto.*;
import com.personal.project.util.*;

import java.sql.*;
import java.util.*;

public class
InstructorMysqlDAOImpl implements InstructorDAO {

    @Override
    public void addInstructor(Instructor instructor) throws Exception {
        Boolean result = SQLExecutor.runTransaction( (conn -> {
                    Long oid = SQLExecutor.runInsertWithPK(InstructorSQLDef.INS_ADD_PERSON,List.of(instructor.getLastName(),instructor.getFirstName(),instructor.getGender(),instructor.getPhoneNumber()));
                    SQLExecutor.runUpdate(InstructorSQLDef.INS_ADD_INSTRUCTOR, List.of(instructor.getEmployeeID() + "",instructor.getTitle() + "",oid));
                })
        );
        if (result==Boolean.FALSE) throw new Exception("Add instructor Failed");
    }

    @Override
    public Instructor findByOid(long employeeOid) throws Exception {

        return new SQLExecutor<Instructor>().runQuery(InstructorSQLDef.INS_FIND_BY_OID, List.of(employeeOid), (rs -> {
            Instructor instructor = null;
            while (rs.next()) {
                instructor = getInstructorFromResultSet(rs);
            }
            return instructor;
        }));
    }

    @Override
    public Instructor findByCourseId(String courseId) throws Exception {
        return new SQLExecutor<Instructor>().runQuery(InstructorSQLDef.INS_BY_COURSE_ID, List.of(courseId), (rs -> {
            Instructor instructor = null;
            while (rs.next()) {
                instructor = getInstructorFromResultSet(rs);
            }
            return instructor;
        }));
    }


    @Override
    public Instructor findById(Long employeeId) throws Exception {
        return new SQLExecutor<Instructor>().runQuery(InstructorSQLDef.INS_FIND_BY_ID, List.of(employeeId), (rs -> {
            Instructor instructor = null;
            while (rs.next()) {
                instructor = getInstructorFromResultSet(rs);
            }
            return instructor;
        }));
    }

    @Override
    public List<Instructor> findAll() throws Exception {
        return new SQLExecutor<List<Instructor>>().runQuery(InstructorSQLDef.INS_FIND_ALL, null, (rs -> {
            List<Instructor> instructors = new ArrayList<>();
            while (rs.next()) {
                Instructor instructor=getInstructorFromResultSet(rs);
                instructors.add(instructor);
            }
            return instructors;
        }));
    }

    @Override
    public List<Instructor> findByName(String name) throws Exception {
        return new SQLExecutor<List<Instructor>>().runQuery(InstructorSQLDef.INS_FIND_BY_NAME, List.of("%" + name + "%", "%" + name + "%"), (rs -> {
            List<Instructor> instructors = new ArrayList<>();
            while (rs.next()) {
                Instructor instructor=getInstructorFromResultSet(rs);
                instructors.add(instructor);
            }
            return instructors;
        }));
    }

    @Override
    public void deleteById(Long employeeId) throws Exception {
        int count=SQLExecutor.runUpdate(InstructorSQLDef.INS_DELETE_BY_ID,List.of(employeeId));
        System.out.println(count + "row/s affected");
    }

    @Override
    public Instructor update(Instructor instructor) throws Exception {
        int count=SQLExecutor.runUpdate(InstructorSQLDef.INS_UPDATE,List.of(instructor.getLastName(),instructor.getFirstName()
                ,instructor.getPhoneNumber()+"",instructor.getEmployeeID()+""));
        System.out.println(count + "row/s affected");
        Instructor instructor1 = findById(instructor.getEmployeeID());
        return instructor1;
    }



    private Instructor getInstructorFromResultSet(ResultSet rs){
        Instructor instructor=new Instructor();
        instructor.setRole("Instructor");
        try{
            if (SQLExecutor.hasColumn(rs, "oid"))
                instructor.setInstructor_oid(rs.getLong("oid"));
            if (SQLExecutor.hasColumn(rs, "first_name"))
                instructor.setFirstName(rs.getString("first_name"));
            if (SQLExecutor.hasColumn(rs, "last_name"))
                instructor.setLastName(rs.getString("last_name"));
            if (SQLExecutor.hasColumn(rs, "employee_id"))
                instructor.setEmployeeID(rs.getLong("employee_id"));
            if (SQLExecutor.hasColumn(rs, "phone"))
                instructor.setPhoneNumber(rs.getString("phone"));
            if (SQLExecutor.hasColumn(rs, "gender"))
                instructor.setGender(rs.getString("gender"));
            if (SQLExecutor.hasColumn(rs, "title"))
                instructor.setTitle(Instructor.Title.valueOf(rs.getString("title")));
        }catch(SQLException e){
            e.printStackTrace();
        }
        return instructor;
    }
}
