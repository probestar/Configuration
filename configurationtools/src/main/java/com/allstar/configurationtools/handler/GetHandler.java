/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title GetHandler.java
 * @Package com.allstar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 2:20:54 PM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtools.handler;

import java.nio.charset.Charset;

import com.allstar.configurationtools.CTResult;

public class GetHandler extends CTHandler {

	public String getCommand() {
		return "get";
	}

	public CTResult handle(String[] paramters) throws Throwable {
		byte[] b = getBridge().get(paramters[1]);
		String s = new String(b, Charset.forName("utf-8"));
		return new CTResult(s + "\r\nOK.");
	}
}