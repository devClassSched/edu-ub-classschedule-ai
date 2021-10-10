package edu.ub.classscheduleai.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ub.classscheduleai.entity.Schedule;
import edu.ub.classscheduleai.entity.ScheduleDetail;

@Transactional
@Repository
public interface ScheduleDetailsRepository extends JpaRepository<ScheduleDetail,Integer>{
	
	//List<ScheduleDetail> findAllByScheduled(long objectId);
	
	@Query("SELECT ai FROM scheduledetail ai join schedule s on (ai.schedule = s.id) "
			+ "WHERE  s.semester = :semID order by ai.day,ai.startTime")
	List<ScheduleDetail> findAllBySemesterId(long semID);
		
	@Query("SELECT ai FROM scheduledetail ai join schedule s on (ai.schedule = s.id) "
			+ "WHERE  s.semester = :semID and s.professor = :profID order by ai.day,ai.startTime")
	List<ScheduleDetail> findAllBySemesterIdAndProfessorId(long semID, long profID);
	
	@Query("SELECT ai FROM scheduledetail ai join schedule s on (ai.schedule = s.id) "
			+ "WHERE  s.semester = :semID and ai.classroom = :classroomID order by ai.day,ai.startTime")
	List<ScheduleDetail> findAllBySemesterIdAndClassroomId(long semID, long classroomID);
	
	@Query("SELECT ai FROM scheduledetail ai join schedule s on (ai.schedule = s.id) "
			+ "WHERE  s.semester = :semID and s.course = :courseID order by ai.day,ai.startTime")
	List<ScheduleDetail> findAllBySemesterIdAndCourseId(long semID, long courseID);
	
	@Query("SELECT distinct(ai.schedule) FROM scheduledetail ai join schedule s on (ai.schedule = s.id) "
			+ "WHERE  s.semester = :semID order by ai.day,ai.startTime")
	List<Long> findAllDistinctSchedIdBySemesterId(long semID);
	
	@Modifying
	@Query("delete from scheduledetail where schedule in ?1")
	void deleteBySchedule(List<Schedule> schedIdList);
	
}
