package com.objectsync.portal.core;

import java.util.List;

import com.objectsync.portal.core.model.SecuredUser;
import com.objectsync.portal.core.model.User;
import com.objectsync.portal.core.model.WorkspaceServer;

public interface PortalCore {

	SecuredUser register(String email, String password)
			throws RegistrationException;

	SecuredUser login(String email, String password);

	List<WorkspaceServer> getWorkspaceServers();

	List<WorkspaceServer> getUserWorkspaceServers(User user);

	void saveWorkspaceServer(WorkspaceServer server);

}
