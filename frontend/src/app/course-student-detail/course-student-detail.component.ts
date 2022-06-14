import {Component, OnInit} from '@angular/core';
import {CourseStudent} from "../model/course-student";
import {ActivatedRoute, Router} from "@angular/router";
import {CourseService} from "../service/course-service";
import {StudentService} from "../service/student-service";
import {Student} from "../model/student";
import {GeneralService} from "../service/general-service";

@Component({
  selector: 'app-course-student-detail',
  templateUrl: './course-student-detail.component.html',
  styleUrls: ['./course-student-detail.component.css']
})
export class CourseStudentDetailComponent implements OnInit {
  enrolled: CourseStudent[];
  finished: CourseStudent[];
  name: any;

  constructor(private courseService: CourseService,
              private studentService:StudentService,
              private generalService:GeneralService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getCourseDetail(this.route.snapshot.paramMap.get('id'));
  }

  getCourseDetail(id: number | any): void {
    this.courseService.findByID(id)
      .subscribe(
        (course:any)=>{
          this.enrolled=course.studentEnrolled;
          for(let i=0;i<this.enrolled.length;i++){
            this.studentService.findByID(this.enrolled[i].studentOid).subscribe(
              (student:any|null)=>{
                this.enrolled[i].student=student;
              }
            )
          }
          this.finished=course.finishedStudent;
          for(let i=0;i<this.finished.length;i++){
            this.studentService.findByID(this.finished[i].studentOid).subscribe(
              (student:any|null)=>{
                this.finished[i].student=student;
              }
            )
          }
        },
        (error:any)=>{
          console.log(error);
        }
      )
  }
  stopAction():void{
    this.router.navigate(['/courses']);
  }
  dropCourse(id:any){
    this.courseService.findByID(id)
      .subscribe(
        (course:any)=>{
          this.generalService.dropCourse(id,course)
        },
        (error:any)=>{
          console.log(error);
        }
      )
  }


}
