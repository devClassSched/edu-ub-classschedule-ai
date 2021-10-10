package edu.ub.classscheduleai.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.util.Day;
import edu.ub.classscheduleai.util.SortByTime;

public class SchedulerHelper {

	private static final LocalTime schoolEndTime = LocalTime.of(20, 0);
	
	/*
	 * Find last class schedule in a list
	 * source is of schedule per classroom
	 * */
	public static LocalTime findNextAvailableSchedulePerClassroom(List<ScheduleDetail> source,int hours) {
		LocalTime startTime;
		if(source != null && source.size() > 0) {
			startTime = source.get(source.size() - 1).getEndTime();			
			LocalTime allottedTime = startTime.plus(hours, ChronoUnit.MINUTES);
			if(allottedTime.isBefore(schoolEndTime)) {
					return startTime;				
			}			
		}else {
			startTime = LocalTime.of(7, 0);
			return startTime;
		}
		return null;
	}
	
	/*
	 * Find if course already exists
	 */
	public static boolean isCourseExist(List<ScheduleDetail> source, int courseId) {
		long count = source.stream().filter(e -> e.getSchedule().getCourse().getId() == courseId).count();
		if(count > 0 )
			return true;
		
		return false;
	}
	
	/*
	 * Find if Course is already have max 3hrs alloted for the day.
	 */
	public static boolean findMaxCourseHourPerDay(List<ScheduleDetail> source,int courseId) {
		int totalHours = 0;
		if(source != null && source.size() > 0 ) {
			for(ScheduleDetail sd : source) {
				if(sd.getSchedule().getCourse().getId() == courseId) {
					totalHours += ChronoUnit.MINUTES.between(sd.getEndTime(), sd.getStartTime());
				}
			}
			if(totalHours > 0) {
				return (totalHours/60) > 3.0;
			}
		}
		return false;
	}
	
	/*
	 * Find if given {start} has conflict with a list of schedules
	 * source is of schedule per course
	 * */
	public static boolean hasConflictPerCourse(List<ScheduleDetail> source, LocalTime start) {
		boolean conflict = false;
		for(ScheduleDetail sd : source) {
			if(sd.getStartTime().isAfter(start) || sd.getStartTime().compareTo(start) == 0) {
				if(sd.getEndTime().compareTo(start) == 0 || sd.getEndTime().isBefore(start)) {
					conflict = true;
				}
			}
		}
		return conflict;
	}
	
	/*
	 * Get all schedule per given Room 
	 * 
	 */
	public static List<ScheduleDetail> getAllSchedulePerRoom(List<ScheduleDetail> source, int roomId){
		List<ScheduleDetail> tmp = source.stream().filter(e -> e.getClassroom().getId() == roomId).collect(Collectors.toList());
		
		return tmp;
	}
	
	public static EnumMap<Day,List<ScheduleDetail>> getSchedulePerDay(List<ScheduleDetail> tmp){
		EnumMap<Day,List<ScheduleDetail>> dayMap = new EnumMap<Day,List<ScheduleDetail>>(Day.class);
		if(tmp != null && tmp.size() > 0) {
			List<ScheduleDetail> m = new ArrayList<>();
			List<ScheduleDetail> t = new ArrayList<>();
			List<ScheduleDetail> w = new ArrayList<>();
			List<ScheduleDetail> th = new ArrayList<>();
			List<ScheduleDetail> f= new ArrayList<>();
			
			for(ScheduleDetail sd : tmp) {
				switch(sd.getDay()) {
					case MONDAY: m.add(sd);
						break;
					case TUESDAY: t.add(sd);
						break;
					case WEDNESDAY: w.add(sd);
						break;
					case THURSDAY: th.add(sd);
						break;
					case FRIDAY: f.add(sd);
						break;					
				}
			}
			
			Collections.sort(m,new SortByTime());
			dayMap.put(Day.MONDAY, m);
			Collections.sort(t,new SortByTime());
			dayMap.put(Day.TUESDAY, t);
			Collections.sort(w,new SortByTime());
			dayMap.put(Day.WEDNESDAY, w);
			Collections.sort(th,new SortByTime());
			dayMap.put(Day.THURSDAY, th);
			Collections.sort(f,new SortByTime());
			dayMap.put(Day.FRIDAY, t);
		}
		return dayMap;
	}
	
	/*
	 * Get List of schedule per given day
	 */
	public static List<ScheduleDetail> getSchedulePerGivenDay(List<ScheduleDetail> tmp, Day d){
		List<ScheduleDetail> tmpDays = new ArrayList<>();
		if(tmp != null && tmp.size() > 0) {		
			
			for(ScheduleDetail sd : tmp) {
				if(sd.getDay() == d) {
					tmpDays.add(sd);
				}				
			}
			
			Collections.sort(tmpDays,new SortByTime());
		}
		return tmpDays;
	}
}
