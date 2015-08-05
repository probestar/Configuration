/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationEncoder.java
 * @Package com.allstar.configuration.codec
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 30, 2015 10:06:39 AM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configuration.codec;

import java.nio.charset.Charset;

import com.allstar.configuration.model.ConfigurationData;
import com.google.gson.Gson;

public class ConfigurationEncoder {
	private static Gson _gson = new Gson();

	public static String encode(ConfigurationData data) {
		return _gson.toJson(data, data.getClass());
	}

	public static byte[] encode1(ConfigurationData data) {
		return encode(data).getBytes(Charset.forName("utf-8"));
	}
}
