package edu.ub.classscheduleai.util;

import java.util.Comparator;

import edu.ub.classscheduleai.entity.User;



public class SortByAllotedSched implements Comparator<User>{

	@Override
	public int compare(User o1, User o2) {
		return Integer.compare(o1.getCurrentcount(), o2.getCurrentcount());
	}
}
