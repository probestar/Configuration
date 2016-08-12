/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title ConfigurationTracerFactoryImpl.java
 * @Package com.probestar.configurationtools.utils
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年4月26日 上午11:36:41
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools.utils;


import com.probestar.configuration.common.ConfigurationTracer;
import com.probestar.configuration.common.ConfigurationTracerFactory;

public class ConfigurationTracerFactoryImpl implements ConfigurationTracerFactory {

    @Override
    public ConfigurationTracer createTracer(Class<?> c) {
        return new ConfigurationTracerImpl(c);
    }

}
