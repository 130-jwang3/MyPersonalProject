package com.personal.project.dao.impl;

import com.personal.project.dao.*;
import com.personal.project.dto.*;

import java.sql.*;
import java.util.*;

public class StudentMysqlDAOImpl implements StudentDAO {
    String url="jdbc:mysql://20.213.20.65:3306/school";
    String uname="root";
    String pass="aD801108";

    @Override
    public void addStudent(Student student) throws Exception {

        String query="insert into person(last_name,first_name,gender,phone,birthday) values (?,?,?,?,?)";
        String query2="insert into student(student_id,type,major,person_oid) values(?,?,?,?)";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection(url,uname,pass);
        con.setAutoCommit(false);
        Savepoint save1 = con.setSavepoint();
        PreparedStatement st=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        PreparedStatement st1=con.prepareStatement(query2,Statement.RETURN_GENERATED_KEYS);
        st.setString(1,student.getLastName());
        st.setString(2,student.getFirstName());
        st.setString(3,student.getGender());
        st.setString(4,student.getPhoneNumber()+"");
        st.setDate(5,null);
        int count=st.executeUpdate();
        ResultSet rs = st.getGeneratedKeys();
        st1.setString(1,student.getStudentNumber()+"");
        st1.setString(2,student.getType()+"");
        st1.setString(3,student.getMajor()+"");
        if(rs.next()){
            st1.setLong(4,rs.getLong(1));
        }
        count+=st1.executeUpdate();
        if(count==0){
            con.rollback(save1);
            System.out.println("student list has been rolled back");
        }
        System.out.println(count+"row/s affected");

        st.close();
        st1.close();
        con.commit();
        con.setAutoCommit(true);
        con.close();
    }

    @Override
    public Student findById(Long studentId) throws Exception {
        Student student=new Student();
        String query="SELECT * FROM student join person on person_oid=person.oid where student_id='"+(studentId+"")+"' group by student_id;";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection(url,uname,pass);

        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        rs.next();
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setMajor(Student.Major.valueOf(rs.getString("major")));
        student.setStudentNumber(Long.parseLong(rs.getString("student_id")));
        student.setPhoneNumber(rs.getString("phone"));
        student.setRole("Student");
        student.setType(Student.Type.valueOf(rs.getString("type")));
        student.setGender(rs.getString("gender"));
        st.close();
        con.close();
        return student;

    }

    @Override
    public List<Student> findAll() throws Exception {
        List<Student> students=new ArrayList<>();
        String query="SELECT * FROM student join person on person_oid=person.oid ;";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection(url,uname,pass);

        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        while(rs.next()) {
            Student student=new Student();
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setMajor(Student.Major.valueOf(rs.getString("major")));
            student.setStudentNumber(Long.parseLong(rs.getString("student_id")));
            student.setPhoneNumber(rs.getString("phone"));
            student.setRole("Student");
            student.setType(Student.Type.valueOf(rs.getString("type")));
            student.setGender(rs.getString("gender"));
            students.add(student);
        }
        st.close();
        con.close();
        return students;
    }

    @Override
    public List<Student> findByName(String name) throws Exception {

        List<Student> students=new ArrayList<>();
        String query="SELECT * FROM student join person on person_oid=person.oid join (select student_oid,avg(course_grade) num_grade from course_student_map group by student_oid) csmap on student_oid=student.oid where first_name like'%"+name+"%' or last_name like '%"+name+"%' group by student_id;";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection(url,uname,pass);

        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        while(rs.next()) {
            Student student=new Student();
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setMajor(Student.Major.valueOf(rs.getString("major")));
            student.setStudentNumber(Long.parseLong(rs.getString("student_id")));
            student.setPhoneNumber(rs.getString("phone"));
            student.setRole("Student");
            student.setType(Student.Type.valueOf(rs.getString("type")));
            student.setGender(rs.getString("gender"));
            student.setNumGrade((int) rs.getDouble("num_grade"));
            students.add(student);
        }
        st.close();
        con.close();
        return students;
    }

    @Override
    public void deleteById(Long studentId) throws Exception {
        String query="delete from student where student_id=(?);";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection(url,uname,pass);

        PreparedStatement st=con.prepareStatement(query);
        st.setString(1,studentId+"");
        int count=st.executeUpdate();

        System.out.println(count+"row/s affected");

        st.close();
        con.close();
    }

    @Override
    public Student update(Student student) throws Exception {
        String query="update student,person set last_name=?,first_name=?, phone=?,major=? where person_oid=person.oid and student_id=?;";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection(url,uname,pass);

        PreparedStatement st=con.prepareStatement(query);
        st.setString(1,student.getLastName());
        st.setString(2,student.getFirstName());
        st.setString(3,student.getPhoneNumber()+"");
        st.setString(4,student.getMajor()+"");
        st.setString(5,student.getStudentNumber()+"");
        int count=st.executeUpdate();

        System.out.println(count+"row/s affected");

        st.close();
        con.close();
        return null;
    }
}
