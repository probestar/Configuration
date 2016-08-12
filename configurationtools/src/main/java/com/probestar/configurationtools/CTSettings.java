package com.probestar.configurationtools;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Copyright (c) 2016 All rights reserved.
 *
 * @author ProbeStar
 * @version V1.0
 * @Title CTSettings.java
 * @Package com.probestar.configurationtools
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年4月29日 上午11:30:29
 * @Description
 */

public class CTSettings {
    private static Logger _tracer = LoggerFactory.getLogger(CTSettings.class);
    private static CTSettings _instance;

    private String _zks;

    public static void initialize() throws FileNotFoundException, IOException {
        _instance = new CTSettings();
    }

    public static CTSettings getInstance() {
        return _instance;
    }

    private CTSettings() throws FileNotFoundException, IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("CTSettings.Properties"));
        _zks = p.getProperty("Zookeepers");
        Preconditions.checkNotNull(_zks, "Zookeepers");

        _tracer.error("Load CTSettings.Properties: " + toString());
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
        sb.append("Zookeepers: ");
        sb.append(getZookeepers());
        sb.append("; Addresss: ");
        sb.append(getAddress());
        sb.append("; Path: ");
        sb.append(getPath());
        return sb.toString();
    }
}
