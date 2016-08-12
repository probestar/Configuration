/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title CTHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 27, 2015 4:10:45 PM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools.handler;


import com.probestar.configuration.model.ConfigurationTable;
import com.probestar.configuration.zk.ZKBridge;
import com.probestar.configurationtools.CTHelpSettings;
import com.probestar.configurationtools.CTResult;
import com.probestar.configurationtools.CTSession;
import com.probestar.configurationtools.CTSettings;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CTHandler implements Comparable<CTHandler> {

    private long _watch;
    private HashMap<String, ZKBridge> _bridges;

    public CTHandler() {
        _bridges = new HashMap<String, ZKBridge>();
    }

    public abstract String getCommand();

    public abstract CTResult handle(String[] paramters) throws Throwable;

    public ArrayList<String> getAilas() {
        return null;
    }

    public ZKBridge getBridge() {
        return getBridge(CTSession.getInstance().getCurrentTable());
    }

    public synchronized ZKBridge getBridge(String tableName) {
        ZKBridge bridge = _bridges.get(tableName);
        if (bridge == null) {
            String name = CTSession.getInstance().getOperatorName();
            String pwd = CTSession.getInstance().getPassword();
            if (tableName.equals(ConfigurationTable.Root))
                bridge = new ZKBridge(CTSettings.getInstance().getZookeepers(), name, pwd);
            else
                bridge = new ZKBridge(CTSettings.getInstance().getZookeepers() + "/" + tableName, name, pwd);
            bridge.start();
            _bridges.put(tableName, bridge);
        }
        return bridge;
    }

    public String getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("Command: ");
        sb.append(getCommand());
        sb.append("\r\n");
        sb.append(CTHelpSettings.getInstance().getHelp(getCommand()));
        sb.append("\r\nAilas: ");
        if (getAilas() == null) {
            sb.append("nil");
        } else {
            for (int i = 0; i < getAilas().size(); i++) {
                sb.append(getAilas().get(i));
                if (i < getAilas().size() - 1)
                    sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void startWatch() {
        _watch = System.currentTimeMillis();
    }

    public long stopWatch() {
        return System.currentTimeMillis() - _watch;
    }

    public String toString() {
        return "CTHandler: " + getCommand();
    }

    public int compareTo(CTHandler o) {
        return getCommand().compareTo(o.getCommand());
    }
}
