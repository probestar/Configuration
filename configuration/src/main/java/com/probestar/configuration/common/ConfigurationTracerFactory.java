/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title ConfigurationTracerFactory.java
 * @Package com.probestar.configuration.common
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年4月26日 上午10:59:30
 * @version V1.0
 * @Description
 */

package com.probestar.configuration.common;

public interface ConfigurationTracerFactory {

    ConfigurationTracer createTracer(Class<?> c);

}