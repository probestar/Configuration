/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title CTSession.java
 * @Package com.allstar.configurationtools
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 11:37:50 AM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtools;

import com.allstar.configuration.ConfigurationTableManager;
import com.allstar.configurationtest.model.ConfigurationTable;
import com.allstar.psutils.CinTracer;

public class CTSession {
	private static CinTracer _tracer = CinTracer.getInstance(CTSession.class);
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
