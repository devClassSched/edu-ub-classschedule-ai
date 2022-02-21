package edu.ub.classscheduleai.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="courses")
@Table(name="courses")
public class Course {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="title")
	private String description;
	
	@Column(name="section")
	private String section;
	


	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="domain_value_id")
	private DomainValue category;
	
	@Column(name="lecture_hours")
	private int lectureHours;

	@Column(name="lab_hours")	
	private int labHours;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="lecture_room", nullable=true)
	private Classroom lectureRoom;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="lab_room", nullable=true)	
	private Classroom labRoom;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="semester_id")
	private Semester semester;
	
	public Course() {}
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
	
	public DomainValue getCategory() {
		return category;
	}


	public void setCategory(DomainValue category) {
		this.category = category;
	}


	public int getLectureHours() {
		return lectureHours;
	}


	public void setLectureHours(int lectureHours) {
		this.lectureHours = lectureHours;
	}


	public int getLabHours() {
		return labHours;
	}


	public void setLabHours(int labHours) {
		this.labHours = labHours;
	}


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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalHours() {
		return this.labHours+this.lectureHours;
	}

	public Classroom getLectureRoom() {
		return lectureRoom;
	}

	public void setLectureRoom(Classroom lectureRoom) {
		this.lectureRoom = lectureRoom;
	}

	public Classroom getLabRoom() {
		return labRoom;
	}

	public void setLabRoom(Classroom labRoom) {
		this.labRoom = labRoom;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	
}
