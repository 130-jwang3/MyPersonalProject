import { Component, OnInit } from '@angular/core';
import {InstructorService} from "../service/instructor-service";
import {ActivatedRoute, Router} from "@angular/router";
import {StudentService} from "../service/student-service";

@Component({
  selector: 'app-student-detail',
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.css']
})
export class StudentDetailComponent implements OnInit {
  currentStudent:any;
  message="";
  constructor(
    private studentService:StudentService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.message='';
    this.getStudent(this.route.snapshot.paramMap.get('id'));
  }
  getStudent(id:string|null):void{
    this.studentService.findByID(id)
      .subscribe(
        (student:null)=> {
          this.currentStudent = student;
          console.log(student);
        },
        (error:any)=>{
          console.log(error);
        }
      );
  }
  updateStudent():void{
    this.studentService.update(this.currentStudent.studentNumber,this.currentStudent)
      .subscribe(
        response=>{
          console.log(response);
          this.message="Instructor was updated";
          this.router.navigate(['/students']);
        },
        error => {
          console.log(error);
        }
      );
  }
  stopAction():void{
    this.router.navigate(['/students']);
  }

}
