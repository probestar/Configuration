/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title ConfigurationClientReceiver.java
 * @Package com.probestar.configurationtest
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年4月26日 下午9:21:56
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtest;


import com.probestar.configuration.common.ConfigurationReciever;
import com.probestar.configuration.model.ConfigurationData;
import com.probestar.configuration.model.ConfigurationTable;
import com.probestar.configuration.utils.ConfigurationTracerHelper;

public class ConfigurationClientReciever<ConfigurationRow> extends ConfigurationReciever<ConfigurationData> {
    private static ConfigurationTracerHelper _tracer = ConfigurationTracerHelper.getInstance(ConfigurationModuleReceiver.class);

    public ConfigurationClientReciever(Class<?> clazz) {
        super(ConfigurationTable.ClientUpdate, "", clazz);
    }

    @Override
    public void onConfigurationReceived(String key, ConfigurationData data) {
        _tracer.info("[" + key + "]" + data.toString());
    }

    @Override
    public void onConfigurationRemoved(String key) {
        _tracer.info("[" + key + "] has been remvoed. ");
    }
}