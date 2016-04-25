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
			_zk = createZooKeeper();
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
			boolean exists = !(_zk.exists(path, false) == null);
			if (exists)
				_tracer.info("Path %s exists", path);
			else
				_tracer.info("Path %s not exists", path);
			return exists;
		} catch (Throwable t) {
			_tracer.error("ZKBridge.exists", t);
			return false;
		}
	}

	public void create(String key, byte[] data) throws Throwable {
		String path = getPath(key);
		try {
			if (!exists(path)) {
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
		List<String> list = _zk.getChildren("/", false);
		_tracer.debug("Get list: " + list.toString());
		return list;
	}

	public void set(String key, byte[] value) throws Throwable {
		String path = getPath(key);
		_zk.setData(path, value, -1);
		_tracer.info("Set %s to %s.", path, PSConvert.bytes2HexString(value));
	}

	public byte[] get(String key) throws Throwable {
		byte[] b = null;
		String path = getPath(key);
		b = _zk.getData(path, false, null);
		_tracer.debug("%s: %s", path, PSConvert.bytes2HexString(b));
		return b;
	}

	public synchronized void close() {
		try {
			_zk.close();
			_tracer.info("[" + _zk.toString() + "] has been closed.");
		} catch (InterruptedException t) {
			_tracer.error("ZKBridge.close error.", t);
		}
	}

	@Override
	public synchronized void process(WatchedEvent event) {
		_tracer.info("Received Event: " + event.toString());
		try {
			switch (event.getState()) {
			case Expired:
				_zk.close();
				_tracer.info("[" + _zk.toString() + "] has been closed for receiving expired event.");
				_zk = createZooKeeper();
				break;
			case SyncConnected:
				switch (event.getType()) {
				case None:
				case NodeChildrenChanged:
					continued("/");
					fireNodesChanged();
					break;
				case NodeDataChanged:
					continued(event.getPath());
					fireNodeChanged(event.getPath());
					break;
				case NodeDeleted:
					fireNodeDeleted(event.getPath());
					break;
				default:
					_tracer.error("Got unhandled Event. " + event.toString());
					continued("/");
					fireNodesChanged();
					break;
				}
				break;
			default:
				break;
			}
		} catch (Throwable t) {
			_tracer.error("ZKBridge.process error. " + event.toString(), t);
		}
	}

	@Override
	public String toString() {
		return _zk == null ? super.toString() : _zk.toString();
	}

	private void fireNodeChanged(String path) throws Throwable {
		byte[] data = get(path);
		for (ZKBridgeListener listener : _listeners)
			listener.onNodeChanged(_tableName, path, data);
	}

	private void fireNodeDeleted(String path) throws Throwable {
		for (ZKBridgeListener listener : _listeners)
			listener.onNodeRemoved(_tableName, path);
	}

	private void fireNodesChanged() throws Throwable {
		List<String> list = list();
		for (String path : list)
			fireNodeChanged(path);
	}

	private String getPath(String key) {
		return key.startsWith("/") ? key : "/" + key;
	}

	private ZooKeeper createZooKeeper() throws Exception {
		String conn = _tableName.isEmpty() ? _conn : String.format("%s/%s", _conn, _tableName);
		ZooKeeper zk = new ZooKeeper(conn, 5000, this);
		if (_name != null && _password != null)
			zk.addAuthInfo("digest", String.format("%s:%s", _name, _password).getBytes());
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

	private void continued(String path) throws KeeperException, InterruptedException {
		if (path == null)
			path = "/";
		List<String> children = _zk.getChildren(path, true);
		_zk.exists(path, true);
		for (String child : children)
			_zk.exists(path + child, true);
	}
}