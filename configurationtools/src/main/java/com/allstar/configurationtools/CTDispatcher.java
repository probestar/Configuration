/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title CTDispatcher.java
 * @Package com.allstar.configurationtools
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 27, 2015 3:57:23 PM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import com.allstar.configurationtools.handler.CTHandler;
import com.allstar.psutils.CinTracer;

public class CTDispatcher {
	private static CinTracer _tracer = CinTracer.getInstance(CTDispatcher.class);
	private static CTDispatcher _instance;

	private HashMap<String, CTHandler> _handlers;

	static {
		try {
			_instance = new CTDispatcher();
		} catch (Throwable t) {
			_tracer.error("CTDispatcher.static error.", t);
		}
	}

	public static CTDispatcher getInstance() {
		return _instance;
	}

	private CTDispatcher() {
		_handlers = new HashMap<String, CTHandler>();
	}

	public void register(CTHandler handler) {
		String cmd = handler.getCommand();
		if (_handlers.containsKey(cmd)) {
			_tracer.error("Duplicate handler detected. " + handler);
			return;
		}

		_handlers.put(cmd.toUpperCase(), handler);

		ArrayList<String> list = handler.getAilas();
		if (list == null)
			return;
		for (String ailias : list) {
			if (_handlers.containsKey(ailias)) {
				_tracer.error("Duplicate ailias detected. " + ailias);
				continue;
			}
			_handlers.put(ailias.toUpperCase(), handler);
		}
	}

	public CTResult dispatch(String cmd) {
		CTResult ret = null;
		try {
			String[] temp = cmd.split(" ");
			CTHandler handler = _handlers.get(temp[0].toUpperCase());
			if (handler == null) {
				_tracer.info("Can't find Handler. Command line: " + cmd);
				return new CTResult(String.format("Can Not find [%s], please type help for more information.", temp[0]));
			}

			handler.startWatch();
			ret = handler.handle(temp);
			ret.appendContext(" [Time eclipse: " + handler.stopWatch() / 1000.0f + "s.]");
		} catch (Throwable t) {
			_tracer.error("CTDispatcher.dispatch error.", t);
			ret = new CTResult("Error. Please check logs.");
		}
		return ret;
	}

	public String help(String cmd) {
		String ret = null;
		try {
			CTHandler handler = _handlers.get(cmd.toUpperCase());
			if (handler == null) {
				_tracer.info("Can't find Handler. Command line: " + cmd);
				return cmd + " is not a cmd, please type help for more information.";
			}
			ret = handler.getHelp();
		} catch (Throwable t) {
			_tracer.error("CTDispatcher.dispatch error.", t);
			ret = "Error\r\n" + t.getMessage();
		}
		return ret;
	}

	public String commandList() {
		ArrayList<CTHandler> list = new ArrayList<CTHandler>();
		for (Entry<String, CTHandler> entry : _handlers.entrySet()) {
			if (list.contains(entry.getValue()))
				continue;
			list.add(entry.getValue());
		}
		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (CTHandler handler : list) {
			sb.append(handler.getCommand());
			if (handler.getAilas() == null) {
				sb.append("\r\n");
				continue;
			}
			sb.append(" (");
			for (String ailas : handler.getAilas()) {
				sb.append(ailas);
				sb.append(", ");
			}
			sb.setLength(sb.length() - 2);
			sb.append(")\r\n");
		}
		return sb.toString();
	}
}
