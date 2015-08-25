/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title QuitHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 31, 2015 2:36:39 PM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configurationtools.handler;

import java.util.ArrayList;

import com.probestar.configurationtools.CTResult;
import com.probestar.configurationtools.CTResultType;

public class QuitHandler extends CTHandler {

	public ArrayList<String> getAilas() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("exit");
		list.add("bye");
		return list;
	}
	
	public String getCommand() {
		return "quit";
	}

	public CTResult handle(String[] paramters) throws Throwable {
		return new CTResult(CTResultType.Quit, "See u...");
	}

}
