package edu.ub.classscheduleai.service;

import java.util.List;
import java.util.Optional;


public interface BaseScheduleService <T>{

	List<T> getAll();
	Optional<T> getById(int id);
	T save(T obj);
	
	
}
