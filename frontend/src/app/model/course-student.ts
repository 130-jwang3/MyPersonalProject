import {Student} from "./student";
import {Course} from "./course";

export class CourseStudent {
  oid:number;
  studentOid:number;
  courseOid:number;
  state:any;
  courseRole:any;
  score:any;
  student:Student;
  course:Course;
}
