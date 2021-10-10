package edu.ub.classscheduleai.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ub.classscheduleai.entity.Classroom;
import edu.ub.classscheduleai.util.Coursetype;

@Transactional
@Repository
public interface ClassroomRepository extends JpaRepository<Classroom,Integer>{
	
	//@Query("SELECT s FROM classroom s where s.catregroy =:id")
	List<Classroom> findAllByCategory(long id);
	
	//@Query("SELECT s FROM classroom s where s.catregroy =:id and s.coursetype =:type")
	List<Classroom> findAllByCategoryAndCoursetype(long id,Coursetype type);
}
