import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { Instructor } from '../model/instructor';
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn:'root'
})
export class InstructorService {

  instructorUrl:string='http://localhost:8080/api/instructor';
  headers=new HttpHeaders().set('Content-Type','application/json');

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<any> {
    return this.http.get(this.instructorUrl).pipe(
      catchError(this.handleError)
    );
  }

  public save(instructor: any):Observable<any>{
    return this.http.post<Instructor>(this.instructorUrl, instructor).pipe(
      catchError(this.handleError)
    );
  }

  public delete(id:any):Observable<any>{
    return this.http.delete(`${this.instructorUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  public findByName(name:any):Observable<any>{
    return this.http.get(`${this.instructorUrl}?name=${name}`).pipe(
      catchError(this.handleError)
    );
  }

  public findByID(id:any):Observable<any>{
    return this.http.get(`${this.instructorUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  public update(id:any,value:any):Observable<any>{
    return this.http.put(`${this.instructorUrl}/${id}`,value).pipe(
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
