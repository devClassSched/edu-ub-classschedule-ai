package edu.ub.classscheduleai.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.Schedule;
import edu.ub.classscheduleai.entity.Semester;

@Transactional
@Repository
public interface CourseRepository extends JpaRepository<Course,Integer>{
	
	@Query("SELECT s FROM courses s where s.semester = :sem")
	List<Course> findAllCourseForSemester(Semester sem);
	
	@Query("SELECT s FROM courses s join schedule b on  b.semester= s.semester and b.course = s where s.id = :courseId")
	List<Course> findIfCanDelete(int courseId);
}