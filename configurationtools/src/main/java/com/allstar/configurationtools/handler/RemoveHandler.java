/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title RemoveHandler.java
 * @Package com.allstar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 11:33:46 AM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtools.handler;

import java.util.ArrayList;

import com.allstar.configurationtools.CTResult;

public class RemoveHandler extends CTHandler {

	public ArrayList<String> getAilas() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("delete");
		list.add("rm");
		return list;
	}

	public String getCommand() {
		return "remove";
	}

	public CTResult handle(String[] paramters) throws Throwable {
		String key = paramters[1];
		getBridge().delete(key);
		return new CTResult("OK.");
	}
}