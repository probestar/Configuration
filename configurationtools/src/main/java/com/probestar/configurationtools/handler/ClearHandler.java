/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ClearHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 29, 2015 11:44:27 AM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools.handler;


import com.probestar.configuration.model.ConfigurationTable;
import com.probestar.configurationtools.CTResult;
import com.probestar.configurationtools.CTSession;
import com.probestar.psutils.PSConsole;

import java.util.List;

public class ClearHandler extends CTHandler {

    public String getCommand() {
        return "clear";
    }

    public CTResult handle(String[] paramters) throws Throwable {
        if (!CTSession.getInstance().getCurrentTable().equals(ConfigurationTable.Root))
            return new CTResult("You MUST switch to '/' first.\r\nError.");
        System.out.print("How dare u do this?\r\n[y/n] ");
        if (!PSConsole.readLine().equalsIgnoreCase("Y"))
            return new CTResult("Cancelled.");
        clear("");
        return new CTResult("OK.");
    }

    private void clear(String path) throws Throwable {
        List<String> list = getBridge().list(path);
        for (String s : list) {
            String temp = path + "/" + s;
            clear(temp);
            getBridge().delete(temp);
        }
    }
}
