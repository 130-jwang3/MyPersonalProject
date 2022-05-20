use school;
/*
1.查询专业为“Computer Science"的学生的
名字, 
类别, 
性别，
出生年月日，
并按出生年月日降序排序
*/
select distinct
concat(last_name,", ",first_name) studentName,gender,type,cast(birthday as date)
from
student
join 
person
where  
student.major="Computer Science" and person.oid=student.person_oid
order by
birthday desc;
/*
2.查询每门课程的oid和学生平均分数
*/
select course.oid, course_grade
from
course,course_student_map
where
course_role="Student" and course_oid=course.oid;