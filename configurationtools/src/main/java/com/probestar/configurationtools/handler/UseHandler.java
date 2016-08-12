/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title UseHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 11:35:28 AM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools.handler;


import com.probestar.configuration.model.ConfigurationTable;
import com.probestar.configurationtools.CTResult;
import com.probestar.configurationtools.CTSession;

import java.util.ArrayList;

public class UseHandler extends CTHandler {

    public ArrayList<String> getAilas() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("switch");
        return list;
    }

    public String getCommand() {
        return "use";
    }

    public CTResult handle(String[] paramters) {
        String table = paramters[1];
        if (!ConfigurationTable.contains(table))
            return new CTResult(table + " is not supported.");
        CTSession.getInstance().setCurrentTable(table);
        return new CTResult("CurrentTable is " + CTSession.getInstance().getCurrentTable() + ".\r\nOK.");
    }

}