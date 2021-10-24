package edu.ub.classscheduleai.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.ub.classscheduleai.util.Status;

@Entity(name="scheduleprocess")
@Table(name="scheduleprocess")
public class ScheduleProcess {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Enumerated(EnumType.ORDINAL)
	private Status status;
	
	@Column(name="totalcourses")
	private int totalCourses;
	
	@Column(name="completedcourses")
	private int completedCourses;
	
	@Column(name="coursenotscheduled")
	private int courseNotCompleted;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="semester_id")
	private Semester sem;

	public Semester getSem() {
		return sem;
	}

	public void setSem(Semester sem) {
		this.sem = sem;
	}

	public ScheduleProcess() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getTotalCourses() {
		return totalCourses;
	}

	public void setTotalCourses(int totalCourses) {
		this.totalCourses = totalCourses;
	}

	public int getCompletedCourses() {
		return completedCourses;
	}

	public void setCompletedCourses(int completedCourses) {
		this.completedCourses = completedCourses;
	}

	public int getCourseNotCompleted() {
		return courseNotCompleted;
	}

	public void setCourseNotCompleted(int courseNotCompleted) {
		this.courseNotCompleted = courseNotCompleted;
	}
	
	public void addCourseCompleted(int i) {
		this.completedCourses += i;
		this.courseNotCompleted -= i;
	}
	
}
