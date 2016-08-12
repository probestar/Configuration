/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationReciever.java
 * @Package com.probestar.configuration
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 5:24:01 PM
 * @version V1.0
 * @Description
 */

package com.probestar.configuration.common;

import com.probestar.configuration.model.ConfigurationData;
import com.probestar.configuration.utils.ConfigurationSettings;

public abstract class ConfigurationReciever<T extends ConfigurationData> {
    private String _table;
    private String _service;
    private String _computer;
    private String _module;
    private Class<?> _clazz;

    public ConfigurationReciever(String tableName, String moduleName, Class<?> clazz) {
        _table = tableName;
        _module = moduleName;
        _clazz = clazz;
        _service = ConfigurationSettings.getInstance().getServiceName();
        _computer = ConfigurationSettings.getInstance().getComputerName();
    }

    public String getTableName() {
        return _table;
    }

    public String getServiceName() {
        return _service;
    }

    public String getComputerName() {
        return _computer;
    }

    public String getModuleName() {
        return _module;
    }

    public Class<?> getClazz() {
        return _clazz;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TableName: ");
        sb.append(getTableName());
        sb.append("; ServiceName: ");
        sb.append(getServiceName());
        sb.append("; ComputerName: ");
        sb.append(getComputerName());
        sb.append("; ModuleName: ");
        sb.append(getModuleName());
        sb.append("; Clazz: ");
        sb.append(getClazz().getName());
        return sb.toString();
    }

    public abstract void onConfigurationReceived(String key, T data);

    public abstract void onConfigurationRemoved(String key);

}
