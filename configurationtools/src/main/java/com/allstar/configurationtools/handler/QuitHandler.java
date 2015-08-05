/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title QuitHandler.java
 * @Package com.allstar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 31, 2015 2:36:39 PM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtools.handler;

import java.util.ArrayList;

import com.allstar.configurationtools.CTResult;
import com.allstar.configurationtools.CTResultType;

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
