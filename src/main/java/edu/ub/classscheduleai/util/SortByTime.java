package edu.ub.classscheduleai.util;

import java.util.Comparator;

import edu.ub.classscheduleai.entity.ScheduleDetail;

public class SortByTime implements Comparator<ScheduleDetail>{

	@Override
	public int compare(ScheduleDetail o1, ScheduleDetail o2) {
		return o1.getStartTime().compareTo(o2.getStartTime());
	}

}
