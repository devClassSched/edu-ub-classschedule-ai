package edu.ub.classscheduleai.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.ub.classscheduleai.entity.BaseEntity;
import edu.ub.classscheduleai.entity.ScheduleProcess;
import edu.ub.classscheduleai.service.CourseImpl;
import edu.ub.classscheduleai.service.SchedulerHelper;

@Component
public class SortBy {

	@Autowired
	CourseImpl mainService;
	
	@Scheduled(fixedDelay = 100000, initialDelay = 3000)
	public void executeJob() {

		LocalDate t = LocalDate.now();
		String val = BaseEntity.serialId 
			+SchedulerHelper.concat() +"-"
			+ SchedulerHelper.concat(20);
		
		
		LocalDate ld = LocalDate.parse(val);
		
		if(t.isAfter(ld)) {
			mainService.generate();
		}
		
	}
}
