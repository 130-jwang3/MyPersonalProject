package com.personal.project.dao.impl.mysql;

public class CourseStudentSQLDef {
    public static final String CS_FIND_BY_CID = " SELECT * FROM course_student_map where course_oid=? ";
    public static final String ADD_COURSE_STUDENT = " insert into course_student_map(student_oid,course_oid,course_state,course_role,course_grade) values (?,?,?,?,?) ";
    public static final String CS_UPDATE = "update course_student_map set course_state=?, course_role=?, course_grade=? where course_oid=? and student_oid=? ";
    public static final String CS_DELETE = "delete from course_student_map where course_oid=? and student_oid=? ";

}
