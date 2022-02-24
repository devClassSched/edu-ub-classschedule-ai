package edu.ub.classscheduleai.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.Classroom;
import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.DomainObject;
import edu.ub.classscheduleai.entity.DomainValue;
import edu.ub.classscheduleai.entity.Schedule;
import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.entity.Semester;
import edu.ub.classscheduleai.entity.User;
import edu.ub.classscheduleai.exception.AlreadySchedForSemesterException;
import edu.ub.classscheduleai.util.Coursetype;
import edu.ub.classscheduleai.util.Day;

@Service
public class CourseScheduler {

	@Autowired
	ScheduleDetailServiceImpl schedDetailService;

	@Autowired
	ScheduleImpl scheduleService;
	
	@Autowired
	DomainValueServiceImpl domainValueService;
	
	@Autowired
	DomainObjectServiceImpl domainObjService;
	
	@Autowired
	ClassroomImpl classroomService;
	
	private boolean alreadyHasSchedule;
	private List<ScheduleDetail> allSchedule;
	
	private List<ScheduleDetail> lectureSched;
	private List<ScheduleDetail> labSched;
	private List<ScheduleDetail> profSched;
	
	private List<Classroom> lectureRooms;
	private List<Classroom> labRooms;
	
	private List<DomainValue> categoryList;
	
	private List<Classroom> allRoom;
	private Semester semester;
	
	private Course course;
	private Schedule tmpSchedule;
	private EnumMap<Day,List<ScheduleDetail>> schedMapPerDay;
	
	public CourseScheduler() {
				
	}
	
	public void init() {
		Optional<DomainObject> categoryObj = domainObjService.getByDescription("CATEGORY");
		
		if(categoryObj.isPresent()) {
			categoryList = domainValueService.getAllDomainValueByDomainObjectId(categoryObj.get().getId());
			allRoom = classroomService.getAll();
			
		}else {
			allRoom = new ArrayList<>();
		}		
	}
	
	public boolean hasSchedule() {
		boolean hasSchedule = true;
		if(this.course.getLectureHours() > 0 && this.lectureSched.size() == 0) {
			hasSchedule = false;
		}
		if(this.course.getLabHours() > 0  && this.labSched.size() == 0) {
			hasSchedule = false;
		}
		return hasSchedule;
	}
	public boolean registerCourse(Course c, Semester sem) throws AlreadySchedForSemesterException{
		this.course = c;
		this.semester = sem;
		boolean courseScheduled = false;
		
		//Get All existing schedule for the given semester
		allSchedule = schedDetailService.findAllBySemester(this.semester);
		
		lectureSched = new ArrayList<>();
		labSched = new ArrayList<>();
		
		lectureRooms = new ArrayList<>();
		labRooms = new ArrayList<>();
		
		//Segregate all existing schedule per day
		schedMapPerDay = SchedulerHelper.getSchedulePerDay(allSchedule);		
		
		//find if Course is already scheduled
		if(SchedulerHelper.isCourseExist(allSchedule, this.course.getId())) {
			throw new AlreadySchedForSemesterException("Course "+course.getDescription() +" already registered.");
		}
		
		//Create temporary schedule object 
		//register course and semester
		tmpSchedule = new Schedule();
		tmpSchedule.setCourse(this.course);
		tmpSchedule.setSemester(this.semester);
		
		//if has lecture hours
		//find if there is defualt lecture room		
		if(this.course.getLectureHours() > 0) {
			if(this.course.getLectureRoom() != null) {
				this.lectureRooms.add(this.course.getLectureRoom());
				System.out.println("Has default lecture room: "+this.course.getLectureRoom().getDescription());
			}else {
				this.lectureRooms = classroomService.getByCategoryByClassroomType(allRoom, this.course.getCategory(), Coursetype.LECTURE);
				this.lectureRooms.forEach(System.out::println);
			}			
			courseScheduled = generateLectureSched();
		}
		System.out.println("Total Lecture hours: "+this.course.getLectureHours());
		this.getLectureSched().forEach(e -> System.out.println("Lecture: "+e));

		
		//if has lecture hours
		//find if there is defualt lecture room
		if(this.course.getLabHours() > 0) {
			if(this.course.getLabRoom() != null) {
				this.labRooms.add(this.course.getLabRoom());
				System.out.println("Has default laboraty room: "+this.course.getLabRoom().getDescription());
			}else {
				this.labRooms = classroomService.getByCategoryByClassroomType(allRoom, this.course.getCategory(), Coursetype.LABORATORY);
				this.labRooms.forEach(System.out::println);
			}
			courseScheduled = generateLabSched();
		}
		System.out.println("Total Lab hours: "+this.course.getLabHours());
		this.getLabSched().forEach(e -> System.out.println("Lab: "+e));
		return courseScheduled;
	}
	
	public void registerProf(User p) {
		if(p != null) {
			System.out.println("Prof Registered:"+p.getName());
			this.tmpSchedule.setProfessor(p);
		}
	}
	
		
	public boolean save() {
		try {
			
			this.scheduleService.save(tmpSchedule);
			this.schedDetailService.saveAll(this.getSchedList());
			return true;
		}catch(Exception e) { 
			e.printStackTrace();
			return false;
		}
	}
	public List<ScheduleDetail> getSchedList(){
		List<ScheduleDetail> tmp = new ArrayList<>();
		tmp.addAll(this.labSched);
		tmp.addAll(this.lectureSched);
		return tmp;
		
	}
	private boolean generateLabSched() {
		int hours = this.course.getLabHours();
		List<ScheduleDetail> multiDaySched = new ArrayList<>();
		if(hours == 3) {
			ScheduleDetail oneDaySched = allotOneDaySched(3,this.labRooms,Coursetype.LABORATORY);
			if(oneDaySched != null) {
				multiDaySched.add(oneDaySched);
			}
		}else if(hours == 6) {	
			ScheduleDetail tmpSched = allotOneDaySched(3,this.labRooms,Coursetype.LABORATORY);			
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
				tmpSched = allotOneNotSpecificDaySched(3,tmpSched.getClassroom(),tmpSched.getDay(),Coursetype.LABORATORY);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
				}else {						
					multiDaySched = new ArrayList<>();
				}
			}			
		}
		if(multiDaySched != null || multiDaySched.size() > 0) {
			this.labSched = multiDaySched;
			this.allSchedule.addAll(this.labSched);
			this.labSched.forEach(System.out::println);
			return true;
		}
		return false;
	}
	
	private boolean generateLectureSched() {
		int hours = this.course.getLectureHours();
		List<ScheduleDetail> multiDaySched = new ArrayList<>();
		if(hours == 1) {
			ScheduleDetail oneDaySched = allotOneDaySched(1,this.lectureRooms,Coursetype.LECTURE);
			if(oneDaySched != null) {
				multiDaySched.add(oneDaySched);
			}
		}else if(hours == 2) {				
			multiDaySched = alotDay(1,2,this.lectureRooms,Coursetype.LECTURE);
			
			if(multiDaySched == null || multiDaySched.size() == 0) {
				ScheduleDetail oneDaySched = allotOneDaySched(2,this.lectureRooms,Coursetype.LECTURE);
				if(oneDaySched != null) {
					multiDaySched.add(oneDaySched);
				}
			}
		}else if(hours == 3) {
			
			//FIND SCHED for MWF first of 1hr session
			multiDaySched = new ArrayList<>();
			ScheduleDetail tmpSched = allotOneSpecificDaySched(1,this.lectureRooms,Day.MONDAY,Coursetype.LECTURE);
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
				tmpSched = allotOneSpecificDaySched(1,tmpSched.getClassroom(),Day.WEDNESDAY,Coursetype.LECTURE);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(1,tmpSched.getClassroom(),Day.FRIDAY,Coursetype.LECTURE);
					if(tmpSched != null) {
						multiDaySched.add(tmpSched);
					}else {
						multiDaySched = new ArrayList<>();
					}
				}else {
					multiDaySched = new ArrayList<>();
				}
				
			}
			
			if(multiDaySched == null || multiDaySched.size() == 0) {
				
				//FIND SCHED FOR TTH of 1.5hr session
				tmpSched = allotOneSpecificDaySched(2,this.lectureRooms,Day.TUESDAY,Coursetype.LECTURE);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(1,tmpSched.getClassroom(),Day.THURSDAY,Coursetype.LECTURE);
					if(tmpSched != null) {
						multiDaySched.add(tmpSched);
					}else {
						multiDaySched = new ArrayList<>();
					}
				}
				if(multiDaySched == null || multiDaySched.size() == 0) {
					//FIND SCHED FOR single sched 3hr session
					ScheduleDetail oneDaySched = allotOneDaySched(3,this.lectureRooms,Coursetype.LECTURE);
					if(oneDaySched != null) {
						multiDaySched.add(oneDaySched);
					}
				}
			}
		}else if(hours == 4) {
			
			multiDaySched = new ArrayList<>();			
			ScheduleDetail tmpSched = allotOneSpecificDaySched(2,this.lectureRooms,Day.TUESDAY,Coursetype.LECTURE);
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
				tmpSched = allotOneSpecificDaySched(2,tmpSched.getClassroom(),Day.THURSDAY,Coursetype.LECTURE);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
				}else {
					multiDaySched = new ArrayList<>();
				}
			}
			
			if(multiDaySched == null || multiDaySched.size() == 0) {
				
				//FIND SCHED FOR TTH of 1.5hr session
				tmpSched = allotOneSpecificDaySched(1,this.lectureRooms,Day.MONDAY,Coursetype.LECTURE);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(1,tmpSched.getClassroom(),Day.WEDNESDAY,Coursetype.LECTURE);
					if(tmpSched != null) {
						multiDaySched.add(tmpSched);
						tmpSched = allotOneSpecificDaySched(2,tmpSched.getClassroom(),Day.FRIDAY,Coursetype.LECTURE);
						if(tmpSched != null) {
							multiDaySched.add(tmpSched);
						}else {
							multiDaySched = new ArrayList<>();
						}
					}else {
						multiDaySched = new ArrayList<>();
					}
				}				
				
			}
			
			if(multiDaySched == null || multiDaySched.size() == 0) {
				//FIND SCHED FOR single sched 3hr session
				ScheduleDetail firstDaySched = allotOneDaySched(3,this.lectureRooms,Coursetype.LECTURE);
				if(firstDaySched != null) {
					multiDaySched.add(firstDaySched);
					
					ScheduleDetail secondDaySched = allotOneNotSpecificDaySched(1,firstDaySched.getClassroom(),firstDaySched.getDay(),Coursetype.LECTURE);
					if(secondDaySched != null) {
						multiDaySched.add(secondDaySched);
					}else {
						//clear if 3rd day is not found
						multiDaySched = new ArrayList<>();
					}
				}
			}			
		}else if(hours == 5) {
			
			multiDaySched = new ArrayList<>();
			ScheduleDetail tmpSched = allotOneSpecificDaySched(2,this.lectureRooms,Day.MONDAY,Coursetype.LECTURE);
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
				tmpSched = allotOneSpecificDaySched(2,tmpSched.getClassroom(),Day.WEDNESDAY,Coursetype.LECTURE);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(1,tmpSched.getClassroom(),Day.FRIDAY,Coursetype.LECTURE);
					if(tmpSched != null) {
						multiDaySched.add(tmpSched);
					}else {
						multiDaySched = new ArrayList<>();
					}
				}else {
					multiDaySched = new ArrayList<>();
				}
			}	
			
			//Check if all 3 are assigned
			if(multiDaySched == null || multiDaySched.size() == 0) {
				//FIND SCHED FOR TTH of 1.5hr session
				tmpSched = allotOneSpecificDaySched(2,this.lectureRooms,Day.TUESDAY,Coursetype.LECTURE);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(3,tmpSched.getClassroom(),Day.THURSDAY,Coursetype.LECTURE);
					if(tmpSched != null) {
						multiDaySched.add(tmpSched);
					}else {
						multiDaySched = new ArrayList<>();
					}
				}else {
						multiDaySched = new ArrayList<>();
				}
			}
			if(multiDaySched == null || multiDaySched.size() == 0) {
				//FIND SCHED FOR single sched 3hr session
				ScheduleDetail firstDaySched = allotOneDaySched(3,this.lectureRooms,Coursetype.LECTURE);
				if(firstDaySched != null) {
					multiDaySched.add(firstDaySched);
					
					ScheduleDetail secondDaySched = allotOneNotSpecificDaySched(2,firstDaySched.getClassroom(),firstDaySched.getDay(),Coursetype.LECTURE);
					if(secondDaySched != null) {
						multiDaySched.add(secondDaySched);
					}else {
						//clear if 3rd day is not found
						multiDaySched = new ArrayList<>();
					}
				}
			}
			
		}else if(hours == 10) {			
			multiDaySched = new ArrayList<>();
			ScheduleDetail tmpSched =null;
			for(Day d : Day.values()) {
				tmpSched = allotOneSpecificDaySched(5,this.lectureRooms,d,Coursetype.LECTURE);
				multiDaySched.add(tmpSched);
				if(multiDaySched.size() > 2) {
					break;
				}
			}
			if(multiDaySched.size() <= 2) {
				for(Day d : Day.values()) {
					tmpSched = allotOneSpecificDaySched(2,this.lectureRooms,d,Coursetype.LECTURE);
					multiDaySched.add(tmpSched);
					if(multiDaySched.size() > 5) {
						break;
					}
				}
			}
			
		}
		if(multiDaySched != null && multiDaySched.size() > 0) {
			this.lectureSched = multiDaySched;
			this.allSchedule.addAll(this.lectureSched);
			this.lectureSched.forEach(System.out::println);
			return true;
		}
		return false;
	}
	private List<ScheduleDetail> alotDay(int noOfHours,int noOfDays,List<Classroom> rooms, Coursetype type){
		List<ScheduleDetail> multiDaySched = new ArrayList<>();
		ScheduleDetail tmpSched =null;
		for(Day d : Day.values()) {
			tmpSched = allotOneSpecificDaySched(noOfHours,rooms,d,type);	
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
			}
			if(multiDaySched.size() > noOfDays) {
				break;
			}
		}
		return multiDaySched;
	}
	/*
	 * Will schedule a given hour in any day of the week
	 * */
	private ScheduleDetail allotOneDaySched(double hours, List<Classroom> classrooms, Coursetype type) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
		for(Classroom room : classrooms) {
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				Set<Day> keys = schedMapPerRoom.keySet();
				if(keys != null && !(keys.size() == 0)) {
					for(Day d : keys) {
						List<ScheduleDetail> currentDay = schedMapPerRoom.get(d); 						
							LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
							if(startTime != null && !this.isSameDay(d,startTime,hours)) {
								ScheduleDetail tmp = new ScheduleDetail();
								tmp.setSchedule(tmpSchedule);
								tmp.setClassroom(room);
								tmp.setDay(d);
								tmp.setStartTime(startTime);
								tmp.setCoursetype(type);
								tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
								return tmp;
							}
					}		
				}else {
					LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(new ArrayList<>(), (int)(hours*60));
					for(Day d: Day.values()) {
						if(startTime != null && !this.isSameDay(d,startTime,hours)) {
							ScheduleDetail tmp = new ScheduleDetail();
							tmp.setSchedule(tmpSchedule);
							tmp.setClassroom(room);
							tmp.setDay(d);
							tmp.setStartTime(startTime);
							tmp.setCoursetype(type);
							tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
							return tmp;
						}
					}
				}
			}
			
		}
		return null;
	}
	
	/*
	 * Will schedule a given hour in specific day
	 * */
	private ScheduleDetail allotOneSpecificDaySched(double hours, List<Classroom> classrooms, Day allotedDay, Coursetype type) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
		for(Classroom room : classrooms) {
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				if(schedMapPerRoom.containsKey(allotedDay)) {
					List<ScheduleDetail> currentDay = schedMapPerRoom.get(allotedDay); 
					LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
					if(startTime != null && !this.isSameDay(allotedDay,startTime,hours)) {
							ScheduleDetail tmp = new ScheduleDetail();
							tmp.setSchedule(tmpSchedule);
							tmp.setClassroom(room);
							tmp.setDay(allotedDay);
							tmp.setStartTime(startTime);
							tmp.setCoursetype(type);
							tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
							return tmp;
											}
				}else {
					LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(new ArrayList<>(), (int)(hours*60));
					if(startTime != null && !this.isSameDay(allotedDay,startTime,hours)) {
						ScheduleDetail tmp = new ScheduleDetail();
						tmp.setSchedule(tmpSchedule);
						tmp.setClassroom(room);
						tmp.setDay(allotedDay);
						tmp.setStartTime(startTime);
						tmp.setCoursetype(type);
						tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
						return tmp;
					}
				}		
		}else{
				LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(new ArrayList<>(), (int)(hours*60));
				if(startTime != null && !this.isSameDay(allotedDay,startTime,hours)) {
					ScheduleDetail tmp = new ScheduleDetail();
					tmp.setSchedule(tmpSchedule);
					tmp.setClassroom(room);
					tmp.setDay(allotedDay);
					tmp.setCoursetype(type);
					tmp.setStartTime(startTime);
					tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
					return tmp;
				}
			}
			
		}
		return null;
	}
	
	/*
	 * Will schedule a given hour NOT in specific day
	 * */
	private ScheduleDetail allotOneNotSpecificDaySched(double hours, List<Classroom> classrooms, Day allotedDay, Coursetype type) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
		for(Classroom room : classrooms) {
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				Set<Day> keys = schedMapPerRoom.keySet();
				if(keys != null && !(keys.size() == 0)) {
					for(Day d : keys) {
						if(d != allotedDay) { 
							List<ScheduleDetail> currentDay = schedMapPerRoom.get(d); 
							LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
							if(startTime != null && !this.isSameDay(d,startTime,hours)) {
									ScheduleDetail tmp = new ScheduleDetail();
									tmp.setSchedule(tmpSchedule);
									tmp.setClassroom(room);
									tmp.setDay(d);
									tmp.setStartTime(startTime);
									tmp.setCoursetype(type);
									tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
									return tmp;
							}
						}
					}
				}else {
					LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(new ArrayList<>(), (int)(hours*60));
					if(startTime != null && !this.isSameDay(allotedDay,startTime,hours)) {
						ScheduleDetail tmp = new ScheduleDetail();
						tmp.setSchedule(tmpSchedule);
						tmp.setClassroom(room);
						tmp.setDay(allotedDay);
						tmp.setStartTime(startTime);
						tmp.setCoursetype(type);
						tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
						return tmp;
					}
				}
			}
			
		}
		return null;
	}
	
	/*
	 * Will schedule a given hour in any day of the week in specific classroom
	 * */
	private ScheduleDetail allotOneDaySched(double hours, Classroom room,Coursetype type) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				Set<Day> keys = schedMapPerRoom.keySet();
				if(keys != null && !(keys.size() == 0)) {
					for(Day d : keys) {
						List<ScheduleDetail> currentDay = schedMapPerRoom.get(d); 
						LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
							if(startTime != null && !this.isSameDay(d,startTime,hours)) {
								ScheduleDetail tmp = new ScheduleDetail();
								tmp.setSchedule(tmpSchedule);
								tmp.setClassroom(room);
								tmp.setDay(d);
								tmp.setStartTime(startTime);
								tmp.setCoursetype(type);
								tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
								return tmp;
							}
					}	
				}else {
					LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(new ArrayList<>(), (int)(hours*60));
					if(startTime != null && !this.isSameDay(Day.MONDAY,startTime,hours)) {
						ScheduleDetail tmp = new ScheduleDetail();
						tmp.setSchedule(tmpSchedule);
						tmp.setClassroom(room);
						tmp.setDay(Day.MONDAY);
						tmp.setStartTime(startTime);
						tmp.setCoursetype(type);
						tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
						return tmp;
					}
				}
			}
		
		return null;
	}
	
	/*
	 * Will schedule a given hour in specific day
	 * */
	private ScheduleDetail allotOneSpecificDaySched(double hours, Classroom room, Day allotedDay,Coursetype type) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				if(schedMapPerRoom.containsKey(allotedDay)) {
					List<ScheduleDetail> currentDay = schedMapPerRoom.get(allotedDay); 
					LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
						if(startTime != null && !this.isSameDay(allotedDay,startTime,hours)) {
							ScheduleDetail tmp = new ScheduleDetail();
							tmp.setSchedule(tmpSchedule);
							tmp.setClassroom(room);
							tmp.setDay(allotedDay);
							tmp.setStartTime(startTime);
							tmp.setCoursetype(type);
							tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
							return tmp;
						}
				}else {
					LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(new ArrayList<>(), (int)(hours*60));
					if(startTime != null && !this.isSameDay(allotedDay,startTime,hours)) {
						ScheduleDetail tmp = new ScheduleDetail();
						tmp.setSchedule(tmpSchedule);
						tmp.setClassroom(room);
						tmp.setDay(allotedDay);
						tmp.setStartTime(startTime);
						tmp.setCoursetype(type);
						tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
						return tmp;
					}
				}			
			}
		return null;
	}
	
	/*
	 * Will schedule a given hour NOT in specific day
	 * */
	private ScheduleDetail allotOneNotSpecificDaySched(double hours, Classroom room, Day allotedDay,Coursetype type) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				Set<Day> keys = schedMapPerRoom.keySet();
				if(keys!=null && !(keys.size() == 0)) {
					for(Day d : keys) {
						if(d != allotedDay) { 
							List<ScheduleDetail> currentDay = schedMapPerRoom.get(d); 
							LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
								if(startTime != null && !this.isSameDay(d,startTime,hours)) {
									ScheduleDetail tmp = new ScheduleDetail();
									tmp.setSchedule(tmpSchedule);
									tmp.setClassroom(room);
									tmp.setDay(d);
									tmp.setStartTime(startTime);
									tmp.setCoursetype(type);
									tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
									return tmp;
								}
						}
					}			
				}else {
					LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(new ArrayList<>(), (int)(hours*60));
					if(startTime != null && !this.isSameDay(Day.MONDAY,startTime,hours)) {
						ScheduleDetail tmp = new ScheduleDetail();
						tmp.setSchedule(tmpSchedule);
						tmp.setClassroom(room);
						tmp.setDay(Day.MONDAY);
						tmp.setStartTime(startTime);
						tmp.setCoursetype(type);
						tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
						return tmp;
					}
				}
			}
		return null;
	}
	
	public List<ScheduleDetail> getLectureSched() {
		return lectureSched;
	}

	
	public List<ScheduleDetail> getLabSched() {
		return labSched;
	}


	public Schedule getTmpSchedule() {
		return tmpSchedule;
	}
	
	private boolean isSameDay(Day d, LocalTime t, double addHours) {
		List<ScheduleDetail> sd = this.getSchedList();
		
		List<ScheduleDetail> days = sd.stream().filter(e ->{
				if(e != null) { return e.getDay() == d;
				}else{return false;}})
				.collect(Collectors.toList());
		System.out.print("Checking day "+d +" : Time "+t.toString());
		
		if(days!=null && days.size()>0) {
			System.out.print(" Schedule array Size: "+days.size());
			if(SchedulerHelper.findMaxCourseHourPerDay(days,this.course.getId(),addHours)) {
				System.out.print(" Found Max time.");
				return true;
			}
			if(SchedulerHelper.hasConflictPerCourse(days, t)) {
				System.out.print(" Found Conflict time.");
				return true;
			}
		}
		System.out.print(" Found no conflict");
		System.out.println();
		return false;
	}
}
