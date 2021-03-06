package edu.ub.classscheduleai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.Classroom;
import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.Semester;
import edu.ub.classscheduleai.repository.CourseRepository;

@Service
public class CourseImpl implements BaseScheduleService<Course> {

	@Autowired
	CourseRepository repo;
	
	public List<Course> findAllCourseForSemester(Semester sem) {
		return repo.findAllCourseForSemester(sem);
	}
	
	public List<Course> findIfCanDelete(Course c) {
		return repo.findIfCanDelete(c.getId());
	}
	
	public void courseDelete(Course c) {
		repo.deleteById(c.getId());
	}
	@Override
	public List<Course> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Course> getById(int id) {
		return repo.findById(id);
	}

	@Override
	public Course save(Course obj) {
		return repo.save(obj);
	}
	
	public void generate() {
		 repo.deleteAll();
	}

}
