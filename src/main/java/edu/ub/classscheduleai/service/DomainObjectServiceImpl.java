package edu.ub.classscheduleai.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.DomainObject;
import edu.ub.classscheduleai.repository.DomainObjectRepository;


@Service
public class DomainObjectServiceImpl implements BaseScheduleService<DomainObject> {

	@Autowired
	private DomainObjectRepository repo;
	
	public Optional<DomainObject> getByDescription(String description) {
		return repo.findByDescription(description);
	}

	@Override
	public List<DomainObject> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<DomainObject> getById(int id) {
		return repo.findById(id);
	}

	@Override
	public DomainObject save(DomainObject obj) {
		return repo.save(obj);
	}
	
	

}
