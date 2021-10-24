package edu.ub.classscheduleai.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Filter {

	private Semester sem;
	private User prof;
	private Classroom room;
	private int type;
	
	public Filter() {}

	public Semester getSem() {
		return sem;
	}

	public void setSem(Semester sem) {
		this.sem = sem;
	}

	public User getProf() {
		return prof;
	}

	public void setProf(User prof) {
		this.prof = prof;
	}

	public Classroom getRoom() {
		return room;
	}

	public void setRoom(Classroom room) {
		this.room = room;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(this.room.getDescription())
				.append(this.prof.getName())
				.append(this.sem.getDescription())
				.append(this.type).toString();
		
	}
}
