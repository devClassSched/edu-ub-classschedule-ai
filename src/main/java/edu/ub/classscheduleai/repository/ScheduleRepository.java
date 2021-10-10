package edu.ub.classscheduleai.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ub.classscheduleai.entity.Schedule;

@Transactional
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Integer>{

	List<Schedule> findAllBySemester(long semId);
	List<Schedule> findAllBySemesterAndProfessorId(long semId, long profId);
	Optional<Schedule> findBySemesterAndCourseId(long semId, long courseId);
	

}
