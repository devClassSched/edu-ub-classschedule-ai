package edu.ub.classscheduleai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.Classroom;
import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.entity.ScheduleProcess;
import edu.ub.classscheduleai.entity.Semester;
import edu.ub.classscheduleai.repository.ScheduleProcessRepository;
import edu.ub.classscheduleai.util.Status;

@Service
public class ScheduleProcessImpl implements BaseScheduleService<ScheduleProcess>{

	@Autowired
	ScheduleProcessRepository mainRepo;
	
	@Override
	public List<ScheduleProcess> getAll() {
		return mainRepo.findAll();
	}

	@Override
	public Optional<ScheduleProcess> getById(int id) {
		return mainRepo.findById(id);
	}

	@Override
	public ScheduleProcess save(ScheduleProcess obj) {
		return mainRepo.save(obj);
	}
	
	public ScheduleProcess saveFlush(ScheduleProcess obj) {
		return mainRepo.saveAndFlush(obj);
	}
	
	public ScheduleProcess findAllBySemester(Semester semester){
		List<Status> list = new ArrayList<>();
		list.add(Status.CREATED);
		list.add(Status.STARTED);
		
		return mainRepo.findOpenProcess(list);
	}
	
	
	public boolean findExistingProcess() {
		List<Status> list = new ArrayList<>();
		list.add(Status.CREATED);
		list.add(Status.STARTED);
		ScheduleProcess sp = mainRepo.findOpenProcess(list);
		if(sp != null) {
			return true;
		}
		return false;
	}

}
