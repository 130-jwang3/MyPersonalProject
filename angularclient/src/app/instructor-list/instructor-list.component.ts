import { Component, OnInit } from '@angular/core';
import {Instructor} from "../model/instructor";
import {InstructorService} from "../service/instructor-service";
import { Observable,Subject } from "rxjs";

import {FormControl,FormGroup,Validators} from '@angular/forms';


@Component({
  selector: 'app-instructor-list',
  templateUrl: './instructor-list.component.html',
  styleUrls: ['./instructor-list.component.css']
})
export class InstructorListComponent implements OnInit {

  instructors:any;
  instructor:any;
  index=-1;
  searchName='';


  constructor(private instructorService:InstructorService) { }

  ngOnInit(){
    this.getAllInstructor();
  }
  getAllInstructor():void{
    this.instructorService.findAll().subscribe(
      data=>{
        this.instructors=data;
      },(error:any)=>{
        console.log(error);
      }
    );
  }
  deleteInstructor(id:number){
    this.instructorService.delete(id).subscribe(
      responses=>{
        this.getAllInstructor();
      },
      error => {
        console.log(error);
      }
    );
  }
  searchByName():void{
    this.instructorService.findByName(this.searchName)
      .subscribe(
        instructors=>{
          this.instructors=instructors;
        },
        error => {
          console.log(error);
        }
      );
  }

}
