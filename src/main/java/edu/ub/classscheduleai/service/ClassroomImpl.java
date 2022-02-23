package edu.ub.classscheduleai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.Classroom;
import edu.ub.classscheduleai.entity.DomainValue;
import edu.ub.classscheduleai.entity.User;
import edu.ub.classscheduleai.repository.ClassroomRepository;
import edu.ub.classscheduleai.util.Coursetype;

@Service
public class ClassroomImpl implements BaseScheduleService<Classroom>{

	@Autowired
	ClassroomRepository repo;
	
	public List<Classroom> findAllClassroomByCategory(DomainValue category){
		if(category == null)
			return null;
		return repo.findAllByCategory(category.getId());
	}
	
	public List<Classroom> getByCategoryByClassroomType(List<Classroom> rooms, DomainValue category, Coursetype type){
		List<Classroom> filteredList =  rooms.stream().filter(
				e -> e.getCoursetype() == type)
				.collect(Collectors.toList());
		
		List<Classroom> finalList = new ArrayList<>();
		for(Classroom fList : filteredList) {
			Set<DomainValue> catList = fList.getCategory();
			boolean test = catList.stream().anyMatch(e -> e.equals(category));
			if(test) {
				finalList.add(fList);				
			}
				
		}
		return finalList;
	}
	public List<Classroom> findAllClassroomByCategoryAndType(DomainValue category,Coursetype type){		
		return repo.findAllByCategoryAndCoursetype(category.getId(),type);
	}
	
	public List<Classroom> findAllClassroomByType(Coursetype type){		
		return repo.findAllByCoursetype(type);
	}

	public List<Classroom> findIfCanDelete(Classroom c) {
		return repo.findIfCanDelete(c.getId());
	}
	
	public void classroomDelete(Classroom c) {
		repo.deleteById(c.getId());
	}
	@Override
	public List<Classroom> getAll() {
		return repo.findAll();		
	}

	@Override
	public Optional<Classroom> getById(int id) {		
		return repo.findById(id);
	}

	@Override
	public Classroom save(Classroom obj) {
		return repo.save(obj);
	}
	
	
}
