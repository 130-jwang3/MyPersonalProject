package com.personal.project.dao.impl.mysql;

import com.personal.project.dao.*;
import com.personal.project.dto.*;
import com.personal.project.util.*;

import java.sql.*;
import java.util.*;

public class CourseStudentMysqlDAOImpl implements CourseStudentDAO {
    @Override
    public List<CourseStudent> findByCourseOid(Long courseOid) throws Exception{
        return new SQLExecutor<List<CourseStudent>>().runQuery(CourseStudentSQLDef.CS_FIND_BY_CID, List.of(courseOid), (rs -> {
            List<CourseStudent> courseStudents = new ArrayList<>();
            while (rs.next()) {
                CourseStudent courseStudent = getCourseStudentFromResultSet(rs);
                courseStudents.add(courseStudent);
            }
            return courseStudents;
        }));
    }

    @Override
    public void addCourseStudent(CourseStudent courseStudent) throws Exception{
        int count=SQLExecutor.runUpdate(CourseStudentSQLDef.ADD_COURSE_STUDENT,List.of(courseStudent.getStudentOid(),courseStudent.getCourseOid(),courseStudent.getState()+"",courseStudent.getCourseRole(),courseStudent.getScore()));
        System.out.println(count + "row/s affected");
    }

    @Override
    public CourseStudent updateCourseStudent(CourseStudent courseStudent) throws Exception {
        int count=SQLExecutor.runUpdate(CourseStudentSQLDef.CS_UPDATE,List.of(courseStudent.getState()+"",courseStudent.getCourseRole(),courseStudent.getScore(),courseStudent.getCourseOid(),courseStudent.getStudentOid()));
        System.out.println(count + "row/s affected");
        return courseStudent;
    }

    @Override
    public void deleteCourseStudent(Long course_oid, Long student_oid) throws Exception {
        int count=SQLExecutor.runUpdate(CourseStudentSQLDef.CS_DELETE,List.of(course_oid,student_oid));
        System.out.println(count + "row/s affected");
    }

    private CourseStudent getCourseStudentFromResultSet(ResultSet rs){
        CourseStudent courseStudent=new CourseStudent();
        try{
            if (SQLExecutor.hasColumn(rs, "student_oid"))
                courseStudent.setStudentOid(rs.getLong("student_oid"));
            if (SQLExecutor.hasColumn(rs, "course_oid"))
                courseStudent.setCourseOid(rs.getLong("course_oid"));
            if (SQLExecutor.hasColumn(rs, "course_role"))
                courseStudent.setCourseRole(rs.getString("course_role"));
            if (SQLExecutor.hasColumn(rs, "course_grade"))
                courseStudent.setScore(rs.getDouble("course_grade"));
            if (SQLExecutor.hasColumn(rs, "course_state"))
                courseStudent.setState(Course.StudentCourseState.valueOf(rs.getString("course_state")));
        }catch(SQLException e){
            e.printStackTrace();
        }
        return courseStudent;
    }

}
