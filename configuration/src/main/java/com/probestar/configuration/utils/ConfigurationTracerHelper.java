/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title ConfigurationTracerHelper.java
 * @Package com.probestar.configuration.utils
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年4月26日 上午10:58:19
 * @version V1.0
 * @Description
 */

package com.probestar.configuration.utils;


import com.probestar.configuration.common.ConfigurationTracer;
import com.probestar.configuration.common.ConfigurationTracerFactory;

import java.util.HashMap;
import java.util.Map.Entry;

public class ConfigurationTracerHelper {

    private static ConfigurationTracerFactory _factory;
    private static HashMap<Class<?>, ConfigurationTracerHelper> _tracers;

    private ConfigurationTracer _tracer;

    static {
        try {
            _tracers = new HashMap<Class<?>, ConfigurationTracerHelper>();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void registerConfigurationTracerFactory(ConfigurationTracerFactory factory) {
        _factory = factory;
        for (Entry<Class<?>, ConfigurationTracerHelper> entry : _tracers.entrySet())
            entry.getValue().registerConfigurationStackTracer(entry.getKey());
    }

    public static synchronized ConfigurationTracerHelper getInstance(Class<?> c) {
        ConfigurationTracerHelper tracer = _tracers.get(c);
        if (tracer == null) {
            tracer = new ConfigurationTracerHelper(c);
            _tracers.put(c, tracer);
        }
        return tracer;
    }

    private ConfigurationTracerHelper(Class<?> c) {
        registerConfigurationStackTracer(c);
    }

    private void registerConfigurationStackTracer(Class<?> c) {
        if (_factory != null && _tracer == null) {
            try {
                _tracer = _factory.createTracer(c);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public void debug(String info, Object... args) {
        debug(String.format(info, args));
    }

    public void debug(String info) {
        if (_tracer == null)
            return;
        try {
            _tracer.debug(info);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void info(String info, Object... args) {
        info(String.format(info, args));
    }

    public void info(String info) {
        if (_tracer == null)
            return;
        try {
            _tracer.info(info);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void warn(String info) {
        if (_tracer == null)
            return;
        try {
            _tracer.warn(info);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void warn(String info, Throwable t) {
        if (_tracer == null)
            return;
        try {
            _tracer.warn(info, t);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void error(String info) {
        if (_tracer == null)
            return;
        try {
            _tracer.error(info);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void error(String info, Throwable t) {
        if (_tracer == null)
            return;
        try {
            _tracer.error(info, t);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}