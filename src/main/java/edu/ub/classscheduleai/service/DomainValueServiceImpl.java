package edu.ub.classscheduleai.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ub.classscheduleai.entity.DomainObject;
import edu.ub.classscheduleai.entity.DomainValue;
import edu.ub.classscheduleai.repository.DomainObjectRepository;
import edu.ub.classscheduleai.repository.DomainValueRepository;
import edu.ub.classscheduleai.util.ConstantHelper;


@Service
public class DomainValueServiceImpl implements DomainValueService {
	
	@Autowired
	private DomainValueRepository mainRepo;
	
	@Autowired
	private DomainObjectRepository objectRepo;
	
	@Override
	public List<DomainValue> getAllDomainValue() {		
		return mainRepo.findAll();
	}
	
	public DomainValue getByDescription(String description) {
		return mainRepo.findByShortDescription(description);
	}
		
	public List<DomainValue> getAllDayObject(String[] val){
		List<String> stringList = new ArrayList<>(Arrays.asList(val));
		return mainRepo.findByShortDescriptionIn(stringList);
	}
	@Override
	public List<DomainValue> getAllDomainValueWithObject() {
		List<DomainValue> tmp = mainRepo.findAll();
		List<DomainObject> tmpObj = objectRepo.findAll();
		tmp.forEach(e -> {
			//int objId = e.get
		});
		return tmp;
	}

	@Override
	public List<DomainValue> getAllDomainValueByDomainObjectId(int domainObjectId) {
		
		return mainRepo.findAllByDomainObjectId(domainObjectId);
	}

	@Override
	public Optional<DomainValue> getDomainValue(int domainValueId) {
		
		return mainRepo.findById(domainValueId);
	}
	
	public DomainValue save(DomainValue obj) {
		return mainRepo.save(obj);
	}
}
