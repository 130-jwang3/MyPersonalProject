package com.personal.project.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class Course {

    public static class SortByGrade implements Comparator<CourseStudent>{
        public int compare(CourseStudent a, CourseStudent b){
            return  Double.compare(a.getScore(),b.getScore());
        }
    }

    public static enum StudentCourseState {
        ENROLLED,
        DROPPED,
        WAITLISTED,
        FINISHED,
        FAILED
    }

    private String name;
    private long assignedInstructor;
    //the key of the map is distinct by nature
    private List<CourseStudent> studentEnrolled = new ArrayList<>();
    private List<Long> teacherAssists = new ArrayList<>();
    private Queue<CourseStudent> waitList=new PriorityQueue<>();
    private List<CourseStudent> finishedStudent=new ArrayList<>();
    private String courseID;
    private long courseOID;
    private int capacity;
    private double credit;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Long getCourseOID() {
        return courseOID;
    }

    public void setCourseOID(Long courseOID) {
        this.courseOID = courseOID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAssignedInstructor() {
        return assignedInstructor;
    }

    public void setAssignedInstructor(Long assignedInstructor) {
        this.assignedInstructor = assignedInstructor;
    }

    public List<CourseStudent> getStudentEnrolled() {
        return studentEnrolled;
    }

    public void setStudentEnrolled(List<CourseStudent> studentEnrolled) {
        this.studentEnrolled = studentEnrolled;
    }

    public List<Long> getTeacherAssists() {
        return teacherAssists;
    }

    public void setTeacherAssists(List<Long> teacherAssists) {
        this.teacherAssists = teacherAssists;
    }
    @JsonIgnore
    public int getTotalStudent() {
        int size=(int)studentEnrolled.stream().filter(courseStudent -> courseStudent.getState()==StudentCourseState.ENROLLED).count();
        return size;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Queue<CourseStudent> getWaitList() {
        return waitList;
    }

    public void setWaitList(Queue<CourseStudent> waitList) {
        this.waitList = waitList;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public List<CourseStudent> getFinishedStudent() {
        return finishedStudent;
    }

    public void setFinishedStudent(List<CourseStudent> finishedStudent) {
        this.finishedStudent = finishedStudent;
    }

    public Course(){

    }

    public Course(String name, int capacity, double credit, Long instructor_oid) {
        this.name = name;
        this.assignedInstructor=instructor_oid;
        this.courseID = UUID.randomUUID().toString();
        this.capacity=capacity;
        this.credit=credit;
    }

}
