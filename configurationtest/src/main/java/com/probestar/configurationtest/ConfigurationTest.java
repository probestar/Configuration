/**
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
 */

package com.probestar.configurationtest;


import com.probestar.configuration.Configuration;
import com.probestar.configuration.model.ClientUpdateRow;
import com.probestar.configuration.model.ConfigurationRow;
import com.probestar.configuration.utils.ConfigurationTracerHelper;
import com.probestar.configurationtest.utils.ConfigurationTracerFactoryImpl;

public class ConfigurationTest {
    private static ConfigurationTracerHelper _tracer = ConfigurationTracerHelper.getInstance(ConfigurationTest.class);

    public static void main(String[] args) {
        try {
            Configuration.initialize();
            Configuration.getInstance().registerConfigurationTracerFactory(new ConfigurationTracerFactoryImpl());
            Configuration.getInstance().initConfiguration(new ConfigurationModuleReceiver<ConfigurationRow>("DbSettingConfig", ConfigurationRow.class));
            Configuration.getInstance().initConfiguration(new ConfigurationModuleReceiver<ConfigurationRow>("CinRouter", ConfigurationRow.class));
//            Configuration.getInstance().initConfiguration(new ConfigurationClientReciever<ConfigurationRow>(ClientUpdateRow.class));
            synchronized (ConfigurationTest.class) {
                ConfigurationTest.class.wait();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
