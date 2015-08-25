/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title CTSession.java
 * @Package com.probestar.configurationtools
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 11:37:50 AM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configurationtools;

import com.probestar.configuration.ConfigurationTableManager;
import com.probestar.configurationtest.model.ConfigurationTable;
import com.probestar.psutils.PSTracer;

public class CTSession {
	private static PSTracer _tracer = PSTracer.getInstance(CTSession.class);
	private static CTSession _instance;

	private String _currentTable;
	private String _name;
	private String _pwd;

	static {
		try {
			_instance = new CTSession();
		} catch (Throwable t) {
			_tracer.error("CTSession.static error.", t);
		}
	}

	public static CTSession getInstance() {
		return _instance;
	}

	private CTSession() {
		_currentTable = ConfigurationTable.Configuration;
	}

	public void setCurrentTable(String table) {
		_currentTable = ConfigurationTableManager.getStanderTableName(table);
	}

	public String getCurrentTable() {
		return _currentTable;
	}

	public void setOperatorName(String name) {
		_name = name;
	}

	public String getOperatorName() {
		return _name;
	}

	public void setPassword(String pwd) {
		_pwd = pwd;
	}

	public String getPassword() {
		return _pwd;
	}

	public String getPrefix() {
		return String.format("%s@%s", getOperatorName(), getCurrentTable());
	}
}
