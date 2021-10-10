package edu.ub.classscheduleai.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.entity.Semester;

@Transactional
@Repository
public interface SemesterRepository extends JpaRepository<Semester,Integer>{

	@Query("SELECT ai FROM semester ai left join scheduleprocess s on (ai.id = s.id) "
			+ "WHERE  s.id is null order by ai.id ")
	List<Semester> findAllNotInScheduler();
}
