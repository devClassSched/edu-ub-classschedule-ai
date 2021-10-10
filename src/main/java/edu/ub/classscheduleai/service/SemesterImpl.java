package edu.ub.classscheduleai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.Semester;
import edu.ub.classscheduleai.entity.User;
import edu.ub.classscheduleai.repository.SemesterRepository;

@Service
public class SemesterImpl implements BaseScheduleService<Semester> {

	@Autowired
	SemesterRepository repo;

	public List<Semester> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Semester> getById(int id) {
		return repo.findById(id);
	}

	@Override
	public Semester save(Semester obj) {
		return repo.save(obj);
	}

	public List<Semester> getAllNoSched(){
		return repo.findAllNotInScheduler();
	}
}

