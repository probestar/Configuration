/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationTest.java
 * @Package com.probestar.configuration
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 5:11:04 PM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configurationtest;

import java.io.FileInputStream;
import java.util.Properties;

import com.probestar.configuration.Configuration;
import com.probestar.configuration.ConfigurationReciever;
import com.probestar.configuration.ConfigurationSettings;
import com.probestar.configurationtest.model.ClientUpdateRow;
import com.probestar.configurationtest.model.ConfigurationRow;
import com.probestar.configurationtest.model.ConfigurationTable;
import com.probestar.psutils.PSTracer;

public class ConfigurationTest {
	private static PSTracer _tracer = PSTracer.getInstance(ConfigurationTest.class);

	public static void main(String[] args) {
		try {
			Configuration.getInstance().setSettings(loadSettings());
			Configuration.getInstance().initConfiguration(ConfigurationTable.Configuration, ConfigurationRow.class,
					new ConfigurationReciever<ConfigurationRow>() {
						public void onConfigurationReceived(ConfigurationRow row) {
							_tracer.info(row.toString());
						}
					});

			Configuration.getInstance().initConfiguration(ConfigurationTable.ClientUpdate, ClientUpdateRow.class, new ConfigurationReciever<ClientUpdateRow>() {
				public void onConfigurationReceived(ClientUpdateRow row) {
					_tracer.info(row.toString());
				}
			});

			synchronized (ConfigurationTest.class) {
				ConfigurationTest.class.wait();
			}
		} catch (Throwable t) {
			_tracer.error("ConfigurationTest.test error.", t);
		}
	}

	private static ConfigurationSettings loadSettings() throws Throwable {
		Properties p = new Properties();
		p.load(new FileInputStream("ZKSettings.properties"));
		ConfigurationSettings settings = new ConfigurationSettings();
		settings.setServers(p.getProperty("Servers"));
		return settings;
	}
}
