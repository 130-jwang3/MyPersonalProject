package com.personal.project.controller;

import com.personal.project.dto.*;
import com.personal.project.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/instructor")
@CrossOrigin(origins = "http://localhost:4200")
public class InstructorController {
    @Autowired
    CollegeHRService instructorService;

    @PostMapping
    public ResponseEntity addInstructor(@RequestBody Instructor instructor) {
        if (instructor.getEmployeeID()==null || instructor.getFirstName()==null || instructor.getLastName()==null)
            return new ResponseEntity<>("Mandatory fields missing", HttpStatus.BAD_REQUEST);
        int rtn = instructorService.addInstructor(instructor);
        if (rtn==0)
            return ResponseEntity.ok(instructorService.findByStudentID(instructor.getEmployeeID()));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInstructorById(@PathVariable("id") Long id) {
        int rtn= instructorService.deleteById(id,"Instructor");
        if (rtn==0)
            return ResponseEntity.ok("successful deleted");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping
    public ResponseEntity getInstructorByName(@RequestParam(value = "name", required = false) String name) {
        List<Instructor> instructors;
        if (name!=null)
            instructors =instructorService.findAllInstructorByName(name);
        else
            instructors=instructorService.findAllInstructor();
        if (instructors!=null) {
            return ResponseEntity.ok(instructors);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getInstructorByID(@PathVariable("id")Long id){
        Instructor instructor=instructorService.findByEmployeeID(id);
        if (instructor!=null) {
            return ResponseEntity.ok(instructor);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateInstructor(@PathVariable("id") Long id,@RequestBody Instructor instructor){
        if (instructor.getInstructor_oid()==null||instructorService.findByEmployeeID(id)==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            instructorService.updateByEmployeeID(instructor);
            return ResponseEntity.ok(instructorService.findByEmployeeID(instructor.getInstructor_oid()));
        }
    }
}
