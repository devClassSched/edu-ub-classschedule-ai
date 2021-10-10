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

@Entity(name="schedule")
@Table(name="schedule")
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="semester_id")
	private Semester semester;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="professor_id")
	private User professor;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="course_id")
	private Course course;
	
	public Schedule() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public User getProfessor() {
		return professor;
	}

	public void setProfessor(User professor) {
		this.professor = professor;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}
