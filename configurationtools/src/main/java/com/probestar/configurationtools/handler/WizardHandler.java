/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title WizardHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 30, 2015 10:02:25 AM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools.handler;


import com.probestar.configuration.codec.ConfigurationDecoder;
import com.probestar.configuration.codec.ConfigurationEncoder;
import com.probestar.configuration.model.ClientUpdateRow;
import com.probestar.configuration.model.ConfigurationData;
import com.probestar.configuration.model.ConfigurationRow;
import com.probestar.configuration.model.ConfigurationTable;
import com.probestar.configurationtools.CTResult;
import com.probestar.configurationtools.CTSession;
import com.probestar.psutils.PSConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class WizardHandler extends CTHandler {
    private static Logger _tracer = LoggerFactory.getLogger(WizardHandler.class);

    public ArrayList<String> getAilas() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("wz");
        return list;
    }

    public String getCommand() {
        return "wizard";
    }

    public CTResult handle(String[] paramters) throws Throwable {
        String key = paramters.length > 1 ? paramters[1] : null;
        System.out.println("Please input the following fileds.");
        byte[] b = null;
        if (key != null)
            b = getBridge().get(key);

        ConfigurationData newData = null;
        switch (CTSession.getInstance().getCurrentTable()) {
            case ConfigurationTable.Configuration:
                newData = configurationWizard(b);
                break;
            case ConfigurationTable.ClientUpdate:
                newData = clientupdatewizard(b);
                break;
            default:
                _tracer.error("Unsupport Table: " + CTSession.getInstance().getCurrentTable());
                break;
        }

        if (key == null) {
            key = newData.getKey();
            getBridge().create(key, ConfigurationEncoder.encode1(newData));
        } else {
            getBridge().set(key, ConfigurationEncoder.encode1(newData));
        }
        return new CTResult("OK.");
    }

    private ConfigurationRow configurationWizard(byte[] data) {
        ConfigurationRow row = data == null ? new ConfigurationRow() : ConfigurationDecoder.decode(data, ConfigurationRow.class);

        row.setKeyword(inputItem("Keyword", row.getKeyword()));
        row.setValue(inputItem("Value", row.getValue()));
        row.setModuleName(inputItem("ModuleName", row.getModuleName()));
        row.setServiceName(inputItem("ServiceName", row.getServiceName()));
        row.setComputerName(inputItem("ComputerName", row.getComputerName()));
        row.setComments(inputItem("Comments", row.getComments()));
        row.setEnable(Boolean.parseBoolean(inputItem("Enable", row.getEnable() ? "true" : "false")));

        return row;
    }

    private ClientUpdateRow clientupdatewizard(byte[] data) {
        ClientUpdateRow row = data == null ? new ClientUpdateRow() : ConfigurationDecoder.decode(data, ClientUpdateRow.class);

        row.setClientType(inputItem("ClientType", row.getClientType()));
        row.setClientVersion(inputItem("ClientVersion", row.getClientVersion()));
        row.setUpdateType(Integer.parseInt(inputItem("UpdateType", String.valueOf(row.getUpdateType()))));
        row.setUpdateToVersion(inputItem("UpdateToVersion", row.getUpdateToVersion()));
        row.setDownloadUrl(inputItem("DownloadUrl", row.getDownloadUrl()));
        row.setDetailPageUrl(inputItem("DetailPageUrl", row.getDetailPageUrl()));
        row.setPackageSize(Long.parseLong(inputItem("PackageSize", String.valueOf(row.getPackageSize()))));
        row.setReleaseTime(Long.parseLong(inputItem("ReleaseTime", String.valueOf(row.getReleaseTime()))));

        return row;
    }

    private String inputItem(String key, String value) {
        System.out.print(String.format("%s[%s]: ", key, value));
        String input = PSConsole.readLine();
        if (input.equalsIgnoreCase(""))
            return value;
        return input;
    }

}
