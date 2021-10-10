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
	public void generateSchedule(int semesterId) {
		Optional<Semester> sem = this.getSemester(semesterId);
		List<Course> courseList = new ArrayList<>();
		List<User> profList = profService.getAllProf();
		
		if(sem.isPresent()) {
			ScheduleProcess spList = schedProcessService.findAllBySemester(sem.get());
			courseScheduler.init();
			if(spList != null) {
				courseList = courseService.findAllNotCreatedBySemesterId(sem.get());
				List<ScheduleDetail> currentList = schedDetailService.findAllBySemester(sem.get());
				for(Course c : courseList) {
					try {
						courseScheduler.registerCourse(c,sem.get());
						
						
						if(courseScheduler.save()) {
							List<User> trimmedUserList = profScheduler.getApplicableProf(profList, c.getCategory());
							profScheduler.registerSchedule(trimmedUserList, courseScheduler.getSchedList(), currentList);
							currentList.addAll(courseScheduler.getSchedList());
							
							spList.setStatus(Status.STARTED);
							spList.addCourseCompleted(1);
							schedProcessService.saveFlush(spList);
						}						
					}catch(Exception e) {}
				}
				spList.setStatus(Status.COMPLETED);
				schedProcessService.saveFlush(spList);
			}
		}		
	}
	
	
	public boolean clearSchedule(int semesterId) {
		Optional<Semester> sem = this.getSemester(semesterId);
		if(sem.isPresent()) {			
			List<Long> tmp = schedDetailService.findSchedIdPerSemester(sem.get());	
			schedDetailService.deleteByScheduleID(tmp);
			return true;
		}		
		return false;
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
