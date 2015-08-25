/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ClientUpdateRow.java
 * @Package com.probestar.configuration.model
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 30, 2015 5:24:40 PM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configurationtest.model;

import com.probestar.configuration.model.ConfigurationData;

public class ClientUpdateRow extends ConfigurationData {
	private String _clientType;
	private String _clientVersion;
	private int _updateType;
	private String _updateToVersion;
	private String _downloadUrl;
	private String _detailPageUrl;
	private long _packageSize;
	private long _releaseTime;

	public void setClientType(String clientType) {
		_clientType = clientType;
	}

	public String getClientType() {
		return _clientType;
	}

	public void setClientVersion(String clientVersion) {
		_clientVersion = clientVersion;
	}

	public String getClientVersion() {
		return _clientVersion;
	}

	public void setUpdateType(int updateType) {
		_updateType = updateType;
	}

	public int getUpdateType() {
		return _updateType;
	}

	public void setUpdateToVersion(String version) {
		_updateToVersion = version;
	}

	public String getUpdateToVersion() {
		return _updateToVersion;
	}

	public void setDownloadUrl(String url) {
		_downloadUrl = url;
	}

	public String getDownloadUrl() {
		return _downloadUrl;
	}

	public void setDetailPageUrl(String url) {
		_detailPageUrl = url;
	}

	public String getDetailPageUrl() {
		return _detailPageUrl;
	}

	public void setPackageSize(long size) {
		_packageSize = size;
	}

	public long getPackageSize() {
		return _packageSize;
	}

	public void setReleaseTime(long time) {
		_releaseTime = time;
	}

	public long getReleaseTime() {
		return _releaseTime;
	}

	public String getKey() {
		return String.format("%s_%s", getClientType(), getClientVersion());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClientType: ");
		sb.append(_clientType);
		sb.append("; ClientVersion: ");
		sb.append(_clientVersion);
		sb.append("; UpdateType: ");
		sb.append(_updateType);
		sb.append("; UpdateToVersion: ");
		sb.append(_updateToVersion);
		sb.append("; DownloadUrl: ");
		sb.append(_downloadUrl);
		sb.append("; DetailPageUrl: ");
		sb.append(_detailPageUrl);
		sb.append("; PackageSize: ");
		sb.append(_packageSize);
		sb.append("; ReleaseTime: ");
		sb.append(_releaseTime);
		return sb.toString();
	}
}
