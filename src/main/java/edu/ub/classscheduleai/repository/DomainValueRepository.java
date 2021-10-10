package edu.ub.classscheduleai.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.ub.classscheduleai.entity.DomainValue;


public interface DomainValueRepository extends JpaRepository<DomainValue,Integer>{

	List<DomainValue> findAllByDomainObjectId(int objectId);
	List<DomainValue> findAll();
	DomainValue findByShortDescription(String desc);
	List<DomainValue> findByShortDescriptionIn(Collection<String> days);
}
