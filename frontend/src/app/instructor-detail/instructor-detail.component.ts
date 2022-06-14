import { Component, OnInit } from '@angular/core';
import {InstructorService} from "../service/instructor-service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-instructor-detail',
  templateUrl: './instructor-detail.component.html',
  styleUrls: ['./instructor-detail.component.css']
})
export class InstructorDetailComponent implements OnInit {
  currentInstructor:any;
  message="";
  constructor(
    private instructorService:InstructorService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.message='';
    this.getInstructor(this.route.snapshot.paramMap.get('id'));
  }
  getInstructor(id:string|null):void{
    this.instructorService.findByID(id)
      .subscribe(
        (instructor:null)=> {
          this.currentInstructor = instructor;
          console.log(instructor);
        },
        (error:any)=>{
          console.log(error);
        }
      );
  }
  updateInstructor():void{
    this.instructorService.update(this.currentInstructor.employeeID,this.currentInstructor)
      .subscribe(
        response=>{
          console.log(response);
          this.message="Instructor was updated";
          this.router.navigate(['/instructors']);
        },
        error => {
          console.log(error);
        }
      );
  }
  stopAction():void{
    this.router.navigate(['/instructors']);
  }
}
