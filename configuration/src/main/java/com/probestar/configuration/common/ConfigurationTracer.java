/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title ConfigurationTracer.java
 * @Package com.probestar.configuration.common
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年4月26日 上午11:37:53
 * @version V1.0
 * @Description
 */

package com.probestar.configuration.common;

public interface ConfigurationTracer {

    void debug(String info);

    void info(String info);

    void warn(String info);

    void warn(String info, Throwable t);

    void error(String info);

    void error(String info, Throwable t);

}
