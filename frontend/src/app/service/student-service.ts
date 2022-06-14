import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {Instructor} from "../model/instructor";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  studentUrl:string='http://localhost:8080/api/student';
  headers=new HttpHeaders().set('Content-Type','application/json');

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<any> {
    return this.http.get(this.studentUrl).pipe(
      catchError(this.handleError)
    );
  }

  public save(student: any):Observable<any>{
    return this.http.post<Instructor>(this.studentUrl, student).pipe(
      catchError(this.handleError)
    );
  }

  public delete(id:any):Observable<any>{
    return this.http.delete(`${this.studentUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  public findByName(name:any):Observable<any>{
    return this.http.get(`${this.studentUrl}?name=${name}`).pipe(
      catchError(this.handleError)
    );
  }

  public findByID(id:any):Observable<any>{
    return this.http.get(`${this.studentUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  public update(id:any,value:any):Observable<any>{
    return this.http.put(`${this.studentUrl}/${id}`,value).pipe(
      catchError(this.handleError)
    );
  }

  public detail(id:any):Observable<any>{
    return this.http.get(`${this.studentUrl}/detail/${id}`).pipe(
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
