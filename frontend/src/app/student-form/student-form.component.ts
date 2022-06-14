import { Component, OnInit } from '@angular/core';
import {Instructor} from "../model/instructor";
import {ActivatedRoute, Router} from "@angular/router";
import {InstructorService} from "../service/instructor-service";
import {Student} from "../model/student";
import {StudentService} from "../service/student-service";

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent implements OnInit {

  student: Student;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: StudentService) {
    this.student = new Student();
  }

  onSubmit() {
    this.userService.save(this.student).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/students']);
  }

  ngOnInit(): void {
  }
}
