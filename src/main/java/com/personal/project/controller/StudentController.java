package com.personal.project.controller;

import com.personal.project.dto.*;
import com.personal.project.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    CollegeHRService studentService;

    @PostMapping
    public ResponseEntity addStudent(@RequestBody Student student) {
        if (student.getStudentNumber()==null || student.getFirstName()==null || student.getLastName()==null)
          return new ResponseEntity<>("Mandatory fields missing", HttpStatus.BAD_REQUEST);
        int rtn = studentService.addStudent(student);
        if (rtn==0)
            return ResponseEntity.ok(studentService.findByStudentID(student.getStudentNumber()));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudentById(@PathVariable("id") Long id) {
        int rtn=studentService.deleteById(id,"Student");
        if (rtn==0)
            return ResponseEntity.ok(studentService.findByStudentID(id));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{id}/course")
    public ResponseEntity deleteStudentWithAllCourses(@PathVariable("id") Long id) {
        if (studentService.findByStudentID(id)==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            studentService.deleteStudentsWithAllInvolvedCourses(id);
            return new ResponseEntity<>("Delete Successfully!", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public ResponseEntity getStudentByName(@RequestParam(value = "name", required = false) String name) {
        List<Student> students;
        if (name!=null)
            students =studentService.findAllStudentByName(name);
        else
            students=studentService.findAllStudent();
        if (students!=null) {
            return ResponseEntity.ok(students);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity updateStudent(@RequestBody Student student){
        if (student.getStudentOID()==null||studentService.findByStudentOID(student.getStudentOID())==null)
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            studentService.updateByStudentID(student);
            return ResponseEntity.ok(studentService.findByStudentOID(student.getStudentOID()));
        }
    }

    @GetMapping("/{id}/grade")
    public ResponseEntity getStudentGradeById(@PathVariable("id") Long id) {
        if (studentService.findByStudentID(id)==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            return ResponseEntity.ok(studentService.getStudentCreditAndCGPA(id));
        }
    }

}
