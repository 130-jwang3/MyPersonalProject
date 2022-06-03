import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {InstructorListComponent} from "./instructor-list/instructor-list.component";
import {InstructorFormComponent} from "./instructor-form/instructor-form.component";

const routes: Routes = [
  { path: '', redirectTo: 'view-student', pathMatch: 'full' },
  {path:'instructors',component:InstructorListComponent},
  {path:'addinstructor',component:InstructorFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
