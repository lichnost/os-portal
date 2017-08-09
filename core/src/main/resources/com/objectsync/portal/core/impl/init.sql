CREATE KEYSPACE objectsync_service WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

CREATE TABLE users (id uuid PRIMARY KEY, email text, password text);
CREATE INDEX user_email_idx ON users (email);

CREATE TABLE workspace_servers (id uuid PRIMARY KEY, workspace text);

CREATE TABLE user_servers (id uuid PRIMARY KEY, user_id uuid, workspace_server_id uuid);
CREATE INDEX user_servers_user_idx ON user_servers (user_id);
CREATE INDEX user_servers_workspace_server_idx ON user_servers (workspace_server_id);

CREATE TABLE user_server_origins (id uuid PRIMARY KEY, workspace_servers_id uuid, origin text, description text);
CREATE INDEX user_server_origins_user_server_idx ON user_server_origins (workspace_servers_id);