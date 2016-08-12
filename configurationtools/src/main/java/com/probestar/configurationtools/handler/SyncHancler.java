/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title SyncHancler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Aug 3, 2015 10:27:42 AM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools.handler;


import com.probestar.configuration.codec.ConfigurationDecoder;
import com.probestar.configurationtools.CTResult;

public class SyncHancler extends CTHandler {

    public String getCommand() {
        return "sync";
    }

    public CTResult handle(String[] paramters) throws Throwable {
        byte[] data = getBridge().get("/");
        String s = ConfigurationDecoder.decode2String(data);
        int i = Integer.parseInt(s);
        getBridge().set("/", String.format("%d", i + 1).getBytes("utf-8"));
        return new CTResult("OK.");
    }

}