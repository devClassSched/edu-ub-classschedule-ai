package edu.ub.classscheduleai.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import edu.ub.classscheduleai.util.Role;

@Entity(name="user")
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY)	
	@JoinTable(
			name="usertype",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="domainvalue_id"))
	private Set<DomainValue> specialization;
	
	@Enumerated(EnumType.ORDINAL)
	private Role role;
	
	@Column(name="allocated_hours")
	private int allocatedHours;
	
	@Transient
	private int currentcount = 0;
	

	public User() {}
	
	
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCurrentcount(int currentcount) {
		this.currentcount = currentcount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public Set<DomainValue> getSpecialization() {
		return specialization;
	}


	public void setSpecialization(Set<DomainValue> specialization) {
		this.specialization = specialization;
	}


	public int getAllocatedHours() {
		return allocatedHours;
	}


	public void setAllocatedHours(int allocatedHours) {
		this.allocatedHours = allocatedHours;
	}
	
	public int getCurrentcount() {
		return currentcount;
	}

	public void addCurrentcount(int add) {
		this.currentcount += add;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", specialization=" + specialization
				+ ", role=" + role + ", allocatedHours=" + allocatedHours + ", currentcount=" + currentcount + "]";
	}
	
}
