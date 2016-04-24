/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ReloadHandler.java
 * @Package com.probestar.configurationtools.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 5:43:00 PM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configurationtools.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.probestar.configuration.codec.ConfigurationEncoder;
import com.probestar.configuration.model.ConfigurationData;
import com.probestar.configurationtest.model.ClientUpdateRow;
import com.probestar.configurationtest.model.ConfigurationRow;
import com.probestar.configurationtest.model.ConfigurationTable;
import com.probestar.configurationtools.CTDispatcher;
import com.probestar.configurationtools.CTResult;
import com.probestar.configurationtools.CTSession;
import com.probestar.psutils.PSTracer;

public class ReloadHandler extends CTHandler {
	private static PSTracer _tracer = PSTracer.getInstance(ReloadHandler.class);

	public String getCommand() {
		return "reload";
	}

	public CTResult handle(String[] paramters) throws Throwable {
		String url = "jdbc:mysql://10.10.169.63:5624/urapport_config?useUnicode=true&amp;characterEncoding=utf8&amp;autoReconnect=true&user=centosuser&password=user2012";
		if (paramters.length > 1)
			url = paramters[1];

		ArrayList<ConfigurationData> list = null;
		switch (CTSession.getInstance().getCurrentTable()) {
		case ConfigurationTable.Configuration:
			list = loadConfiguration(url);
			break;
		case ConfigurationTable.ClientUpdate:
			list = loadClientUpdate(url);
			break;
		default:
			break;
		}

		for (ConfigurationData row : list) {
			String value = ConfigurationEncoder.encode(row);
			CTDispatcher.getInstance().dispatch(String.format("add %s %s", row.getKey(), value));
		}
		return new CTResult("OK.");
	}

	private ArrayList<ConfigurationData> loadConfiguration(String url) {
		String sql = "select * from cin_config;";
		Connection conn = null;
		ArrayList<ConfigurationData> list = new ArrayList<ConfigurationData>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ConfigurationRow row = new ConfigurationRow();
				row.setKeyword(rs.getString("keyword"));
				row.setValue(rs.getString("value"));
				row.setServiceName(rs.getString("servicename"));
				row.setModuleName(rs.getString("modulename"));
				row.setComputerName(rs.getString("computername"));
				row.setComments(rs.getString("comments"));
				row.setEnable(!rs.getBoolean("isenable"));
				// _tracer.info(row.toString());
				list.add(row);
			}
		} catch (Throwable t) {
			_tracer.error("loadConfiguration error.", t);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				_tracer.error("loadConfiguration.finally error.", e);
			}
		}
		return list;
	}

	private ArrayList<ConfigurationData> loadClientUpdate(String url) {
		String sql = "select * from cin_clientupdate;";
		Connection conn = null;
		ArrayList<ConfigurationData> list = new ArrayList<ConfigurationData>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ClientUpdateRow row = new ClientUpdateRow();
				row.setClientType(rs.getString("clienttype"));
				row.setClientVersion(rs.getString("clientversion"));
				row.setUpdateType(rs.getInt("updatetype"));
				row.setUpdateToVersion(rs.getString("updatetoversion"));
				row.setDownloadUrl(rs.getString("downloadurl"));
				row.setDetailPageUrl(rs.getString("detailpageurl"));
				row.setPackageSize(rs.getLong("packagesize"));
				row.setReleaseTime(rs.getLong("releasetime"));
				list.add(row);
			}
		} catch (Throwable t) {
			_tracer.error("loadClientUpdate error.", t);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				_tracer.error("loadClientUpdate.finally error.", e);
			}
		}
		return list;
	}
}