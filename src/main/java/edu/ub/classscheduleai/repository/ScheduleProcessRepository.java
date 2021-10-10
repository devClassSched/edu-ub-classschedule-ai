package edu.ub.classscheduleai.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ub.classscheduleai.entity.ScheduleProcess;
import edu.ub.classscheduleai.util.Status;

@Transactional
@Repository
public interface ScheduleProcessRepository extends JpaRepository<ScheduleProcess,Integer>{

	@Query("SELECT ai FROM scheduleprocess ai where status in ?1")
	ScheduleProcess findOpenProcess(List<Status> list);
	
	
}
