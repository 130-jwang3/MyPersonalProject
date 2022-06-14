package com.personal.project.controller;

import com.personal.project.dto.*;
import com.personal.project.service.*;
import org.checkerframework.checker.units.qual.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {
    @Autowired
    CollegeHRService courseService;

    @PostMapping
    public ResponseEntity addCourse(@RequestBody Course course) {
        if (course.getCredit()==null || course.getCourseID()==null || course.getCapacity()==null)
            return new ResponseEntity<>("Mandatory fields missing", HttpStatus.BAD_REQUEST);
        int rtn = courseService.addCourse(course);
        if (rtn==0)
            return ResponseEntity.ok(courseService.findByCourseName(course.getName()));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/course-student")
    public ResponseEntity deleteCourseStudentById(@RequestParam("name") String name,@RequestParam("id") long id) {
        int rtn= courseService.deleteCourseStudentByName(name,id);
        if (rtn==0)
            return ResponseEntity.ok(courseService.findByCourseName(name));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourseById(@PathVariable("id") Long id) {
        int rtn= courseService.deleteCourseById(id);
        if (rtn==0)
            return ResponseEntity.ok("successful deleted");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping
    public ResponseEntity getCourseByName(@RequestParam("name") String name) {
        Course courses= courseService.findByCourseName(name);
        if (courses!=null) {
            return ResponseEntity.ok(courses);
        }else{
            return null;
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity getCourseByID(@PathVariable("id") Long id) {
        Course course= courseService.findCourseName(id);
        if (course!=null) {
            return ResponseEntity.ok(course);
        }else{
            return null;
        }

    }

    @GetMapping("/all")
    public ResponseEntity getAllCourses(){
        List<Course> courses= courseService.findAllCourses();
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCourse(@PathVariable("id") Long id,@RequestBody Course course){
        if (courseService.findCourseName(id)==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            courseService.updateCourse(course);
            return ResponseEntity.ok(courseService.findByCourseName(course.getName()));
        }
    }

    @PostMapping("/course-student/{id}")
    public ResponseEntity<CourseStudent> addCourseStudent(@PathVariable("id") Long id,@RequestParam("name") String name) {
        Long oid=courseService.findByStudentID(id).getStudentOID();
        String course_id=courseService.findByCourseName(name).getCourseID();
        int rtn = courseService.enrollStudent(oid,course_id);
        if (rtn==0)
            return ResponseEntity.ok(courseService.findCourseStudentByID(oid,courseService.findByCourseName(name)));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/course-student")
    public ResponseEntity getCourseStudentByID(@RequestParam("id") Long id,@RequestParam("name") String courseName){
        CourseStudent courseStudent=courseService.findCourseStudentByID(courseService.findByStudentID(id).getStudentOID(),courseService.findByCourseName(courseName));
        if (courseStudent!=null) {
            return ResponseEntity.ok(courseStudent);
        }else{
            return null;
        }
    }

    @PostMapping("/course-student/ta")
    public ResponseEntity<CourseStudent> addTA(@RequestParam("name") String name,@RequestParam("id") Long id) {
        Long student_oid=courseService.findByStudentID(id).getStudentOID();
        Long course_oid=courseService.findByCourseName(name).getCourseOID();
        CourseStudent courseStudent=new CourseStudent(student_oid,course_oid, Course.StudentCourseState.ENROLLED,-1.0,"TA");
        int rtn = courseService.addTA(courseStudent);
        if (rtn==0)
            return ResponseEntity.ok(courseService.findCourseStudentByID(student_oid,courseService.findByCourseName(name)));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/course-student")
    public ResponseEntity courseStudentDropCourse(@RequestParam("id") Long id,@RequestBody Course course){
        Long oid=courseService.findByStudentID(id).getStudentOID();
        System.out.println("here");
        if (courseService.findCourseStudentByID(oid,course)==null||oid==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            courseService.dropCourse(course,oid);
            return ResponseEntity.ok(courseService.findCourseStudentByID(oid,course));
        }
    }

    @PutMapping("/course-student/{id}")
    public ResponseEntity updateGrade(@PathVariable("id")Long id,@RequestParam("grade") Double grade,@RequestBody Course course){
        Long oid=courseService.findByStudentID(id).getStudentOID();
        if (courseService.findCourseStudentByID(oid,course)==null||oid==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            courseService.assignGrade(course,oid,grade);
            return ResponseEntity.ok(courseService.findCourseStudentByID(oid,course));
        }
    }
}
