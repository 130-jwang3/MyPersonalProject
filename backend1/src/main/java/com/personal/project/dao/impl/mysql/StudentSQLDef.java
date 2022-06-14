package com.personal.project.dao.impl.mysql;

public class StudentSQLDef {
    public static final String STU_FIND_BY_ID = " SELECT * FROM student s join person p on s.person_oid=p.oid where s.student_id= ? group by s.student_id ";
    public static final String STU_FIND_BY_OID = " SELECT * FROM student s join person p on s.person_oid=p.oid where s.oid= ? group by s.oid ";
    public static final String STU_FIND_ALL = " SELECT * FROM student join person on person_oid=person.oid ";
    public static final String STU_FIND_BY_NAME = " SELECT * FROM student join person on person_oid=person.oid where first_name like ? or last_name like ? ";
    public static final String STU_DELETE_BY_ID = " delete from student where student_id=(?) ";
    public static final String STU_UPDATE = " update student,person set last_name=?,first_name=?, phone=?,major=?,type=? where person_oid=person.oid and student_id=? ";
    public static final String STU_ADD_STUDENT_1 = " insert into person(last_name,first_name,gender,phone) values (?,?,?,?) ";
    public static final String STU_ADD_STUDENT_2 = " insert into student(student_id,type,major,person_oid) values(?,?,?,?) ";

}
