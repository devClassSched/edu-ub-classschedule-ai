package edu.ub.classscheduleai.service;

import java.util.List;
import java.util.Optional;

import edu.ub.classscheduleai.entity.DomainValue;

public interface DomainValueService {

	List<DomainValue> getAllDomainValue();
	List<DomainValue> getAllDomainValueWithObject();
	List<DomainValue> getAllDomainValueByDomainObjectId(int domainObjectId);
	Optional<DomainValue> getDomainValue(int domainValueId);
	
	
}
