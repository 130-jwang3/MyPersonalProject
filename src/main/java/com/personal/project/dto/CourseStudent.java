package com.personal.project.dto;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;
import com.personal.project.service.*;
import org.hibernate.annotations.*;


public class CourseStudent {
    private Long oid;

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    private Long studentOid;
    private Long courseOid;
    private Course.StudentCourseState state;
    private Double score = 0.0;
    private String courseRole;
    private Student student;
    private Course course;
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getStudentOid() {
        return studentOid;
    }

    public void setStudentOid(Long studentOid) {
        this.studentOid = studentOid;
    }

    public Long getCourseOid() {
        return courseOid;
    }

    public void setCourseOid(Long courseOid) {
        this.courseOid = courseOid;
    }

    public Course.StudentCourseState getState() {
        return state;
    }

    public void setState(Course.StudentCourseState state) {
        this.state = state;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCourseRole() {
        return courseRole;
    }

    public void setCourseRole(String courseRole) {
        this.courseRole = courseRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseStudent that = (CourseStudent) o;
        return Objects.equals(studentOid, that.studentOid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentOid);
    }

    public CourseStudent() {
    }

    public CourseStudent(Long studentOid, Long courseOid, Course.StudentCourseState state, Double score, String courseRole) {
        this.studentOid = studentOid;
        this.courseOid = courseOid;
        this.state = state;
        this.score = score;
        this.courseRole = courseRole;
    }
}
