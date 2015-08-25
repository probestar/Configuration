/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ClearHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 11:44:27 AM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configurationtools.handler;

import java.util.List;

import com.probestar.configurationtools.CTResult;
import com.probestar.psutils.PSConsole;

public class ClearHandler extends CTHandler {

	public String getCommand() {
		return "clear";
	}

	public CTResult handle(String[] paramters) throws Throwable {
		System.out.print("How dare u do this?\r\n[y/n] ");
		if (!PSConsole.readLine().equalsIgnoreCase("Y"))
			return new CTResult("Cancelled.");
		List<String> list = getBridge().list();
		for (String s : list)
			getBridge().delete(s);
		return new CTResult(list.size() + " itme(s) has been removed." + "\r\nOK.");
	}
}
