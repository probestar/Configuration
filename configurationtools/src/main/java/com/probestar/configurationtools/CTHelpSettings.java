/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title CTHelpSettings.java
 * @Package com.probestar.configurationtools
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 5:24:13 PM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

public class CTHelpSettings {
    private static Logger _tracer = LoggerFactory.getLogger(CTHelpSettings.class);
    private static CTHelpSettings _instance;

    static {
        try {
            _instance = new CTHelpSettings();
        } catch (Throwable t) {
            _tracer.error("CTHelpSettings.static error.", t);
        }
    }

    public static CTHelpSettings getInstance() {
        return _instance;
    }

    private Properties _p;

    private CTHelpSettings() {
        try {
            _p = new Properties();
            _p.load(new FileInputStream("Help.properties"));
        } catch (Throwable t) {
            _tracer.error("CTHelpSettings.CTHelpSettings error.", t);
        }
    }

    public String getHelp(String cmd) {
        String key = cmd.toUpperCase();
        String help = _p.getProperty(key);
        if (help == null)
            help = _p.getProperty("DEFAULT");
        return help;
    }
}
