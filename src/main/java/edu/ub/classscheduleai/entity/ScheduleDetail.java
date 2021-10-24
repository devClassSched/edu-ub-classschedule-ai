package edu.ub.classscheduleai.entity;

import java.time.LocalTime;

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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import edu.ub.classscheduleai.util.Coursetype;
import edu.ub.classscheduleai.util.Day;
import edu.ub.classscheduleai.util.LocalTimeSerializer;

@Entity(name="scheduledetail")
@Table(name="scheduledetail")
public class ScheduleDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="schedule_id")
	private Schedule schedule;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="classroom_id")
	private Classroom classroom;
	
	@Enumerated(EnumType.ORDINAL)
	private Day day;		

	@JsonSerialize(using = LocalTimeSerializer.class)
	@Column(name = "start_time", columnDefinition = "TIME")
	private LocalTime startTime;
	
	@JsonSerialize(using = LocalTimeSerializer.class)
	@Column(name = "end_time", columnDefinition = "TIME")
	private LocalTime endTime;
	

	@Enumerated(EnumType.ORDINAL)
	private Coursetype coursetype;
	
	
	public Coursetype getCoursetype() {
		return coursetype;
	}

	public void setCoursetype(Coursetype coursetype) {
		this.coursetype = coursetype;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public boolean isConflict(ScheduleDetail obj) {
		if(this.getDay() == obj.getDay()) {
			if(this.getStartTime().compareTo(obj.getStartTime()) >= 0 && this.getEndTime().compareTo(obj.getStartTime()) <= 0) {
				return true;
			}
			if(this.getStartTime().compareTo(obj.getEndTime()) >= 0 && this.getEndTime().compareTo(obj.getEndTime()) <= 0) {
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof ScheduleDetail)) return false;
		return id == ((ScheduleDetail)o).getId();
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(this.classroom.getCoursetype())
				.append(this.classroom.getDescription())
				.append(this.day)
				.append(this.startTime)
				.append(this.endTime).toString();
	}
}

