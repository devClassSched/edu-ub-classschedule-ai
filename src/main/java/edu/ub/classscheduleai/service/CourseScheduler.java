package edu.ub.classscheduleai.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	public boolean registerCourse(Course c, Semester sem) throws AlreadySchedForSemesterException{
		this.course = c;
		this.semester = sem;
		boolean courseScheduled = false;
		
		allSchedule = schedDetailService.findAllBySemester(this.semester);
		
		lectureSched = new ArrayList<>();
		labSched = new ArrayList<>();
		
		schedMapPerDay = SchedulerHelper.getSchedulePerDay(allSchedule);		
			
		if(SchedulerHelper.isCourseExist(allSchedule, this.course.getId())) {
			throw new AlreadySchedForSemesterException("Course "+course.getDescription() +" already registered.");
		}
		
		tmpSchedule = new Schedule();
		tmpSchedule.setCourse(this.course);
		tmpSchedule.setSemester(this.semester);
		
		if(this.course.getLectureHours() > 0) {
			if(this.course.getLectureRoom() != null) {
				lectureRooms.add(this.course.getLectureRoom());
			}else {
				lectureRooms = classroomService.getByCategoryByClassroomType(allRoom, this.course.getCategory(), Coursetype.LECTURE);
			}
			courseScheduled = generateLectureSched();
		}
		
		if(this.course.getLabHours() > 0) {
			if(this.course.getLabRoom() != null) {
				labRooms.add(this.course.getLabRoom());
			}else {
				labRooms = classroomService.getByCategoryByClassroomType(allRoom, this.course.getCategory(), Coursetype.LABORATORY);
			}
			courseScheduled = generateLabSched();
		}
				
		return courseScheduled;
	}
	
	public void registerProf(List<ScheduleDetail> profDetail) {
		this.profSched = profDetail;
	}
	
		
	public boolean save() {
		try {
			
			//this.scheduleService.save(tmpSchedule);
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
			ScheduleDetail oneDaySched = allotOneDaySched(3,this.labRooms);
			if(oneDaySched != null) {
				multiDaySched.add(oneDaySched);
			}
		}else if(hours == 6) {	
			ScheduleDetail tmpSched = allotOneDaySched(3,this.labRooms);			
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
				tmpSched = allotOneNotSpecificDaySched(3,tmpSched.getClassroom(),tmpSched.getDay());
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
				}else {						
					multiDaySched = new ArrayList<>();
				}
			}			
		}
		if(multiDaySched != null || multiDaySched.size() > 0) {
			this.labSched = multiDaySched;
			return true;
		}
		return false;
	}
	
	private boolean generateLectureSched() {
		int hours = this.course.getLectureHours();
		List<ScheduleDetail> multiDaySched = new ArrayList<>();
		if(hours == 1) {
			ScheduleDetail oneDaySched = allotOneDaySched(1,this.lectureRooms);
			if(oneDaySched != null) {
				multiDaySched.add(oneDaySched);
			}
		}else if(hours == 2) {		
			ScheduleDetail tmpSched = allotOneSpecificDaySched(1,this.lectureRooms,Day.TUESDAY);
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
				tmpSched = allotOneSpecificDaySched(1,tmpSched.getClassroom(),Day.THURSDAY);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
				}else {
					multiDaySched = new ArrayList<>();
				}
			}
			if(multiDaySched == null || multiDaySched.size() == 0) {
				ScheduleDetail oneDaySched = allotOneDaySched(2,this.lectureRooms);
				if(oneDaySched != null) {
					multiDaySched.add(oneDaySched);
				}
			}
		}else if(hours == 3) {
			
			//FIND SCHED for MWF first of 1hr session
			multiDaySched = new ArrayList<>();
			ScheduleDetail tmpSched = allotOneSpecificDaySched(1,this.lectureRooms,Day.MONDAY);
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
				tmpSched = allotOneSpecificDaySched(1,tmpSched.getClassroom(),Day.WEDNESDAY);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(1,tmpSched.getClassroom(),Day.FRIDAY);
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
				tmpSched = allotOneSpecificDaySched(1.5,this.lectureRooms,Day.TUESDAY);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(1.5,tmpSched.getClassroom(),Day.THURSDAY);
					if(tmpSched != null) {
						multiDaySched.add(tmpSched);
					}else {
						multiDaySched = new ArrayList<>();
					}
				}
				if(multiDaySched == null || multiDaySched.size() == 0) {
					//FIND SCHED FOR single sched 3hr session
					ScheduleDetail oneDaySched = allotOneDaySched(3,this.lectureRooms);
					if(oneDaySched != null) {
						multiDaySched.add(oneDaySched);
					}
				}
			}
		}else if(hours == 4) {
			
			multiDaySched = new ArrayList<>();			
			ScheduleDetail tmpSched = allotOneSpecificDaySched(2,this.lectureRooms,Day.TUESDAY);
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
				tmpSched = allotOneSpecificDaySched(2,tmpSched.getClassroom(),Day.THURSDAY);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
				}else {
					multiDaySched = new ArrayList<>();
				}
			}
			
			if(multiDaySched == null || multiDaySched.size() == 0) {
				
				//FIND SCHED FOR TTH of 1.5hr session
				tmpSched = allotOneSpecificDaySched(1.5,this.lectureRooms,Day.MONDAY);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(1.5,tmpSched.getClassroom(),Day.WEDNESDAY);
					if(tmpSched != null) {
						multiDaySched.add(tmpSched);
						tmpSched = allotOneSpecificDaySched(1,tmpSched.getClassroom(),Day.FRIDAY);
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
				ScheduleDetail firstDaySched = allotOneDaySched(3,this.lectureRooms);
				if(firstDaySched != null) {
					multiDaySched.add(firstDaySched);
					
					ScheduleDetail secondDaySched = allotOneNotSpecificDaySched(1,firstDaySched.getClassroom(),firstDaySched.getDay());
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
			ScheduleDetail tmpSched = allotOneSpecificDaySched(1.5,this.lectureRooms,Day.MONDAY);
			if(tmpSched != null) {
				multiDaySched.add(tmpSched);
				tmpSched = allotOneSpecificDaySched(1.5,tmpSched.getClassroom(),Day.WEDNESDAY);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(2,tmpSched.getClassroom(),Day.FRIDAY);
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
				tmpSched = allotOneSpecificDaySched(2.5,this.lectureRooms,Day.TUESDAY);
				if(tmpSched != null) {
					multiDaySched.add(tmpSched);
					tmpSched = allotOneSpecificDaySched(2.5,tmpSched.getClassroom(),Day.THURSDAY);
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
				ScheduleDetail firstDaySched = allotOneDaySched(3,this.lectureRooms);
				if(firstDaySched != null) {
					multiDaySched.add(firstDaySched);
					
					ScheduleDetail secondDaySched = allotOneNotSpecificDaySched(1,firstDaySched.getClassroom(),firstDaySched.getDay());
					if(secondDaySched != null) {
						multiDaySched.add(secondDaySched);
					}else {
						//clear if 3rd day is not found
						multiDaySched = new ArrayList<>();
					}
				}
			}
			
		}
		if(multiDaySched != null && multiDaySched.size() > 0) {
			this.lectureSched = multiDaySched;
			return true;
		}
		return false;
	}
	
	/*
	 * Will schedule a given hour in any day of the week
	 * */
	private ScheduleDetail allotOneDaySched(double hours, List<Classroom> classrooms) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
		for(Classroom room : classrooms) {
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				Set<Day> keys = schedMapPerRoom.keySet();
				for(Day d : keys) {
					List<ScheduleDetail> currentDay = schedMapPerRoom.get(d); 
					if(schedMapPerDay.containsKey(d)) {
						hasMaxHourAlloted = SchedulerHelper.findMaxCourseHourPerDay(schedMapPerDay.get(d), this.course.getId());
					}
					if(!hasMaxHourAlloted) {
						LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
						if(startTime != null) {
							ScheduleDetail tmp = new ScheduleDetail();
							tmp.setSchedule(tmpSchedule);
							tmp.setClassroom(room);
							tmp.setDay(d);
							tmp.setStartTime(startTime);
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
	private ScheduleDetail allotOneSpecificDaySched(double hours, List<Classroom> classrooms, Day allotedDay) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
		for(Classroom room : classrooms) {
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				if(schedMapPerRoom.containsKey(allotedDay)) {
					List<ScheduleDetail> currentDay = schedMapPerRoom.get(allotedDay); 
					if(schedMapPerDay.containsKey(allotedDay)) {
						hasMaxHourAlloted = SchedulerHelper.findMaxCourseHourPerDay(schedMapPerDay.get(allotedDay), this.course.getId());
					}
					if(!hasMaxHourAlloted) {
						LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
						if(startTime != null) {
							ScheduleDetail tmp = new ScheduleDetail();
							tmp.setSchedule(tmpSchedule);
							tmp.setClassroom(room);
							tmp.setDay(allotedDay);
							tmp.setStartTime(startTime);
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
	 * Will schedule a given hour NOT in specific day
	 * */
	private ScheduleDetail allotOneNotSpecificDaySched(double hours, List<Classroom> classrooms, Day allotedDay) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
		for(Classroom room : classrooms) {
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				Set<Day> keys = schedMapPerRoom.keySet();
				for(Day d : keys) {
					if(d != allotedDay) { 
						List<ScheduleDetail> currentDay = schedMapPerRoom.get(d); 
						if(schedMapPerDay.containsKey(d)) {
							hasMaxHourAlloted = SchedulerHelper.findMaxCourseHourPerDay(schedMapPerDay.get(d), this.course.getId());
						}
						if(!hasMaxHourAlloted) {
							LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
							if(startTime != null) {
								ScheduleDetail tmp = new ScheduleDetail();
								tmp.setSchedule(tmpSchedule);
								tmp.setClassroom(room);
								tmp.setDay(d);
								tmp.setStartTime(startTime);
								tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
								return tmp;
							}
						}
					}
				}				
			}
			
		}
		return null;
	}
	
	/*
	 * Will schedule a given hour in any day of the week in specific classroom
	 * */
	private ScheduleDetail allotOneDaySched(double hours, Classroom room) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				Set<Day> keys = schedMapPerRoom.keySet();
				for(Day d : keys) {
					List<ScheduleDetail> currentDay = schedMapPerRoom.get(d); 
					if(schedMapPerDay.containsKey(d)) {
						hasMaxHourAlloted = SchedulerHelper.findMaxCourseHourPerDay(schedMapPerDay.get(d), this.course.getId());
					}
					if(!hasMaxHourAlloted) {
						LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
						if(startTime != null) {
							ScheduleDetail tmp = new ScheduleDetail();
							tmp.setSchedule(tmpSchedule);
							tmp.setClassroom(room);
							tmp.setDay(d);
							tmp.setStartTime(startTime);
							tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
							return tmp;
						}
					}
				}				
			}
		
		return null;
	}
	
	/*
	 * Will schedule a given hour in specific day
	 * */
	private ScheduleDetail allotOneSpecificDaySched(double hours, Classroom room, Day allotedDay) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				if(schedMapPerRoom.containsKey(allotedDay)) {
					List<ScheduleDetail> currentDay = schedMapPerRoom.get(allotedDay); 
					if(schedMapPerDay.containsKey(allotedDay)) {
						hasMaxHourAlloted = SchedulerHelper.findMaxCourseHourPerDay(schedMapPerDay.get(allotedDay), this.course.getId());
					}
					if(!hasMaxHourAlloted) {
						LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
						if(startTime != null) {
							ScheduleDetail tmp = new ScheduleDetail();
							tmp.setSchedule(tmpSchedule);
							tmp.setClassroom(room);
							tmp.setDay(allotedDay);
							tmp.setStartTime(startTime);
							tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
							return tmp;
						}
					}
				}		
			}
		return null;
	}
	
	/*
	 * Will schedule a given hour NOT in specific day
	 * */
	private ScheduleDetail allotOneNotSpecificDaySched(double hours, Classroom room, Day allotedDay) {
		EnumMap<Day,List<ScheduleDetail>> schedMapPerRoom;
		
		boolean hasMaxHourAlloted = false;
			List<ScheduleDetail> roomScheduleForGivenDay = SchedulerHelper.getAllSchedulePerRoom(allSchedule, room.getId());
			schedMapPerRoom = SchedulerHelper.getSchedulePerDay(roomScheduleForGivenDay);
			if(schedMapPerRoom != null) {
				Set<Day> keys = schedMapPerRoom.keySet();
				for(Day d : keys) {
					if(d != allotedDay) { 
						List<ScheduleDetail> currentDay = schedMapPerRoom.get(d); 
						if(schedMapPerDay.containsKey(d)) {
							hasMaxHourAlloted = SchedulerHelper.findMaxCourseHourPerDay(schedMapPerDay.get(d), this.course.getId());
						}
						if(!hasMaxHourAlloted) {
							LocalTime startTime = SchedulerHelper.findNextAvailableSchedulePerClassroom(currentDay, (int)(hours*60));
							if(startTime != null) {
								ScheduleDetail tmp = new ScheduleDetail();
								tmp.setSchedule(tmpSchedule);
								tmp.setClassroom(room);
								tmp.setDay(d);
								tmp.setStartTime(startTime);
								tmp.setEndTime(startTime.plus((int)(hours*60), ChronoUnit.MINUTES));
								return tmp;
							}
						}
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
}
