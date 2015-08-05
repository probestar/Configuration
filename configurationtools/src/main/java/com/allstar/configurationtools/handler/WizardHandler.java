/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title WizardHandler.java
 * @Package com.allstar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 30, 2015 10:02:25 AM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtools.handler;

import java.util.ArrayList;

import com.allstar.configuration.codec.ConfigurationDecoder;
import com.allstar.configuration.codec.ConfigurationEncoder;
import com.allstar.configuration.model.ConfigurationData;
import com.allstar.configurationtest.model.ConfigurationRow;
import com.allstar.configurationtest.model.ConfigurationTable;
import com.allstar.configurationtools.CTResult;
import com.allstar.configurationtools.CTSession;
import com.allstar.psutils.CinConsole;

public class WizardHandler extends CTHandler {

	public ArrayList<String> getAilas() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("wz");
		return list;
	}

	public String getCommand() {
		return "wizard";
	}

	public CTResult handle(String[] paramters) throws Throwable {
		String key = paramters.length > 1 ? paramters[1] : null;
		System.out.println("Please input the following fileds.");
		byte[] b = null;
		if (key != null)
			b = getBridge().get(key);

		ConfigurationData newData = null;
		switch (CTSession.getInstance().getCurrentTable()) {
		case ConfigurationTable.Configuration:
			newData = configurationWizard(b);
			break;
		default:
			break;
		}

		if (key != null)
			getBridge().delete(key);
		getBridge().create(newData.getKey(), ConfigurationEncoder.encode1(newData));
		return new CTResult("OK.");
	}

	private ConfigurationRow configurationWizard(byte[] data) {
		ConfigurationRow row = data == null ? new ConfigurationRow() : ConfigurationDecoder.decode(data, ConfigurationRow.class);

		row.setKeyword(inputItem("Keyword", row.getKeyword()));
		row.setValue(inputItem("Value", row.getValue()));
		row.setModuleName(inputItem("ModuleName", row.getModuleName()));
		row.setServiceName(inputItem("ServiceName", row.getServiceName()));
		row.setComputerName(inputItem("ComputerName", row.getComputerName()));
		row.setComments(inputItem("Comments", row.getComments()));
		row.setEnable(Boolean.parseBoolean(inputItem("Enable", row.getEnable() ? "true" : "false")));
		return row;
	}

	private String inputItem(String key, String value) {
		System.out.print(String.format("%s[%s]: ", key, value));
		String input = CinConsole.readLine();
		if (input.equalsIgnoreCase(""))
			return value;
		return input;
	}

}
