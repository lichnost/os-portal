package com.objectsync.portal.server.workspace;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.objectsync.portal.core.model.WorkspaceServer;
import com.objectsync.portal.server.domain.DomainManager;
import com.objectsync.portal.server.lifecycle.LifecycleManager;
import com.objectsync.portal.server.lifecycle.LifecycleManager.Handler;
import com.objectsync.portal.server.lifecycle.LifecycleManager.State;

@Singleton
public class CassandraWorkspaceManager implements WorkspaceManager, Handler {

	private LifecycleManager _lifecycle;
	private DomainManager _domain;

	private Semaphore _semaphore = new Semaphore(1);

	private Map<WorkspaceServer, WorkspaceData> _instances = new ConcurrentHashMap<>();

	private class WorkspaceData {

		WorkspaceInstance instance;
		int port;

		WorkspaceData(WorkspaceInstance instance, int port) {
			this.instance = instance;
			this.port = port;
		}

	}

	@Inject
	public CassandraWorkspaceManager(CassandraWorkspaceSettings settings,
			LifecycleManager lifecycle, DomainManager domain) {
		_lifecycle = lifecycle;
		_domain = domain;
		initTrigger();
	}

	private void initTrigger() {
		_lifecycle.setHandler(this);
	}

	@Override
	public State onStart(WorkspaceServer server) {
		try {
			_semaphore.acquire();
			State result = startWorkspace(server);
			registerDomain(server);
			_semaphore.release();
			return result;
		} catch (InterruptedException e) {
			return State.REJECTED;
		}
	}

	@Override
	public State onStop(WorkspaceServer server) {
		try {
			_semaphore.acquire();
			State result = stopWorkspace(server);
			unregisterDomain(server);
			_semaphore.release();
			return result;
		} catch (InterruptedException e) {
			return State.REJECTED;
		}
	}

	private State startWorkspace(WorkspaceServer server) {
		// first find available port
		int port = getAvailablePort();
		if (port == 0) {
			return State.REJECTED;
		}
		WorkspaceInstance instance = new WorkspaceInstance(_settings, server,
				port);
		instance.start();
		_instances.put(server, new WorkspaceData(instance, port));
		return State.STARTED;
	}

	private State stopWorkspace(WorkspaceServer server) {

		WorkspaceData data = _instances.get(server);
		data.instance.stop();
		_instances.remove(server);
		return State.STOPED;
	}

	private void registerDomain(WorkspaceServer server) {
		_domain.register(
				server.getWorkspace(),
				Arrays.asList(
						"ws://" + _lifecycle.getHostName() + ":"
								+ _instances.get(server).port,
						"wss://" + _lifecycle.getHostName() + ":"
								+ _instances.get(server).port));
	}

	private void unregisterDomain(WorkspaceServer server) {
		_domain.unregister(server.getWorkspace());
	}

	private int getAvailablePort() {
		int count = 100;
		while (count > 0) {
			int result = randInt(0x400, 0xFFFF);
			if (portAvailable(result)) {
				return result;
			}
			count--;
		}
		return 0;
	}

	private static int randInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	private static boolean portAvailable(int port) {
		if (port < 0 || port > 0xFFFF) {
			throw new IllegalArgumentException("Invalid port: " + port);
		}

		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}
}
