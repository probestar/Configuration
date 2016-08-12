/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title HelpHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 4:39:52 PM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools.handler;


import com.probestar.configurationtools.CTDispatcher;
import com.probestar.configurationtools.CTResult;

import java.util.ArrayList;

public class HelpHandler extends CTHandler {

    public ArrayList<String> getAilas() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("man");
        return list;
    }

    public String getCommand() {
        return "help";
    }

    public CTResult handle(String[] paramters) {
        if (paramters.length == 1) {
            return new CTResult(CTDispatcher.getInstance().commandList() + "Type 'help [cmd]' for more informatiion.\r\nOK.");
        }
        return new CTResult(CTDispatcher.getInstance().help(paramters[1]) + "\r\nOK.");
    }

}