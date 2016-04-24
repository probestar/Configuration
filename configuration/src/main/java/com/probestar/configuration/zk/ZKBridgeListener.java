/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ZKBridgeListener.java
 * @Package com.probestar.configuration.zk
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 3:35:51 PM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configuration.zk;

public interface ZKBridgeListener {

	void onNodeChanged(String tableName, String key, byte[] data);

	void onNodeRemoved(String tableName, String key);

}