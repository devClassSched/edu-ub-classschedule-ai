package edu.ub.classscheduleai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.User;
import edu.ub.classscheduleai.repository.UserRepository;
import edu.ub.classscheduleai.util.Role;

@Service
public class UserImpl implements BaseScheduleService<User> {

	@Autowired
	UserRepository repo;
	
	public List<User> getAllProf() {
		return repo.findAllProf(Role.PROFESSOR);
	}
	@Override
	public List<User> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<User> getById(int id) {
		return repo.findById(id);
	}

	@Override
	public User save(User obj) {
		return repo.save(obj);
	}

}
