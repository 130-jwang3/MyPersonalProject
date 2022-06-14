package com.personal.project.dao;

import com.personal.project.dto.*;

import java.util.*;

public interface CourseStudentDAO {
     public List<CourseStudent> findByCourseOid(Long courseId) throws Exception;
     public void addCourseStudent(CourseStudent courseStudent) throws Exception;
     public CourseStudent updateCourseStudent(CourseStudent courseStudent) throws Exception;
     public  void deleteCourseStudent(Long course_oid,Long student_oid)throws Exception;
}
