package com.allstar.configurationtest.model;

import com.allstar.configuration.model.ConfigurationData;

public class ConfigurationRow extends ConfigurationData {
	private String _keyword;
	private String _value;
	private String _serviceName;
	private String _moduleName;
	private String _computerName;
	private String _comments;
	private boolean _enable;

	public void setKeyword(String keyword) {
		_keyword = keyword;
	}

	public String getKeyword() {
		return _keyword;
	}

	public void setValue(String value) {
		_value = value;
	}

	public String getValue() {
		return _value;
	}

	public void setServiceName(String serviceName) {
		_serviceName = serviceName;
	}

	public String getServiceName() {
		return _serviceName;
	}

	public void setModuleName(String moduleName) {
		_moduleName = moduleName;
	}

	public String getModuleName() {
		return _moduleName;
	}

	public void setComputerName(String computerName) {
		_computerName = computerName;
	}

	public String getComputerName() {
		return _computerName;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public String getComments() {
		return _comments;
	}

	public void setEnable(boolean enable) {
		_enable = enable;
	}

	public boolean getEnable() {
		return _enable;
	}

	public String getKey() {
		return String.format("%s_%s_%s_%s", getKeyword(), getServiceName(), getModuleName(), getComputerName());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Keyword: ");
		sb.append(_keyword);
		sb.append("; Value: ");
		sb.append(_value);
		sb.append("; ServiceName: ");
		sb.append(_serviceName);
		sb.append("; ModuleName: ");
		sb.append(_moduleName);
		sb.append("; ComputerName: ");
		sb.append(_computerName);
		sb.append("; Comments: ");
		sb.append(_comments);
		sb.append("; Enable: ");
		sb.append(_enable);
		return sb.toString();
	}
}