/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationTable.java
 * @Package com.probestar.configurationtest
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Aug 3, 2015 11:22:34 AM
 * @version V1.0
 * @Description
 */

package com.probestar.configuration.model;

public class ConfigurationTable {
    public static final String Root = "/";
    public static final String Configuration = "Configuration";
    public static final String ClientUpdate = "ClientUpdate";

    public static boolean contains(String tableName) {
        switch (tableName) {
            case Root:
            case Configuration:
            case ClientUpdate:
                return true;
            default:
                return false;
        }
    }
}