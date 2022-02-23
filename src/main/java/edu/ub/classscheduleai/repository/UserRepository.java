package edu.ub.classscheduleai.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ub.classscheduleai.entity.Course;
import edu.ub.classscheduleai.entity.ScheduleDetail;
import edu.ub.classscheduleai.entity.User;
import edu.ub.classscheduleai.util.Role;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	@Query("SELECT ai FROM user ai WHERE ai.role =:role")
	List<User> findAllProf(Role role);
	
	@Query("SELECT s FROM user s join schedule b on  b.professor= s where s.id = :userId")
	List<User> findIfCanDelete(int userId);
}
