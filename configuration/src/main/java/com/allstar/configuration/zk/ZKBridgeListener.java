/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ZKBridgeListener.java
 * @Package com.allstar.configuration.zk
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 3:35:51 PM
 * @version V1.0
 * @Description 
 *
 */

package com.allstar.configuration.zk;

public interface ZKBridgeListener {

	void onNodeChanged(String tableName, byte[] data);

}