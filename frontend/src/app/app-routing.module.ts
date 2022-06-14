import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {InstructorListComponent} from "./instructor-list/instructor-list.component";
import {InstructorFormComponent} from "./instructor-form/instructor-form.component";
import {InstructorDetailComponent} from "./instructor-detail/instructor-detail.component";
import {StudentListComponent} from "./student-list/student-list.component";
import {StudentDetailComponent} from "./student-detail/student-detail.component";
import {StudentFormComponent} from "./student-form/student-form.component";
import {StudentCourseDetailComponent} from "./student-course-detail/student-course-detail.component";
import {CourseListComponent} from "./course-list/course-list.component";
import {CourseFormComponent} from "./course-form/course-form.component";
import {CourseDetailComponent} from "./course-detail/course-detail.component";
import {CourseStudentDetailComponent} from "./course-student-detail/course-student-detail.component";

const routes: Routes = [
  { path: '', redirectTo: 'instructors', pathMatch: 'full' },
  {path:'instructors',component:InstructorListComponent},
  {path:'instructors/:id',component:InstructorDetailComponent},
  {path:'addinstructor',component:InstructorFormComponent},
  {path:'students',component:StudentListComponent},
  {path:'students/:id',component:StudentDetailComponent},
  {path:'addstudent',component:StudentFormComponent},
  {path:'studentdetail/:id',component:StudentCourseDetailComponent},
  {path:'courses',component:CourseListComponent},
  {path:'addcourse',component:CourseFormComponent},
  {path:'courses/:id',component:CourseDetailComponent},
  {path:'coursedetail/:id',component:CourseStudentDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
