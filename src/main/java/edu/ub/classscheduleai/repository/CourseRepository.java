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
	
	@Query("SELECT s FROM courses s left join schedule b on s.id = b.course and b.semester =:sem where b.id is null")
	List<Course> findAllNotCreatedBySemesterId(Semester sem);
}