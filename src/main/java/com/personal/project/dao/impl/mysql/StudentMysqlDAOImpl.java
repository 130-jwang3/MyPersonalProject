package com.personal.project.dao.impl.mysql;

import com.personal.project.dao.*;
import com.personal.project.dto.*;
import com.personal.project.util.*;

import java.sql.*;
import java.util.*;

public class StudentMysqlDAOImpl implements StudentDAO {
    @Override
    public void addStudent(Student student) throws Exception {
        Boolean result = SQLExecutor.runTransaction((conn -> {
                    Long oid = SQLExecutor.runInsertWithPK(StudentSQLDef.STU_ADD_STUDENT_1, List.of(student.getLastName(), student.getFirstName(), student.getGender(), student.getPhoneNumber()));
                    SQLExecutor.runUpdate(StudentSQLDef.STU_ADD_STUDENT_2, List.of(student.getStudentNumber() + "", student.getType().name(), student.getMajor().name(), oid));
                })
        );
        if (!result) throw new Exception("Add Student Failed");
    }

    @Override
    public Student findById(Long studentId) throws Exception {

        Student t = new SQLExecutor<Student>().runQuery(StudentSQLDef.STU_FIND_BY_ID, List.of(studentId), (rs -> {
            Student student = null;
            while (rs.next()) {
                student = getStudentFromResultSet(rs);
            }
            return student;
        }));
        return t;

    }

    @Override
    public Student findByOid(Long studentOid) throws Exception {
        Student t = new SQLExecutor<Student>().runQuery(StudentSQLDef.STU_FIND_BY_OID, List.of(studentOid), (rs -> {
            Student student = null;
            while (rs.next()) {
                student = getStudentFromResultSet(rs);
            }
            return student;
        }));
        return t;
    }


    @Override
    public List<Student> findAll() throws Exception {
        return new SQLExecutor<List<Student>>().runQuery(StudentSQLDef.STU_FIND_ALL, null, (rs -> {
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = getStudentFromResultSet(rs);
                students.add(student);
            }
            return students;
        }));
    }

    @Override
    public List<Student> findByName(String name) throws Exception {
        return new SQLExecutor<List<Student>>().runQuery(StudentSQLDef.STU_FIND_BY_NAME, List.of("%" + name + "%", "%" + name + "%"), (rs -> {
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = getStudentFromResultSet(rs);
                students.add(student);
            }
            return students;
        }));
    }

    @Override
    public void deleteById(Long studentId) throws Exception {
        int count = SQLExecutor.runUpdate(StudentSQLDef.STU_DELETE_BY_ID, List.of(studentId));
        System.out.println(count + "row/s affected");
    }

    @Override
    public Student update(Student student) throws Exception {
        int count = SQLExecutor.runUpdate(StudentSQLDef.STU_UPDATE, List.of(student.getLastName(), student.getFirstName()
                , student.getPhoneNumber() + "", student.getMajor() + "", student.getStudentNumber() + ""));
        System.out.println(count + "row/s affected");
        Student student1 = findById(student.getStudentNumber());
        return student1;
    }

    private Student getStudentFromResultSet(ResultSet rs) {
        Student student = new Student();
        student.setRole("Student");
        try {
            if (SQLExecutor.hasColumn(rs, "oid"))
                student.setStudentOID(rs.getLong("oid"));
            if (SQLExecutor.hasColumn(rs, "first_name"))
                student.setFirstName(rs.getString("first_name"));
            if (SQLExecutor.hasColumn(rs, "last_name"))
                student.setLastName(rs.getString("last_name"));
            if (SQLExecutor.hasColumn(rs, "major"))
                student.setMajor(Student.Major.valueOf(rs.getString("major")));
            if (SQLExecutor.hasColumn(rs, "student_id"))
                student.setStudentNumber(rs.getLong("student_id"));
            if (SQLExecutor.hasColumn(rs, "phone"))
                student.setPhoneNumber(rs.getString("phone"));
            if (SQLExecutor.hasColumn(rs, "type"))
                student.setType(Student.Type.valueOf(rs.getString("type")));
            if (SQLExecutor.hasColumn(rs, "gender"))
                student.setGender(rs.getString("gender"));
            if (SQLExecutor.hasColumn(rs, "num_grade"))
                student.setNumGrade((int) rs.getDouble("num_grade"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

}
