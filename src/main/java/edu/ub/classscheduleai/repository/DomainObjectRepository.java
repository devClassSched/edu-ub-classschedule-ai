package edu.ub.classscheduleai.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ub.classscheduleai.entity.DomainObject;



@Transactional
@Repository
public interface DomainObjectRepository extends JpaRepository<DomainObject,Integer>{

	Set<DomainObject> findAllById(int id);
	List<DomainObject> findAll();
	Optional<DomainObject> findByDescription(String description);
}
