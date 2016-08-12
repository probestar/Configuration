/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationSettings.java
 * @Package com.probestar.configuration
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 5:04:10 PM
 * @version V1.0
 * @Description
 */

package com.probestar.configuration.utils;

import com.google.common.base.Preconditions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationSettings {
    private static ConfigurationTracerHelper _tracer = ConfigurationTracerHelper.getInstance(ConfigurationSettings.class);
    private static ConfigurationSettings _instance;

    private String _service;
    private String _computer;
    private String _zks;

    public static void initialize() throws FileNotFoundException, IOException {
        _instance = new ConfigurationSettings();
    }

    public static ConfigurationSettings getInstance() {
        return _instance;
    }

    private ConfigurationSettings() throws FileNotFoundException, IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("ServiceSettings.properties"));
        _service = p.getProperty("ServiceName");
        _computer = p.getProperty("ComputerName");
        _zks = p.getProperty("Zookeepers");

        Preconditions.checkNotNull(_service, "ServiceName");
        Preconditions.checkNotNull(_computer, "ComputerName");
        Preconditions.checkNotNull(_zks, "Zookeepers");

        _tracer.error("Load ServiceSettings.Properties: " + toString());
    }

    public String getServiceName() {
        return _service;
    }

    public String getComputerName() {
        return _computer;
    }

    public String getZookeepers() {
        return _zks;
    }

    public String getAddress() {
        return getZookeepers().substring(0, getZookeepers().indexOf('/'));
    }

    public String getPath() {
        return getZookeepers().substring(getZookeepers().indexOf('/'));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServiceName:");
        sb.append(getServiceName());
        sb.append("; ComputerName: ");
        sb.append(getComputerName());
        sb.append("; Zookeepers: ");
        sb.append(getZookeepers());
        sb.append("; Addresss: ");
        sb.append(getAddress());
        sb.append("; Path: ");
        sb.append(getPath());
        return sb.toString();
    }
}
