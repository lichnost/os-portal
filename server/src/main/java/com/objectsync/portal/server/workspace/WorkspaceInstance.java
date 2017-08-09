package com.objectsync.portal.server.workspace;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import com.objectsync.portal.core.model.WorkspaceServer;

public class WorkspaceInstance {

	private CassandraWorkspaceSettings _settings;
	private WorkspaceServer _model;
	private int _port;
	private Cassandra _store;
	private Server _server;
	private ServerBootstrap _bootstrap;

	public WorkspaceInstance(CassandraWorkspaceSettings settings, WorkspaceServer model, int port) {
		_settings = settings;
		_model = model;
		_port = port;
	}

	public WorkspaceServer getWorkspaceServer() {
		return _model;
	}

	public void start() {
		_store = new Cassandra(_settings.getHost(), _settings.getPort(),
				_settings.getUsername(), _settings.getPassword(),
				_model.getWorkspace(), false, 1);
		_server = new JVMServer();
		_server.addURIHandler(_store);

		_bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));
		_bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new HttpRequestDecoder());
				pipeline.addLast("encoder", new HttpResponseEncoder());
				pipeline.addLast("objectsync", new NettySession(_server));
				return pipeline;
			}
		});
		_bootstrap.bind(new InetSocketAddress(_port));
	}

	public void stop() {
		_bootstrap.shutdown();
		_store.close();
	}

}
