package com.personal.project.dao.impl.mysql;

public class CourseSQLDef {
    public static final String FIND_BY_ID = " select oid,course_id ,`name`, `capacity`, `credit`,`instructor_oid` from course where course_id = ? ";
    public static final String ADD_COURSE=" insert into course(course_id,name,capacity,credit,instructor_oid) values (?,?,?,?,?) ";
    public static final String FIND_OID = " select oid from course where course_id = ? ";
    public static final String FIND_ALL = " select * from course ";
    public static final String FIND_ID_BY_NAME = " select * from course where `name`=? ";
    public static final String FIND_COURSE_BY_OID=" select * from course where `oid`=? ";
    public static final String DELETE_BY_ID = " delete from course where `oid`=(?) ";
    public static final String DELETE_STU_BY_COURSE=" delete from course_student_map where student_oid=(?) and course_oid=(?) ";
    public static final String UPDATE_COURSE=" update course set instructor_oid=? where `oid`=? ";
}
