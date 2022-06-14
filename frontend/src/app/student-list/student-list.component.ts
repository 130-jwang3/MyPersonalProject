import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {StudentService} from "../service/student-service";
import {GeneralService} from "../service/general-service";
import {CourseService} from "../service/course-service";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {

  students: any;
  student: any;
  index = -1;
  searchName = '';
  addParameter:boolean;
  courses:any[];
  course:any;

  constructor(
    private studentService: StudentService,
    private courseService: CourseService,
    private generalService:GeneralService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
    this.studentService.findAll().subscribe(
      data => {
        this.students = data;
      }, (error: any) => {
        console.log(error);
      }
    );
    this.courseService.findAll().subscribe(
      data=>{
        this.courses=data;
      },(error:any)=>{
        console.log(error);
      }
    )
  }

  toggleForm() {
    this.addParameter = !this.addParameter;
  }

  enrollStudent(id:any){
    this.addParameter = !this.addParameter;
    this.course=(<HTMLSelectElement>document.getElementById('course')).value;
    this.generalService.enrollCourse(id,this.course).subscribe(
      response => {
        console.log(response);
        this.studentService.findAll();
      }, (error: any) => {
        console.log(error);
      }
    );
  }

  deleteStudent(id: number) {
    this.studentService.delete(id).subscribe(
      response => {
        console.log(response);
        this.studentService.findAll();
      }, (error: any) => {
        console.log(error);
      }
    );
  }

  leaveSchool(id:number){
    this.generalService.leaveSchool(id).subscribe(
      response => {
        console.log(response);
        this.studentService.findAll();
      }, (error: any) => {
        console.log(error);
      }
    );
  }

  searchByName(searchName: any): void {
    this.studentService.findByName(searchName)
      .subscribe(
        students => {
          this.students = students;
        },
        error => {
          console.log(error);
        }
      );
  }

}
