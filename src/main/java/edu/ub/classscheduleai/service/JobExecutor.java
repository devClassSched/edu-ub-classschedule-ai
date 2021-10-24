package edu.ub.classscheduleai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.ub.classscheduleai.entity.ScheduleProcess;
import edu.ub.classscheduleai.util.Status;
import edu.ub.classscheduleai.util.TimerLog;

@Component
public class JobExecutor {

	@Autowired
	ScheduleProcessImpl mainService;
	
	@Autowired 
	SchedulerServiceImpl schedulerService;
	
	@Scheduled(fixedDelay = 5000, initialDelay = 3000)
	public void executeJob() {
		ScheduleProcess sp = mainService.getNewJob(Status.CREATED);
		
		if(sp != null) {
			System.out.println("Scheduler AI : Found new job for "+sp.getSem().getDescription());
			TimerLog tl = new TimerLog();
			tl.start();
			schedulerService.generateSchedule(sp);
			tl.end("Scheduling for "+sp.getSem().getDescription());
			System.out.println("Scheduler AI : Process Complete");
		}
		
	}
}
