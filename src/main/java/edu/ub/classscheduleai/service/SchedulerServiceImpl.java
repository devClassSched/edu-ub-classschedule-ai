package edu.ub.classscheduleai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.Schedule;
import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.entity.ScheduleProcess;
import edu.ub.classscheduleai.entity.Semester;
import edu.ub.classscheduleai.entity.User;
import edu.ub.classscheduleai.util.Status;

@Service
public class SchedulerServiceImpl  implements SchedulerService{
	
	@Autowired
	CourseImpl courseService;
	
	@Autowired
	UserImpl profService;
	
	@Autowired
	ScheduleImpl schedService;
	
	@Autowired
	ScheduleDetailServiceImpl schedDetailService;
	
	@Autowired 
	DomainValueServiceImpl domValueService;
	
	@Autowired 
	SemesterImpl semService;
	
	@Autowired
	ScheduleProcessImpl schedProcessService;
	
	@Autowired
	ProfessorScheduler profScheduler;
	
	@Autowired
	CourseScheduler courseScheduler;
	
	@Transactional
	public void generateSchedule(ScheduleProcess sp) {
		System.out.println(sp.getSem());
		Optional<Semester> sem = semService.getById(sp.getSem().getId());
		List<Course> courseList = new ArrayList<>();
		List<User> profList = profService.getAllProf();
		
		if(sem.isPresent()) {
			ScheduleProcess spList = sp;
			courseScheduler.init();
			if(spList != null) {
				spList.setStatus(Status.STARTED);
				schedProcessService.saveFlush(spList);
				courseList = courseService.findAllNotCreatedBySemesterId(sem.get());
				List<ScheduleDetail> currentList = schedDetailService.findAllBySemester(sem.get());
				System.out.println(courseList.size());
				for(Course c : courseList) {
					try {
						System.out.println("Processing "+c.getDescription());
						courseScheduler.registerCourse(c,sem.get());	
						List<User> trimmedUserList = profScheduler.getApplicableProf(profList, c.getCategory());
						courseScheduler.registerProf(profScheduler.registerSchedule(trimmedUserList, courseScheduler.getSchedList(), currentList));						
						if(courseScheduler.save()) {							
							currentList.addAll(courseScheduler.getSchedList());														
							spList.addCourseCompleted(1);
							schedProcessService.saveFlush(spList);
						}						
					}catch(Exception e) { e.printStackTrace();}
				}
				spList.setStatus(Status.COMPLETED);
				schedDetailService.saveAll(currentList);
				schedProcessService.saveFlush(spList);
			}
		}
	}
	
		
	private List<ScheduleDetail> getAllSchedule(int semesterId){
		Optional<Semester> sem = this.getSemester(semesterId);
		if(sem.isPresent()) {
			return schedDetailService.findAllBySemester(sem.get());	
		}
		return null;		
	}
	
	private Optional<Semester> getSemester(int id) {
		return semService.getById(id);
	}
	
	
}
