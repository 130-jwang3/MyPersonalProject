import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CourseService} from "../service/course-service";
import {InstructorService} from "../service/instructor-service";

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {

  courses: any;
  course: any;
  index = -1;
  searchName = '';

  constructor(
    private courseService: CourseService,
    private instructorService: InstructorService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
    this.courseService.findAll().subscribe(
      data => {
        this.courses = data;
        //for(let i=0;i<this.courses.length;i++){
        //}
      }, (error: any) => {
        console.log(error);
      }
    );
  }

  deleteCourse(id: number) {
    this.courseService.delete(id).subscribe(
      response => {
        console.log(response);
        this.router.navigate(['/courses']);
      }, (error: any) => {
        console.log(error);
      }
    );
  }

  searchByName(searchName: any): void {
    this.courseService.findByName(searchName)
      .subscribe(
        instructors => {
          this.courses = instructors;
        },
        error => {
          console.log(error);
        }
      );
  }
}
