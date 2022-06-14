import {CourseStudent} from "./course-student";

export class Course {
  name:any;
  assignedInstructor:any;
  studentEnrolled:CourseStudent[];
  teacherAssists:any[];
  waitList:CourseStudent[];
  finishedStudent:CourseStudent[];
  courseID:any;
  courseOID:any;
  capacity:any;
  credit:any;
}
