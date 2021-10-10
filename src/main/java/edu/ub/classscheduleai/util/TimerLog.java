package edu.ub.classscheduleai.util;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimerLog {
	
	private LocalTime startTime;
	
	public void start() {
		startTime = LocalTime.now();
	}
	
	public void end(String msg) {
		LocalTime tmp = LocalTime.now();
		System.out.println("PROCESS TIME: "+ msg +" : "+ ChronoUnit.MINUTES.between(startTime, tmp));
	}
}
