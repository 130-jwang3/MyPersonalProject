import { Component, OnInit } from '@angular/core';
import {Instructor} from "../model/instructor";
import {ActivatedRoute, Router} from "@angular/router";
import {InstructorService} from "../service/instructor-service";
import {Course} from "../model/course";
import {CourseService} from "../service/course-service";
import { v4 as uuid } from "uuid";

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.css']
})
export class CourseFormComponent implements OnInit{

  course: Course;
  instructors:any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: CourseService,
    private instructorService:InstructorService) {
    this.course = new Course();
  }
  ngOnInit(): void {
    this.instructorService.findAll().subscribe(
      data => {
        this.instructors = data;
      }, (error: any) => {
        console.log(error);
      }
    );
  }

  onSubmit() {
    this.userService.save(this.course).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/courses']);
  }


}
