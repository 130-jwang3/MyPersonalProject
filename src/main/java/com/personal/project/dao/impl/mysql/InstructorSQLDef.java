package com.personal.project.dao.impl.mysql;

public class InstructorSQLDef {
    public static final String INS_FIND_BY_ID = "SELECT * FROM instructor i join person p on i.person_oid=p.oid where i.employee_id= ? group by i.employee_id";
    public static final String INS_FIND_BY_OID = " SELECT * FROM instructor i join person p on i.person_oid=p.oid where i.oid= ? group by i.oid ";
    public static final String INS_FIND_ALL = "SELECT * FROM instructor i join person p on i.person_oid=p.oid ;";
    public static final String INS_FIND_BY_NAME = "SELECT * FROM instructor join person on person_oid=person.oid where first_name like ? or last_name like ?;";
    public static final String INS_DELETE_BY_ID = "delete from instructor where employee_id=(?);";
    public static final String INS_UPDATE = "update instructor,person set last_name=?,first_name=?, phone=? where person_oid=person.oid and employee_id=?;";
    public static final String INS_ADD_PERSON = "insert into person(last_name,first_name,gender,phone) values (?,?,?,?)";
    public static final String INS_ADD_INSTRUCTOR = "insert into instructor(employee_id,title,person_oid) values(?,?,?)";
    public static final String INS_BY_COURSE_ID = "SELECT * FROM instructor where oid = ( select instructor_oid from course c where c.oid = ?)";

}
