/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title SetHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 2:23:12 PM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools.handler;


import com.probestar.configurationtools.CTResult;

import java.util.ArrayList;

public class SetHandler extends CTHandler {

    public ArrayList<String> getAilas() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("update");
        return list;
    }

    public String getCommand() {
        return "set";
    }

    public CTResult handle(String[] paramters) throws Throwable {
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < paramters.length; i++) {
            sb.append(paramters[i]);
            sb.append(" ");
        }
        if (sb.length() > 1)
            sb.setLength(sb.length() - 1);
        getBridge().set(paramters[1], sb.toString().getBytes("utf-8"));
        return new CTResult("OK.");
    }

}
