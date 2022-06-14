import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InstructorListComponent } from './instructor-list/instructor-list.component';
import { InstructorFormComponent } from './instructor-form/instructor-form.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {InstructorService} from "./service/instructor-service";
import { InstructorDetailComponent } from './instructor-detail/instructor-detail.component';
import {DataTablesModule} from "angular-datatables";
import { StudentListComponent } from './student-list/student-list.component';
import { StudentFormComponent } from './student-form/student-form.component';
import { StudentDetailComponent } from './student-detail/student-detail.component'
import { StudentCourseDetailComponent } from './student-course-detail/student-course-detail.component';
import { CourseListComponent } from './course-list/course-list.component';
import { CourseFormComponent } from './course-form/course-form.component';
import { CourseDetailComponent } from './course-detail/course-detail.component';
import { CourseStudentDetailComponent } from './course-student-detail/course-student-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    InstructorListComponent,
    InstructorFormComponent,
    InstructorDetailComponent,
    StudentListComponent,
    StudentFormComponent,
    StudentDetailComponent,
    StudentCourseDetailComponent,
    CourseListComponent,
    CourseFormComponent,
    CourseDetailComponent,
    CourseStudentDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    DataTablesModule
  ],
  providers: [InstructorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
