/**
 *
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
 *
 */

package com.probestar.configuration;

import com.probestar.configuration.model.ConfigurationData;

public interface ConfigurationReciever<T extends ConfigurationData> {

	void onConfigurationReceived(T data);

}
