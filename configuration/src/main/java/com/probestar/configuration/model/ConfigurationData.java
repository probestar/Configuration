/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationData.java
 * @Package com.probestar.configuration
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 5:25:17 PM
 * @version V1.0
 * @Description
 */

package com.probestar.configuration.model;

import java.util.UUID;

public class ConfigurationData {
    private transient String _key;

    public String getKey() {
        if (_key == null)
            _key = UUID.randomUUID().toString();
        return _key;
    }

    @Override
    public String toString() {
        return "";
    }
}
