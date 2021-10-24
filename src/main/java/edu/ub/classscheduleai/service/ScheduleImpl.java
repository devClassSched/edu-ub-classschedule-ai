package edu.ub.classscheduleai.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.Schedule;
import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.entity.Semester;
import edu.ub.classscheduleai.entity.User;
import edu.ub.classscheduleai.repository.CourseRepository;
import edu.ub.classscheduleai.repository.ScheduleRepository;
import edu.ub.classscheduleai.repository.UserRepository;

@Service
public class ScheduleImpl implements BaseScheduleService<Schedule>{

	@Autowired
	ScheduleRepository mainRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	ScheduleDetailServiceImpl sDetailService;
	
	@Autowired
	UserRepository userRepo;
	
	public List<Schedule> findAllBySemester(Semester obj) {
		return mainRepo.findAllBySemester(obj);
	}

	public List<Schedule> findAllProfessorPerSem(User profObj,Semester semObj) {
		return mainRepo.findAllBySemesterAndProfessorId(semObj.getId(),profObj.getId());
	}
	
	public Optional<Schedule> findByCoursePerSem(Course obj, Semester semObj) {
		return mainRepo.findBySemesterAndCourseId(semObj.getId(),obj.getId());
	}
	@Override
	public List<Schedule> getAll() {
		return mainRepo.findAll();
	}
	
	@Transactional
	public void delete(Semester obj) {
		try {
			
			List<Schedule> schedList = mainRepo.findAllBySemester(obj);			
			if(schedList != null && schedList.size()>0) {
				sDetailService.deleteByScheduleID(schedList);
				mainRepo.deleteAll(schedList);
			}			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	@Override
	public Optional<Schedule> getById(int id) {
		return mainRepo.findById(id);
	}

	@Override
	public Schedule save(Schedule obj) {
		return mainRepo.save(obj);
	}

	
	
}
