package com.personal.project.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class Course {

    public static class CourseStudent {
       private Long studentId;
       private StudentCourseState state;
       private Double score=0.0;

        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public StudentCourseState getState() {
            return state;
        }

        public void setState(StudentCourseState state) {
            this.state = state;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CourseStudent that = (CourseStudent) o;
            return Objects.equals(studentId, that.studentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId);
        }

        public CourseStudent() {
        }

        public CourseStudent(Long studentId, StudentCourseState state) {
            this.studentId = studentId;
            this.state = state;
        }
    }

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
    private String assignedInstructor;
    //the key of the map is distinct by nature
    private Set<CourseStudent> studentEnrolled = new HashSet<>();
    private Set<Long> teacherAssists = new HashSet<Long>();
    private Queue<CourseStudent> waitList=new PriorityQueue<>();
    private List<CourseStudent> finishedStudent=new ArrayList<>();
    private String courseID;
    private int capacity;
    private double credit;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignedInstructor() {
        return assignedInstructor;
    }

    public void setAssignedInstructor(String assignedInstructor) {
        this.assignedInstructor = assignedInstructor;
    }

    public Set<CourseStudent> getStudentEnrolled() {
        return studentEnrolled;
    }

    public void setStudentEnrolled(Set<CourseStudent> studentEnrolled) {
        this.studentEnrolled = studentEnrolled;
    }

    public Set<Long> getTeacherAssists() {
        return teacherAssists;
    }

    public void setTeacherAssists(Set<Long> teacherAssists) {
        this.teacherAssists = teacherAssists;
    }
    @JsonIgnore
    public int getTotalStudent() {
        int size=(int)studentEnrolled.stream().filter(courseStudent -> courseStudent.getState()!=StudentCourseState.DROPPED).count();
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Queue<CourseStudent> getWaitList() {
        return waitList;
    }

    public void setWaitList(Queue<CourseStudent> waitList) {
        this.waitList = waitList;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
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

    public Course(String name, int capacity, double credit) {
        this.name = name;
        this.assignedInstructor="";
        this.courseID = UUID.randomUUID().toString();
        this.capacity=capacity;
        this.credit=credit;
    }

}
