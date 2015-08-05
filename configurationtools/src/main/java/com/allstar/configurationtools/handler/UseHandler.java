/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title UseHandler.java
 * @Package com.allstar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 11:35:28 AM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtools.handler;

import java.util.ArrayList;

import com.allstar.configuration.ConfigurationTableManager;
import com.allstar.configurationtools.CTResult;
import com.allstar.configurationtools.CTSession;

public class UseHandler extends CTHandler {

	public ArrayList<String> getAilas() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("switch");
		return list;
	}

	public String getCommand() {
		return "use";
	}

	public CTResult handle(String[] paramters) {
		String table = paramters[1];
		if (!ConfigurationTableManager.containTableName(table))
			return new CTResult(table + " is not supported.");
		CTSession.getInstance().setCurrentTable(table);
		return new CTResult("CurrentTable is " + CTSession.getInstance().getCurrentTable() + ".\r\nOK.");
	}

}