/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title ConfigurationTracerImpl.java
 * @Package com.probestar.configurationtest.utils
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年4月26日 上午11:40:42
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtest.utils;


import com.probestar.configuration.common.ConfigurationTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationTracerImpl implements ConfigurationTracer {
    private Logger _tracer;

    public ConfigurationTracerImpl(Class<?> c) {
        _tracer = LoggerFactory.getLogger(c);
    }

    @Override
    public void debug(String info) {
        _tracer.debug(info);
    }

    @Override
    public void info(String info) {
        _tracer.info(info);
    }

    @Override
    public void warn(String info) {
        _tracer.warn(info);
    }

    @Override
    public void warn(String info, Throwable t) {
        _tracer.warn(info, t);
    }

    @Override
    public void error(String info) {
        _tracer.error(info);
    }

    @Override
    public void error(String info, Throwable t) {
        _tracer.error(info, t);
    }
}