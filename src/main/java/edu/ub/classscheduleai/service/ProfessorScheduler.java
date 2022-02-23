package edu.ub.classscheduleai.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ub.classscheduleai.entity.DomainValue;
import edu.ub.classscheduleai.entity.Schedule;
import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.entity.User;
import edu.ub.classscheduleai.util.SortByAllotedSched;

@Component
public class ProfessorScheduler {

	private List<ScheduleDetail> profSchedule;
	
	@Autowired
	ScheduleDetailServiceImpl schedDetailService;
	
	@Autowired
	ScheduleImpl schedService;
	
	public ProfessorScheduler() {	}
	
	public List<User> getApplicableProf(List<User> profList,DomainValue categoryList){
		List<User> tmpUserList = new ArrayList<>();
		for(User u: profList) {
			if(u.getSpecialization().stream().anyMatch(e -> e.equals(categoryList))) {
				tmpUserList.add(u);
			}
		}
		return tmpUserList;
	}
	/**
	 * 
	 *
	 * @param profList list of allowed professors
	 * @param courseList scheduled list for the course
	 * @param semCourseList all courses in the semester
	 * @return
	 */
	public User registerSchedule( List<User> profList,List<ScheduleDetail> courseList,List<ScheduleDetail> semCourseList) {
		if(profList != null && profList.size() > 0 && courseList != null && courseList.size() > 0) {
			Schedule sched = courseList.get(0).getSchedule();
			profList = countAllocation(profList, semCourseList);
			boolean noConflict = false;
			for(User p : profList) {
				noConflict = false;
				
				profSchedule = getProfSched(semCourseList,p);			
				if(profSchedule != null && profSchedule.size() > 0) {
					for(ScheduleDetail sd : courseList) {
						if(noConflict) {
							continue;
						}
						for(ScheduleDetail profD : profSchedule) {
							if(sd.isConflict(profD)) {
								noConflict = true;
								continue;
							}
						}
					}
				}
				if(!noConflict) {
					//sched.setProfessor(p);	
					//schedService.save(sched);
					return p;
				}
			}
		}
		return null;		
	}
	
	private List<ScheduleDetail> getProfSched(List<ScheduleDetail> semCourseList,User prof){
		List<ScheduleDetail> profSchedList = new ArrayList<>();
		for(ScheduleDetail sd : semCourseList) {
			Schedule sched = sd.getSchedule();
			if(sched != null) {
				User prof1 = sched.getProfessor();
				if(prof1!= null && prof1.equals(prof)) {
					profSchedList.add(sd);
				}
			}
		}
		return profSchedList;
	}
	//Will sort professors based on current allocated subjects
	private List<User> countAllocation(List<User> profList, List<ScheduleDetail> semCourseList){
		//reset all to 0 hours
		profList.forEach(e -> e.setCurrentcount(0));
		
		for(ScheduleDetail sd: semCourseList) {
			if(sd != null) {
				if(sd.getSchedule() != null) {
					Schedule sched = sd.getSchedule();
					User prof = sched.getProfessor();
					if(prof != null) {				
						int noOfHours = sched.getCourse().getTotalHours();
						for(User u : profList) {					
							if(u.equals(prof)) {
								u.addCurrentcount(noOfHours);
							}
						}
					}
				}
			}
		}
		Collections.sort(profList,new SortByAllotedSched());
		
		return profList;
	}
		
}
