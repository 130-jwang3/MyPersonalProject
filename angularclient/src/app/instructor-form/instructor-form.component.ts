import {Component} from '@angular/core';
import {Instructor} from "../model/instructor";
import {ActivatedRoute, Router} from "@angular/router";
import {InstructorService} from "../service/instructor-service";

@Component({
  selector: 'app-instructor-form',
  templateUrl: './instructor-form.component.html',
  styleUrls: ['./instructor-form.component.css']
})
export class InstructorFormComponent{

  instructor: Instructor;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: InstructorService) {
    this.instructor = new Instructor();
  }

  onSubmit() {
    this.userService.save(this.instructor).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/instructor']);
  }

}

