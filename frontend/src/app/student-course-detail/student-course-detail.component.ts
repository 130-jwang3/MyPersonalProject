import { Component, OnInit } from '@angular/core';
import {StudentService} from "../service/student-service";
import {ActivatedRoute, Router} from "@angular/router";
import {CourseStudent} from "../model/course-student";
import {GeneralService} from "../service/general-service";
import {Grades} from "../model/grades";

@Component({
  selector: 'app-student-course-detail',
  templateUrl: './student-course-detail.component.html',
  styleUrls: ['./student-course-detail.component.css']
})
export class StudentCourseDetailComponent implements OnInit {
  students:CourseStudent[];
  name:any;
  credit:any;
  cGPA:any;
  constructor(private studentService:StudentService,
              private generalService:GeneralService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.getStudentDetail(this.route.snapshot.paramMap.get('id'));
  }

  getStudentDetail(id:string|null):void{
    this.studentService.detail(id)
      .subscribe(
        (students:any[])=>{
          this.students=students
        },
        (error:any)=>{
          console.log(error);
        }
      )
    this.generalService.getGrade(id)
      .subscribe(
        (grades:Grades)=>{
          this.credit=Object.values(grades)[1];
          this.cGPA=Object.values(grades)[0];
        }
      ),
      (error:any)=>{
        console.log(error);
      }

  }

  stopAction():void{
    this.router.navigate(['/students']);
  }
}
