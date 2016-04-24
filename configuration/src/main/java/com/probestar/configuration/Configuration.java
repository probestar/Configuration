/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title Configuration.java
 * @Package com.probestar.configuration
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 4:56:44 PM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configuration;

import java.util.HashMap;
import java.util.Map.Entry;

import com.probestar.configuration.codec.ConfigurationDecoder;
import com.probestar.configuration.model.ConfigurationData;
import com.probestar.configuration.zk.ZKBridge;
import com.probestar.configuration.zk.ZKBridgeListener;
import com.probestar.psutils.PSTracer;

public class Configuration implements ZKBridgeListener {
	private static PSTracer _tracer = PSTracer.getInstance(Configuration.class);
	private static Configuration _instance;

	private ConfigurationSettings _settings;
	private HashMap<String, ZKBridge> _bridges;
	private HashMap<String, ConfigurationReciever<? extends ConfigurationData>> _receivers;

	static {
		try {
			_instance = new Configuration();
		} catch (Throwable t) {
			_tracer.error("Configuration error.", t);
		}
	}

	public static Configuration getInstance() {
		return _instance;
	}

	private Configuration() {
		_bridges = new HashMap<String, ZKBridge>();
		_receivers = new HashMap<String, ConfigurationReciever<? extends ConfigurationData>>();
	}

	public void setSettings(ConfigurationSettings settings) {
		_settings = settings;
	}

	public boolean checkRootPath() {
		ZKBridge bridge = new ZKBridge(_settings.getAddress(), "");
		boolean exist = bridge.exists(_settings.getPath());
		bridge.close();
		return exist;
	}

	public void createRootPath(String userName, String password) throws Throwable {
		ZKBridge bridge = new ZKBridge(_settings.getAddress(), "", userName, password);
		bridge.create(_settings.getPath(), "probestar@qq.com".getBytes());
		bridge.close();
	}

	public void initConfiguration(String tableName, Class<?> clazz, ConfigurationReciever<? extends ConfigurationData> receiver) {
		if (tableName == null)
			throw new NullPointerException("moduleName");
		if (receiver == null)
			throw new NullPointerException("receiver");

		try {
			ConfigurationTableManager.addTableName(tableName, clazz);
			_receivers.put(tableName, receiver);
			getBridge(tableName);
		} catch (Throwable t) {
			_tracer.error("Configuration.initConfigurationData error.", t);
		}
	}

	public ZKBridge getBridge(String tableNames) {
		return getBridge(tableNames, null, null);
	}

	public synchronized ZKBridge getBridge(String tableName, String userName, String password) {
		ZKBridge bridge = _bridges.get(tableName);
		if (bridge == null) {
			if (tableName.equalsIgnoreCase(""))
				bridge = new ZKBridge(_settings.getServers(), "", userName, password);
			else
				bridge = new ZKBridge(_settings.getServers(), tableName, userName, password);
			_bridges.put(tableName, bridge);
			bridge.register(this);
		}
		return bridge;
	}

	public void dispose() {
		for (Entry<String, ZKBridge> entry : _bridges.entrySet())
			entry.getValue().close();
		_bridges.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onNodeChanged(String tableName, String key, byte[] data) {
		ConfigurationData row = (ConfigurationData) ConfigurationDecoder.decode(data, ConfigurationTableManager.getClass(tableName));
		if (row == null)
			return;
		ConfigurationReciever<ConfigurationData> receiver = (ConfigurationReciever<ConfigurationData>) _receivers.get(tableName);
		if (receiver == null)
			return;
		receiver.onConfigurationReceived(key, row);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onNodeRemoved(String tableName, String key) {
		ConfigurationReciever<ConfigurationData> receiver = (ConfigurationReciever<ConfigurationData>) _receivers.get(tableName);
		if (receiver == null)
			return;
		receiver.onConfigurationRemoved(key);
	}
}