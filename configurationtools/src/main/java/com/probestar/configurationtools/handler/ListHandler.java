/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ListHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 11:47:55 AM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools.handler;


import com.probestar.configuration.codec.ConfigurationDecoder;
import com.probestar.configurationtools.CTResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListHandler extends CTHandler {

    public ArrayList<String> getAilas() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("ls");
        list.add("ll");
        return list;
    }

    public String getCommand() {
        return "list";
    }

    public CTResult handle(String[] paramters) throws Throwable {
        List<String> list = getBridge().list("/");
        Collections.sort(list);
        String search = getParameters(paramters, "--search");
        StringBuilder sb = new StringBuilder();
        int count = 0;
        if ((paramters.length > 1 && paramters[1].equalsIgnoreCase("-l")) || paramters[0].equalsIgnoreCase("ll")) {
            for (String s : list) {
                byte[] data = getBridge().get(s);
                String value = ConfigurationDecoder.decode2String(data);
                if (search != null && value.indexOf(search) < 0)
                    continue;
                sb.append(s);
                sb.append(" : ");
                sb.append(value);
                sb.append("\r\n");
                count++;
            }
        } else {
            for (String s : list) {
                sb.append(s);
                sb.append("\r\n");
                count++;
            }
        }
        return new CTResult(sb.toString() + count + " item(s).\r\nOK.");
    }

    private String getParameters(String[] parameters, String name) {
        int i;
        for (i = 0; i < parameters.length; i++)
            if (parameters[i].equalsIgnoreCase(name))
                break;
        if (i > parameters.length - 1)
            return null;
        return parameters[i + 1];
    }
}
