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

import org.hibernate.annotations.GenericGenerator;

import edu.ub.classscheduleai.util.Coursetype;

@Entity(name="classroom")
@Table(name="classroom")
public class Classroom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY)	
	@JoinTable(
			name="roomtype",
			joinColumns=@JoinColumn(name="classroom_id"),
			inverseJoinColumns=@JoinColumn(name="domainvalue_id"))
	private Set<DomainValue> category;
	
	@Column(name="description")
	private String description;
	
	@Enumerated(EnumType.ORDINAL)
	private Coursetype coursetype;
	
	public Classroom() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public Set<DomainValue> getCategory() {
		return category;
	}

	public void setCategory(Set<DomainValue> category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Coursetype getCoursetype() {
		return coursetype;
	}

	public void setCoursetype(Coursetype coursetype) {
		this.coursetype = coursetype;
	}
}
