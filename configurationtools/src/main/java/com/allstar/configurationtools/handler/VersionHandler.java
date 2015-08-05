/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title VersionHandler.java
 * @Package com.allstar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 30, 2015 5:40:50 PM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtools.handler;

import java.util.ArrayList;

import com.allstar.configurationtools.CTResult;

public class VersionHandler extends CTHandler {

	public ArrayList<String> getAilas() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("ver");
		list.add("v");
		return list;
	}

	public String getCommand() {
		return "version";
	}

	public CTResult handle(String[] paramters) throws Throwable {
		return new CTResult("Version: 1.0.0.");
	}

}
