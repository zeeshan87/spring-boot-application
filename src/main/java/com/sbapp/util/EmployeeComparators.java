package com.sbapp.util;

import java.util.Comparator;

import com.sbapp.domainobject.EmployeeDO;

public class EmployeeComparators {
	
	/*
	 * Compare age in ascending order
	 */
	public static final Comparator<EmployeeDO> AGE_ASC_COMPARATOR = Comparator.comparing(
			EmployeeDO::getAge);
	
}
