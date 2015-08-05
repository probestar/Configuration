/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationTest.java
 * @Package com.allstar.configuration
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 5:11:04 PM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configurationtest;

import java.io.FileInputStream;
import java.util.Properties;

import com.allstar.configuration.Configuration;
import com.allstar.configuration.ConfigurationReciever;
import com.allstar.configuration.ConfigurationSettings;
import com.allstar.configurationtest.model.ClientUpdateRow;
import com.allstar.configurationtest.model.ConfigurationRow;
import com.allstar.configurationtest.model.ConfigurationTable;
import com.allstar.psutils.CinTracer;

public class ConfigurationTest {
	private static CinTracer _tracer = CinTracer.getInstance(ConfigurationTest.class);

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
