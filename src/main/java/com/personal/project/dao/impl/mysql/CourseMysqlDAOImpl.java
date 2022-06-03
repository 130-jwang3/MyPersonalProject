package com.personal.project.dao.impl.mysql;

import com.personal.project.dao.*;
import com.personal.project.dto.*;
import com.personal.project.util.*;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class CourseMysqlDAOImpl implements CourseDAO {
    private InstructorDAO instructorDAO= new InstructorMysqlDAOImpl();
    private CourseStudentDAO courseStudentDAO= new CourseStudentMysqlDAOImpl();
    private StudentDAO studentDAO=new StudentMysqlDAOImpl();


    @Override
    public void addCourse(Course course) throws Exception {
        int count=SQLExecutor.runUpdate(CourseSQLDef.ADD_COURSE,List.of(course.getCourseID(),course.getName(),course.getCapacity(),course.getCredit(),course.getAssignedInstructor()));
        System.out.println(count + "row/s affected");
    }

    @Override
    public Long findOid(String courseId) throws Exception {
        return new SQLExecutor<Long>().runQuery(CourseSQLDef.FIND_OID, List.of(courseId), (rs -> {
            Long oid=0l;
            while (rs.next()) {
                oid  = getCourseFromResultSet(rs).getCourseOID();
            }
            return oid;
        }));
    }

    @Override
    public Course findById(String courseId) throws Exception {

        return new SQLExecutor<Course>().runQuery(CourseSQLDef.FIND_BY_ID, List.of(courseId), (rs -> {
            Course course=new Course();
            while (rs.next()) {
                course = getCourseFromResultSet(rs);
                break;
            }
            Long courseOid=findOid(courseId);
            List<CourseStudent> students = courseStudentDAO.findByCourseOid(courseOid);
            course.setTeacherAssists(students.stream().filter(courseStudent -> courseStudent.getCourseRole().equalsIgnoreCase("TA")).map(cs->cs.getStudentOid()).collect(Collectors.toList()));
            course.setStudentEnrolled(students.stream().filter(courseStudent -> courseStudent.getCourseRole().equalsIgnoreCase("student")&
                    courseStudent.getState().equals(Course.StudentCourseState.ENROLLED)).collect(Collectors.toList()));
            course.setFinishedStudent(students.stream().filter(courseStudent -> courseStudent.getCourseRole().equalsIgnoreCase("student")&
                    courseStudent.getState().equals(Course.StudentCourseState.FINISHED)).collect(Collectors.toList()));
            course.setWaitList(students.stream().filter(courseStudent -> courseStudent.getCourseRole().equalsIgnoreCase("student")&
                    courseStudent.getState().equals(Course.StudentCourseState.WAITLISTED)).collect(Collectors.toCollection(PriorityQueue::new)));
            return course;
        }));
    }

    @Override
    public List<Course> findAll() throws Exception {
        return new SQLExecutor<List<Course>>().runQuery(CourseSQLDef.FIND_ALL, null, (rs -> {
            List<Course> courses = new ArrayList<>();
            while (rs.next()) {
                String courseId=getCourseFromResultSet(rs).getCourseID();
                Course course=findById(courseId);
                courses.add(course);
            }
            return courses;
        }));
    }

    @Override
    public Course findByName(String name) throws Exception {
        return new SQLExecutor<Course>().runQuery(CourseSQLDef.FIND_ID_BY_NAME, List.of(name), (rs -> {
            Course course=null;
            while (rs.next()) {
                course = getCourseFromResultSet(rs);
                String courseId=course.getCourseID();
                course=findById(courseId);
            }
            return course;
        }));
    }

    @Override
    public void deleteById(String courseID) throws Exception {
        int count=SQLExecutor.runUpdate(CourseSQLDef.DELETE_BY_ID,List.of(courseID));
        System.out.println(count + "row/s affected");
    }

    @Override
    public void deleteStudentByName(String courseName, Long student_id) throws Exception {
        Long student_oid=studentDAO.findById(student_id).getStudentOID();
        Long course_oid=findByName(courseName).getCourseOID();
        int count=SQLExecutor.runUpdate(CourseSQLDef.DELETE_STU_BY_COURSE,List.of(student_oid,course_oid));
        System.out.println(count + "row/s affected");
    }

    @Override
    public Course update(Course course) throws Exception {
        int count=SQLExecutor.runUpdate(CourseSQLDef.UPDATE_COURSE,List.of(course.getAssignedInstructor()));
        System.out.println(count + "row/s affected");
        Course course1=findById(course.getCourseID());
        return course1;
    }

    private Course getCourseFromResultSet(ResultSet rs){
        Course course=new Course();
        try{
            if (SQLExecutor.hasColumn(rs, "oid"))
                course.setCourseOID(rs.getLong("oid"));
            if (SQLExecutor.hasColumn(rs, "course_id"))
                course.setCourseID(rs.getString("course_id"));
            if (SQLExecutor.hasColumn(rs, "name"))
                course.setName(rs.getString("name"));
            if (SQLExecutor.hasColumn(rs, "capacity"))
                course.setCapacity(rs.getInt("capacity"));
            if (SQLExecutor.hasColumn(rs, "credit"))
                course.setCredit(rs.getDouble("credit"));
            if (SQLExecutor.hasColumn(rs, "instructor_oid"))
                course.setAssignedInstructor(rs.getLong("instructor_oid"));
        }catch(SQLException e){
            e.printStackTrace();
        }
        return course;
    }
}
