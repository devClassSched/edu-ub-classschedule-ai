package edu.ub.classscheduleai.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="domainvalue")
@Table(name="domainvalue")
public class DomainValue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column(name="short_description")
	private String shortDescription;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne(targetEntity=DomainObject.class,fetch = FetchType.LAZY)	
	@JoinColumn(name="domain_object_id")
	private DomainObject domainObject;
	
	/*@ManyToMany(fetch = FetchType.LAZY)	
	private Set<User> users;
	
	@ManyToMany(fetch = FetchType.LAZY)	
	private Set<Classroom> rooms;
	
	public Set<Classroom> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Classroom> rooms) {
		this.rooms = rooms;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	*/
	public DomainValue() {}
	
	public DomainValue(DomainObject c) {
		this.domainObject = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof DomainValue)) return false;
		return id != null && id.equals(((DomainValue) o).getId());
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public DomainObject getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(DomainObject domainObject) {
		this.domainObject = domainObject;
	}
	


}
