import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InstructorListComponent } from './instructor-list/instructor-list.component';
import { InstructorFormComponent } from './instructor-form/instructor-form.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InstructorService} from "./service/instructor-service";
import {DataTablesModule} from "angular-datatables";
import { InstructorDetailComponent } from './instructor-detail/instructor-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    InstructorListComponent,
    InstructorFormComponent,
    InstructorDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    DataTablesModule
  ],
  providers: [InstructorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
