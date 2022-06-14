import { Component, OnInit } from '@angular/core';
import {InstructorService} from "../service/instructor-service";
import {ActivatedRoute, Router} from "@angular/router";
import {CourseService} from "../service/course-service";

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})
export class CourseDetailComponent implements OnInit {
  currentCourse:any;
  instructors:any;
  message="";
  constructor(
    private courseService:CourseService,
    private instructorService:InstructorService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.message='';
    this.getCourse(this.route.snapshot.paramMap.get('id'));
    this.instructorService.findAll().subscribe(
      data => {
        this.instructors = data;
      }, (error: any) => {
        console.log(error);
      }
    );
  }
  getCourse(id:string|null):void{
    this.courseService.findByID(id)
      .subscribe(
        (course:null)=> {
          this.currentCourse = course;
        },
        (error:any)=>{
          console.log(error);
        }
      );
  }
  updateCourse():void{
    this.courseService.update(this.currentCourse.courseOID,this.currentCourse)
      .subscribe(
        response=>{
          console.log(response);
          this.message="Course was updated";
          this.router.navigate(['/courses']);
        },
        error => {
          console.log(error);
        }
      );
  }
  stopAction():void{
    this.router.navigate(['/courses']);
  }
}
