/**
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
 */

package com.probestar.configuration;

import com.google.common.base.Preconditions;
import com.probestar.configuration.codec.ConfigurationDecoder;
import com.probestar.configuration.common.ConfigurationReciever;
import com.probestar.configuration.common.ConfigurationTracerFactory;
import com.probestar.configuration.model.ConfigurationData;
import com.probestar.configuration.model.ConfigurationRow;
import com.probestar.configuration.utils.ConfigurationSettings;
import com.probestar.configuration.utils.ConfigurationTracerHelper;
import com.probestar.configuration.zk.ZKBridge;
import com.probestar.configuration.zk.ZKBridgeListener;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Configuration implements ZKBridgeListener {
    private static ConfigurationTracerHelper _tracer = ConfigurationTracerHelper.getInstance(Configuration.class);
    private static Configuration _instance;

    private ConfigurationSettings _settings;
    private ZKBridge _bridge;
    private HashMap<String, ConfigurationReciever<? extends ConfigurationData>> _receivers;

    public static void initialize() throws IOException {
        _instance = new Configuration();
    }

    public static Configuration getInstance() {
        return _instance;
    }

    private Configuration() throws IOException {
        ConfigurationSettings.initialize();
        _settings = ConfigurationSettings.getInstance();
        _bridge = new ZKBridge(_settings.getZookeepers());
        _bridge.register(this);
        _bridge.start();
        _receivers = new HashMap<String, ConfigurationReciever<? extends ConfigurationData>>();
    }

    public void registerConfigurationTracerFactory(ConfigurationTracerFactory factory) {
        ConfigurationTracerHelper.registerConfigurationTracerFactory(factory);
    }

    public void initConfiguration(ConfigurationReciever<? extends ConfigurationData> receiver) {
        Preconditions.checkNotNull(receiver, "receiver");

        try {
            String tableName = receiver.getTableName();
            _receivers.put(tableName, receiver);
            _bridge.fireNodesChanged(tableName);
        } catch (Throwable t) {
            _tracer.error("Configuration.initConfigurationData error.", t);
        }
    }

    @Override
    public void onConnected() {
        try {
            _bridge.fireNodesChanged("/");
        } catch (Throwable t) {
            _tracer.error("Configuration.onConnected error.", t);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNodeChanged(String path, byte[] data) {
        _tracer.info("onNodeChanged. Path: " + path + "; Data: " + new String(data, Charset.forName("utf-8")));
        String tableName = getTableNameFromPath(path);
        if (tableName == null) {
            _tracer.debug("Can NOT parse tableName from path. " + path);
            return;
        }
        ConfigurationReciever<ConfigurationData> receiver = (ConfigurationReciever<ConfigurationData>) _receivers.get(tableName);
        if (receiver == null) {
            _tracer.debug("Can NOT find receiver by tableName. " + tableName);
            return;
        }
        ConfigurationData row = (ConfigurationData) ConfigurationDecoder.decode(data, receiver.getClazz());
        if (row == null) {
            _tracer.debug("Can NOT decode the data to class[" + receiver.getClazz().getName() + ". JSonString: " + new String(data, Charset.forName("utf-8")));
            return;
        }

        if (!checkConfigurationRow(row, receiver))
            return;

        try {
            _tracer.error("Received node changed.\r\n" + receiver.toString() + "\r\n" + row.toString());
            receiver.onConfigurationReceived(path, row);
        } catch (Throwable t) {
            _tracer.error("onConfigurationReceived error.", t);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNodeRemoved(String path) {
        String tableName = getTableNameFromPath(path);
        if (tableName == null)
            return;
        ConfigurationReciever<ConfigurationData> receiver = (ConfigurationReciever<ConfigurationData>) _receivers.get(tableName);
        if (receiver == null)
            return;
        receiver.onConfigurationRemoved(path);
    }

    private String getTableNameFromPath(String path) {
        String[] temp = path.split("/");
        if (temp.length < 2)
            return null;
        return temp[1];
    }

    private boolean checkConfigurationRow(ConfigurationData row, ConfigurationReciever<ConfigurationData> receiver) {
        if (!(row instanceof ConfigurationRow))
            return true;
        ConfigurationRow r = (ConfigurationRow) row;

        if (!r.getEnable()) {
            _tracer.debug("Configuration is NOT enabled.\r\n" + r.toString() + "\r\n" + receiver.toString());
            return false;
        }

        String service = r.getServiceName();
        if (service == null) {
            _tracer.debug("ServcieName is NULL.\r\n" + r.toString() + "\r\n" + receiver.toString());
            return false;
        }
        if (!service.equalsIgnoreCase(receiver.getServiceName()) && !service.equals("*")) {
            _tracer.debug("ServcieName do NOT match receiver.\r\n" + r.toString() + "\r\n" + receiver.toString());
            return false;
        }

        String computer = r.getComputerName();
        if (computer == null) {
            _tracer.debug("ComputerName is NULL.\r\n" + r.toString() + "\r\n" + receiver.toString());
            return false;
        }
        if (!computer.equalsIgnoreCase(receiver.getComputerName()) && !computer.equals("*")) {
            _tracer.debug("ComputerName do NOT match receiver.\r\n" + r.toString() + "\r\n" + receiver.toString());
            return false;
        }

        String module = r.getModuleName();
        if (module == null) {
            _tracer.debug("ModuleName is NULL.\r\n" + r.toString() + "\r\n" + receiver.toString());
            return false;
        }
        if (!module.equalsIgnoreCase(receiver.getModuleName()) && !module.equals("*")) {
            _tracer.debug("ModuleName do NOT match receiver.\r\n" + r.toString() + "\r\n" + receiver.toString());
            return false;
        }

        return true;
    }
}