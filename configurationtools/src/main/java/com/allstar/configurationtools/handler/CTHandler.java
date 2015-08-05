/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title CTHandler.java
 * @Package com.allstar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 27, 2015 4:10:45 PM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtools.handler;

import java.util.ArrayList;

import com.allstar.configuration.Configuration;
import com.allstar.configuration.zk.ZKBridge;
import com.allstar.configurationtools.CTHelpSettings;
import com.allstar.configurationtools.CTResult;
import com.allstar.configurationtools.CTSession;

public abstract class CTHandler implements Comparable<CTHandler> {

	private long _watch;

	public abstract String getCommand();

	public abstract CTResult handle(String[] paramters) throws Throwable;

	public ArrayList<String> getAilas() {
		return null;
	}

	public ZKBridge getBridge() {
		String name = CTSession.getInstance().getOperatorName();
		String pwd = CTSession.getInstance().getPassword();
		return Configuration.getInstance().getBridge(CTSession.getInstance().getCurrentTable(), name, pwd);
	}

	public String getHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("Command: ");
		sb.append(getCommand());
		sb.append("\r\n");
		sb.append(CTHelpSettings.getInstance().getHelp(getCommand()));
		sb.append("\r\nAilas: ");
		if (getAilas() == null) {
			sb.append("nil");
		} else {
			for (int i = 0; i < getAilas().size(); i++) {
				sb.append(getAilas().get(i));
				if (i < getAilas().size() - 1)
					sb.append(", ");
			}
		}
		return sb.toString();
	}

	public void startWatch() {
		_watch = System.currentTimeMillis();
	}

	public long stopWatch() {
		return System.currentTimeMillis() - _watch;
	}

	public String toString() {
		return "CTHandler: " + getCommand();
	}

	public int compareTo(CTHandler o) {
		return getCommand().compareTo(o.getCommand());
	}
}
