import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {Grades} from "../model/grades";

@Injectable({
  providedIn: 'root'
})
export class GeneralService {
  courseUrl:string='http://localhost:8080/api/course';
  instructorUrl:string='http://localhost:8080/api/instructor';
  studentUrl:string='http://localhost:8080/api/student';
  headers=new HttpHeaders().set('Content-Type','application/json');

  constructor(private http:HttpClient) { }

  public getGrade(id:any): Observable<any> {
    return this.http.get(`${this.studentUrl}/${id}/grade`).pipe(
      catchError(this.handleError)
    );
  }

  public leaveSchool(id:any):Observable<any>{
    return this.http.delete(`${this.studentUrl}/${id}/course`).pipe(
      catchError(this.handleError)
    );
  }

  public enrollCourse(id:any,name:any):Observable<any>{
    return this.http.post(`${this.courseUrl}/course-student/${id}`,name).pipe(
      catchError(this.handleError)
    );
  }

  public enrollTA(id:any,name:any):Observable<any>{
    return this.http.post(`${this.courseUrl}/course-student/ta`,name,id).pipe(
      catchError(this.handleError)
    );
  }

  public dropCourse(id:any,course:any):Observable<any>{
    return this.http.put(`${this.courseUrl}/course-student/`,id,course).pipe(
      catchError(this.handleError)
    );
  }

  public assignGrade(id:any,grade:any,course:any):Observable<any>{
    return this.http.post(`${this.courseUrl}/course-student/${id}`,grade,course).pipe(
      catchError(this.handleError)
    );
  }

  public handleError(error: HttpErrorResponse){
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  };
}
