/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title AddHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 10:54:08 AM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configurationtools.handler;

import java.util.ArrayList;

import com.probestar.configurationtools.CTResult;

public class AddHandler extends CTHandler {

	public ArrayList<String> getAilas() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("insert");
		list.add("create");
		return list;
	}

	public String getCommand() {
		return "add";
	}

	public CTResult handle(String[] paramters) throws Throwable {
		String key = paramters[1];
		StringBuilder sb = new StringBuilder();
		for (int i = 2; i < paramters.length; i++) {
			sb.append(paramters[i]);
			sb.append(" ");
		}
		if (sb.length() > 1)
			sb.setLength(sb.length() - 1);
		getBridge().create(key, sb.toString().getBytes("utf-8"));
		return new CTResult("Key is added. " + key + "\r\nOK.");
	}
}