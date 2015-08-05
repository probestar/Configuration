/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationSettings.java
 * @Package com.allstar.configuration
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 5:04:10 PM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtest;

import java.io.FileInputStream;
import java.util.Properties;

import com.allstar.psutils.CinTracer;

public class ConfigurationSettings {
	private static CinTracer _tracer = CinTracer.getInstance(ConfigurationSettings.class);

	private String _servers;

	public ConfigurationSettings() {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("ZKSettings.properties"));

			_servers = p.getProperty("Servers");
		} catch (Throwable t) {
			_tracer.error("ZKSettings.ZKSettings error.", t);
		}
	}

	public void setServers(String servers) {
		_servers = servers;
	}

	public String getServers() {
		return _servers;
	}

	public String getAddress() {
		return getServers().substring(0, getServers().indexOf('/'));
	}

	public String getPath() {
		return getServers().substring(getServers().indexOf('/'));
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Servers: ");
		sb.append(getServers());
		sb.append("; Addresss: ");
		sb.append(getAddress());
		sb.append("; Path: ");
		sb.append(getPath());
		return sb.toString();
	}
}
