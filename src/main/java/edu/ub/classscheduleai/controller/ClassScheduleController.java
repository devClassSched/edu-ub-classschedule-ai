package edu.ub.classscheduleai.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ub.classscheduleai.service.ClassroomImpl;
import edu.ub.classscheduleai.service.CourseImpl;
import edu.ub.classscheduleai.service.DomainObjectServiceImpl;
import edu.ub.classscheduleai.service.DomainValueServiceImpl;
import edu.ub.classscheduleai.service.ScheduleDetailServiceImpl;
import edu.ub.classscheduleai.service.ScheduleImpl;
import edu.ub.classscheduleai.service.ScheduleProcessImpl;
import edu.ub.classscheduleai.service.SchedulerServiceImpl;
import edu.ub.classscheduleai.service.SemesterImpl;
import edu.ub.classscheduleai.service.UserImpl;
import edu.ub.classscheduleai.util.Status;
import edu.ub.classscheduleai.entity.Classroom;
import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.DomainObject;
import edu.ub.classscheduleai.entity.DomainValue;
import edu.ub.classscheduleai.entity.Schedule;
import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.entity.ScheduleProcess;
import edu.ub.classscheduleai.entity.Semester;
import edu.ub.classscheduleai.entity.User;

@RestController
@EnableAutoConfiguration
@CrossOrigin(origins="*")
public class ClassScheduleController {

	@Autowired
	ClassroomImpl roomService;
	
	@Autowired
	CourseImpl courseService;
	
	@Autowired
	UserImpl userService;
	
	@Autowired
	DomainObjectServiceImpl dObjService;
	
	@Autowired
	DomainValueServiceImpl dValueService;
	
	@Autowired
	SemesterImpl semService;
	
	@Autowired
	ScheduleImpl schedService;
	
	@Autowired
	ScheduleDetailServiceImpl schedDetailService;
	
	@Autowired
	SchedulerServiceImpl schedulerService;
	
	@Autowired
	ScheduleProcessImpl scheduleProcService;
	
	@RequestMapping(value = "/classroom", method = RequestMethod.GET)
	public List<Classroom> getAllClassroom() {
	    return roomService.getAll();
	}
	
	@GetMapping(value = "/classroom/{id}")
	public Classroom getClassroomById(@PathVariable String id) {
		Optional<Classroom> classroom;
		try {
			classroom =  roomService.getById(Integer.parseInt(id));
			if(classroom.isPresent()) {
				return classroom.get();
			}
		}catch(Exception e) {}
		
		return new Classroom();
	}
	
	@PostMapping("/classroom")
	public Classroom saveClassroom(@RequestBody Classroom room) {
		return roomService.save(room);
	}
	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public List<Course> getAllCourse() {
	    return courseService.getAll();
	}
	
	@GetMapping(value = "/course/{id}")
	public Course getCourseById(@PathVariable String id) {
		Optional<Course> course;
		try {
			course =  courseService.getById(Integer.parseInt(id));
			if(course.isPresent()) {
				return course.get();
			}
		}catch(Exception e) {}
		
		return new Course();
	}
	
	@PostMapping("/course")
	public Course saveClassroom(@RequestBody Course course) {
		return courseService.save(course);
	}
	
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public List<User> getAllUsers() {
	    return userService.getAll();
	}
	
	@GetMapping(value = "/user/{id}")
	public User getUserById(@PathVariable String id) {
		Optional<User> user;
		try {
			user =  userService.getById(Integer.parseInt(id));
			if(user.isPresent()) {
				return user.get();
			}
		}catch(Exception e) {}
		
		return new User();
	}
	
	@PostMapping("/user")
	public User saveUser(@RequestBody User user) {
		return userService.save(user);
	}
	
	@RequestMapping(value = "/domainObject", method = RequestMethod.GET)
	public List<DomainObject> getAllDObj() {
	    return dObjService.getAll();
	}
	
	@RequestMapping(value = "/domainValue", method = RequestMethod.GET)
	public List<DomainValue> getAllDValue() {
	    return dValueService.getAllDomainValue();
	}
	
	@GetMapping(value = "/domainValue/{id}")
	public DomainValue getDomainValueById(@PathVariable String id) {
		Optional<DomainValue> user;
		try {
			user =  dValueService.getDomainValue(Integer.parseInt(id));
			if(user.isPresent()) {
				return user.get();
			}
		}catch(Exception e) {}
		
		return new DomainValue();
	}
	
	@GetMapping(value = "/domainValue/object/{id}")
	public List<DomainValue> getDomainValueByDomain(@PathVariable String id) {
		List<DomainValue> user = new ArrayList<>();
		try {
			user =  dValueService.getAllDomainValueByDomainObjectId(Integer.parseInt(id));
			return user;
		}catch(Exception e) {}
		
		return user;
	}
	
	@PostMapping("/domainValue")
	public DomainValue saveDomainValue(@RequestBody DomainValue obj) {
		return dValueService.save(obj);
	}
	
	@RequestMapping(value = "/semester", method = RequestMethod.GET)
	public List<Semester> getAllSemester() {
	    return semService.getAll();
	}
	
	@RequestMapping(value = "/semesterNotIn", method = RequestMethod.GET)
	public List<Semester> getAllSemesterNot() {
	    return semService.getAllNoSched();
	}
	
	@GetMapping(value = "/semester/{id}")
	public Semester getSemesterById(@PathVariable String id) {
		Optional<Semester> user;
		try {
			user =  semService.getById(Integer.parseInt(id));
			if(user.isPresent()) {
				return user.get();
			}
		}catch(Exception e) {}
		
		return new Semester();
	}
	
	@PostMapping("/semester")
	public Semester saveSemester(@RequestBody Semester obj) {
		return semService.save(obj);
	}
	
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public List<Schedule> getAllSchedule() {
	    return schedService.getAll();
	}
	
	@GetMapping(value = "/schedule/{id}")
	public Schedule getScheduleById(@PathVariable String id) {
		Optional<Schedule> user;
		try {
			user =  schedService.getById(Integer.parseInt(id));
			if(user.isPresent()) {
				return user.get();
			}
		}catch(Exception e) {}
		
		return new Schedule();
	}
	
	@PostMapping("/schedule")
	public Schedule saveSchedule(@RequestBody Schedule obj) {
		return schedService.save(obj);
	}
	
	@RequestMapping(value = "/scheduleDetail", method = RequestMethod.GET)
	public List<ScheduleDetail> getAllScheduleDetail() {
	    return schedDetailService.getAll();
	}
	
	@GetMapping(value = "/scheduleDetail/{semesterId}")
	public List<ScheduleDetail> getScheduleDetailBySemester(@PathVariable String semesterId) {
		
		List<ScheduleDetail> schedList = new ArrayList<>();
		try {
			Optional<Semester> sem = semService.getById(Integer.parseInt(semesterId));
			if(sem.isPresent()) {
				schedList =  schedDetailService.findAllBySemester(sem.get());			
			}
		}catch(Exception e) {}
		
		return schedList;
	}
	
	@GetMapping(value = "/scheduleDetail/{semesterId}/course/{courseId}")
	public List<ScheduleDetail> getScheduleDetailBySemesterAndCourse(@PathVariable String semesterId, @PathVariable String courseId) {
		
		List<ScheduleDetail> schedList = new ArrayList<>();
		try {
			Optional<Semester> sem = semService.getById(Integer.parseInt(semesterId));
			Optional<Course> course = courseService.getById(Integer.parseInt(courseId));
			if(sem.isPresent() && course.isPresent()) {
				schedList =  schedDetailService.findAllBySemesterAndCourse(sem.get(),course.get());			
			}
		}catch(Exception e) {}
		
		return schedList;
	}
	
	@GetMapping(value = "/scheduleDetail/{semesterId}/prof/{profId}")
	public List<ScheduleDetail> getScheduleDetailBySemesterAndProf(@PathVariable String semesterId, @PathVariable String profId) {
		
		List<ScheduleDetail> schedList = new ArrayList<>();
		try {
			Optional<Semester> sem = semService.getById(Integer.parseInt(semesterId));
			Optional<User> user = userService.getById(Integer.parseInt(profId));
			if(sem.isPresent() && user.isPresent()) {
				schedList =  schedDetailService.findAllBySemesterAndProfessor(sem.get(),user.get());			
			}
		}catch(Exception e) {}
		
		return schedList;
	}
	
	@GetMapping(value = "/scheduleDetail/{semesterId}/classroom/{roomId}")
	public List<ScheduleDetail> getScheduleDetailBySemesterAndRoom(@PathVariable String semesterId, @PathVariable String roomId) {
		
		List<ScheduleDetail> schedList = new ArrayList<>();
		try {
			Optional<Semester> sem = semService.getById(Integer.parseInt(semesterId));
			Optional<Classroom> room = roomService.getById(Integer.parseInt(roomId));
			if(sem.isPresent() && room.isPresent()) {
				schedList =  schedDetailService.findAllBySemesterAndClassroom(sem.get(),room.get());			
			}
		}catch(Exception e) {}
		
		return schedList;
	}
	
	/*@PostMapping("/scheduler/{semesterId}")
	public String schedule(@RequestBody ScheduleProcess obj) {
		List<ScheduleDetail> schedList = new ArrayList<>();
		
		try {
			if(obj!=null && obj.ge)
			Optional<Semester> sem = semService.getById(Integer.parseInt(semesterId));
			if(!scheduleProcService.findExistingProcess()) {
				if(sem.isPresent()) {
					List<Course> course = courseService.getAll();
					ScheduleProcess sp = new ScheduleProcess();
					sp.setStatus(Status.CREATED);
					sp.setCourseNotCompleted(course.size() - 1);
					sp.setCompletedCourses(0);
					sp.setTotalCourses(course.size() - 1);
					scheduleProcService.save(sp);
					return "Scheduler process saved for "+sem.get().getDescription()+".";
				}
			}else {return "Existing process still not complete.";}
		}catch(Exception e) {e.printStackTrace();}
		return "General error encountered.";
		
	}*/
	@RequestMapping(value = "/scheduler", method = RequestMethod.GET)
	public List<ScheduleProcess> getAllScheduler() {
	    return this.scheduleProcService.getAll();
	}
	
	@PostMapping("/scheduler")
	public ScheduleProcess saveUser(@RequestBody ScheduleProcess obj) {
		return this.scheduleProcService.save(obj);
	}
}
