import {Component, OnInit} from '@angular/core';
import {InstructorService} from "../service/instructor-service";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'app-instructor-list',
  templateUrl: './instructor-list.component.html',
  styleUrls: ['./instructor-list.component.css']
})
export class InstructorListComponent implements OnInit {

  instructors: any;
  instructor: any;
  index = -1;
  searchName = '';

  constructor(
    private instructorService: InstructorService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
    this.instructorService.findAll().subscribe(
      data => {
        this.instructors = data;
      }, (error: any) => {
        console.log(error);
      }
    );
  }

  deleteInstructor(id: number) {
    this.instructorService.delete(id).subscribe(
      response => {
        console.log(response);
        this.instructorService.findAll();
      }, (error: any) => {
        console.log(error);
      }
    );
  }

  searchByName(searchName: any): void {
    this.instructorService.findByName(searchName)
      .subscribe(
        instructors => {
          this.instructors = instructors;
        },
        error => {
          console.log(error);
        }
      );
  }

}
