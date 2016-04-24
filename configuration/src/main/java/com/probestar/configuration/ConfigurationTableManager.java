/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationTableManager.java
 * @Package com.probestar.configuration
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 30, 2015 10:23:32 AM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configuration;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfigurationTableManager {
	private static HashMap<String, String> _names = new HashMap<String, String>();
	private static HashMap<String, Class<?>> _classes = new HashMap<String, Class<?>>();

	public static void addTableName(String tableName, Class<?> clazz) {
		_names.put(tableName.toUpperCase(), tableName);
		_classes.put(tableName.toUpperCase(), clazz);
	}

	public static ArrayList<String> getAllTableNames() {
		ArrayList<String> tableNames = new ArrayList<String>();
		for (String tableName : _names.keySet())
			tableNames.add(getStanderTableName(tableName));
		return tableNames;
	}

	public static boolean containTableName(String tableName) {
		return _names.containsKey(tableName.toUpperCase());
	}

	public static String getStanderTableName(String tableName) {
		return _names.get(tableName.toUpperCase());
	}

	public static Class<?> getClass(String tableName) {
		return _classes.get(tableName.toUpperCase());
	}
}
