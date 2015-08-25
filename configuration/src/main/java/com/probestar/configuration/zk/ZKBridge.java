/**
 *
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ZKBridge.java
 * @Package com.probestar.configuration.zk
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 28, 2015 4:51:41 PM
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.configuration.zk;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.probestar.psutils.PSConvert;
import com.probestar.psutils.PSTracer;

public class ZKBridge implements Watcher {
	private static PSTracer _tracer = PSTracer.getInstance(ZKBridge.class);

	private String _conn;
	private String _tableName;
	private String _name;
	private String _password;
	private ArrayList<ZKBridgeListener> _listeners;
	private ZooKeeper _zk;
	private List<ACL> _acl;

	public ZKBridge(String conn, String tableName) {
		this(conn, tableName, null, null);
	}

	public ZKBridge(String conn, String tableName, String userName, String password) {
		try {
			_conn = conn;
			_tableName = tableName;
			_name = userName;
			_password = password;
			_listeners = new ArrayList<ZKBridgeListener>();
			String s = tableName.isEmpty() ? conn : String.format("%s/%s", conn, tableName);
			_zk = createZooKeeper(s, userName, password);
			_acl = createAclList(userName, password);
		} catch (Throwable e) {
			_tracer.error("ZKBridge.ZKBridge error.", e);
		}
	}

	public synchronized void register(ZKBridgeListener listener) {
		if (_listeners.contains(listener)) {
			_tracer.error("ZKBridgeListener has been registered. " + listener.toString());
			return;
		}
		_listeners.add(listener);
	}

	public synchronized void unregister(ZKBridgeListener listener) {
		_listeners.remove(listener);
	}

	public boolean exists(String key) {
		String path = getPath(key);
		try {
			return !(_zk.exists(path, false) == null);
		} catch (Throwable t) {
			_tracer.error("ZKBridge.exists", t);
			return false;
		}
	}

	public void create(String key, byte[] data) throws Throwable {
		String path = getPath(key);
		try {
			if (!exists(key)) {
				_zk.create(path, data, _acl, CreateMode.PERSISTENT);
				_tracer.info("Path %s has been created.", path);
			}
		} catch (KeeperException e) {
			_tracer.error("ZKBridge.create error. key: " + key + "; data: " + PSConvert.bytes2HexString(data), e);
		}
	}

	public void delete(String key) throws Throwable {
		_zk.delete(getPath(key), -1);
		_tracer.info("Path %s has been deleted.", key);
	}

	public List<String> list() throws Throwable {
		return _zk.getChildren("/", null);
	}

	public void set(String key, byte[] value) throws Throwable {
		_zk.setData(getPath(key), value, -1);
	}

	public byte[] get(String key) throws Throwable {
		byte[] b = null;
		b = _zk.getData(getPath(key), null, null);
		return b;
	}

	public void close() {
		try {
			_zk.close();
		} catch (InterruptedException t) {
			_tracer.error("ZKBridge.close error.", t);
		}
	}

	public void process(WatchedEvent event) {
		_tracer.info("Received Event: " + event.toString());
		try {
			switch (event.getState()) {
			case Expired:
				_zk.close();
				_zk = createZooKeeper(_conn, _name, _password);
				break;
			case SyncConnected:
				switch (event.getType()) {
				case None:
					fireNodesChanged();
					_zk.exists("/", this);
					break;
				case NodeDataChanged:
					if (event.getPath().equalsIgnoreCase("/"))
						fireNodesChanged();
					_zk.exists("/", this);
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}

		} catch (Throwable t) {
			_tracer.error("ZKBridge.process error.", t);
		}
	}

	public String toString() {
		return _zk == null ? super.toString() : _zk.toString();
	}

	private void fireNodeChanged(String path) throws Throwable {
		byte[] data = get(path);
		for (ZKBridgeListener listener : _listeners)
			listener.onNodeChanged(_tableName, data);
	}

	private void fireNodesChanged() throws Throwable {
		List<String> list = list();
		for (String path : list)
			fireNodeChanged(path);
	}

	private String getPath(String key) {
		return key.startsWith("/") ? key : "/" + key;
	}

	private ZooKeeper createZooKeeper(String conn, String name, String password) throws Exception {
		ZooKeeper zk = new ZooKeeper(conn, 5000, this);
		if (name != null && password != null)
			zk.addAuthInfo("digest", String.format("%s:%s", name, password).getBytes());
		return zk;
	}

	private List<ACL> createAclList(String name, String pwd) throws Exception {
		List<ACL> list = new ArrayList<ACL>();
		if (name != null && pwd != null) {
			Id id = new Id("digest", DigestAuthenticationProvider.generateDigest(String.format("%s:%s", name, pwd)));
			ACL acl = new ACL(ZooDefs.Perms.ALL, id);
			list.add(acl);
		}
		list.addAll(Ids.READ_ACL_UNSAFE);
		return list;
	}
}