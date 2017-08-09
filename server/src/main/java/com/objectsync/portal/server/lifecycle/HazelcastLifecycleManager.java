package com.objectsync.portal.server.lifecycle;

import java.util.List;

import javax.inject.Inject;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ISet;
import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;
import com.hazelcast.core.MultiMap;
import com.objectsync.portal.core.PortalCore;
import com.objectsync.portal.core.model.WorkspaceServer;

public class HazelcastLifecycleManager extends Thread implements
		MembershipListener, Runnable, LifecycleManager {

	private static String START_WORKSPACE_QUEUE = "workspace-start-queue";
	private static String STARTED_WORKSPACES_MAP = "workspaces-started-map";
	private static String STOP_WORKSPACE_QUEUE = "workspace-stop-queue";
	private static String LOCK = "lock";
	private static String REMOVE_LOCK = "lock";

	private PortalCore _portal;
	private HazelcastLifecycleSettings _settings;
	private Handler _handler;

	private HazelcastInstance _hazelcast;
	private IQueue<WorkspaceServer> _startQueue;
	private MultiMap<String, WorkspaceServer> _startedMap;
	private ISet<WorkspaceServer> _stopQueue;

	private ILock _lock;
	private ILock _removeLock;

	@Inject
	public HazelcastLifecycleManager(PortalCore portal,
			HazelcastLifecycleSettings settings) {
		_portal = portal;
		_settings = settings;

		Config config = new Config();
		config.getGroupConfig().setName(_settings.getGroupName());
		config.getGroupConfig().setPassword(_settings.getGroupPassword());

		_hazelcast = Hazelcast.newHazelcastInstance(config);
		_hazelcast.getCluster().addMembershipListener(this);

		_startQueue = _hazelcast.getQueue(START_WORKSPACE_QUEUE);
		_startedMap = _hazelcast.getMultiMap(STARTED_WORKSPACES_MAP);
		_stopQueue = _hazelcast.getSet(STOP_WORKSPACE_QUEUE);
		_lock = _hazelcast.getLock(LOCK);
		_removeLock = _hazelcast.getLock(REMOVE_LOCK);
		start();
	}

	/**
	 * Starts worspace servier instance.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				WorkspaceServer server = _startQueue.take();
				_lock.lock();
				try {
					if (_handler != null
							&& _handler.onStart(server) == com.objectsync.portal.server.lifecycle.LifecycleManager.State.STARTED) {
						_startedMap.put(_hazelcast.getCluster()
								.getLocalMember().getUuid(), server);
					}
				} finally {
					_lock.unlock();
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	@Override
	public void memberAdded(MembershipEvent membershipEvent) {

	}

	@Override
	public void memberRemoved(MembershipEvent membershipEvent) {
		if (_removeLock.tryLock()) {
			try {
				_lock.lock();
				try {
					String memberId = membershipEvent.getMember().getUuid();
					if (_startedMap.containsKey(memberId)) {
						_startedMap.remove(memberId);
					}
				} finally {
					_lock.unlock();
				}
				checkStarts();
			} finally {
				_removeLock.unlock();
			}
		}
	}

	@Override
	public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
	}

	/**
	 * Check new servers to start.
	 */
	private void checkStarts() {
		_lock.lock();
		try {
			List<WorkspaceServer> servers = _portal.getWorkspaceServers();
			for (WorkspaceServer s : servers) {
				if (!_startedMap.containsValue(s)) {
					_startQueue.add(s);
				}
			}
		} finally {
			_lock.unlock();
		}
	}

	@Override
	public String getHostName() {
		return _hazelcast.getCluster().getLocalMember().getAddress().getHost();
	}

	@Override
	public void setHandler(Handler handler) {
		_handler = handler;
	}

}
