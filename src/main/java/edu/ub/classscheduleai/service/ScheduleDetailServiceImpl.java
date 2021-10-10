package edu.ub.classscheduleai.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.Classroom;
import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.Schedule;
import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.entity.Semester;
import edu.ub.classscheduleai.entity.User;
import edu.ub.classscheduleai.repository.ClassroomRepository;
import edu.ub.classscheduleai.repository.ScheduleDetailsRepository;

@Service
public class ScheduleDetailServiceImpl implements BaseScheduleService<ScheduleDetail>{

	@Autowired
	ScheduleDetailsRepository mainRepo;
	
		
	@Autowired
	ClassroomRepository classRepo;
	
	public List<ScheduleDetail> findBySchedule(Schedule obj) {
		return null;
		//return mainRepo.findAllByScheduledId(obj.getId());
	}

	
	public List<ScheduleDetail> findAllBySemester(Semester semester){
		return mainRepo.findAllBySemesterId(semester.getId());
	}
	
	public List<ScheduleDetail> findAllBySemesterAndProfessor(Semester semester, User user){
		return mainRepo.findAllBySemesterIdAndProfessorId(semester.getId(),user.getId());
	}
	
	public List<ScheduleDetail> findAllBySemesterAndClassroom(Semester semester, Classroom room){
		return mainRepo.findAllBySemesterIdAndClassroomId(semester.getId(), room.getId());
	}
	
	public List<ScheduleDetail> findAllBySemesterAndCourse(Semester semester, Course course){
		return mainRepo.findAllBySemesterIdAndCourseId(semester.getId(), course.getId());
	}
	
	public List<Long> findSchedIdPerSemester(Semester semester){
		return mainRepo.findAllDistinctSchedIdBySemesterId(semester.getId());
	}
	
	@Transactional
	public List<ScheduleDetail> saveAll(List<ScheduleDetail> list) {
		return mainRepo.saveAll(list);
	}
	
	@Transactional
	public void deleteByScheduleID(List<Long> id) {
		//mainRepo.deleteByScheduleID(id);
	}
	
	@Override
	public List<ScheduleDetail> getAll() {
		return mainRepo.findAll();
	}
	
	@Override
	public Optional<ScheduleDetail> getById(int id) {
		return mainRepo.findById(id);
	}

	@Override
	public ScheduleDetail save(ScheduleDetail obj) {
		return mainRepo.save(obj);
	}

}
