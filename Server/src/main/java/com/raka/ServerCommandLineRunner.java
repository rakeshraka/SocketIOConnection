package com.raka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;


@Component
public class ServerCommandLineRunner implements CommandLineRunner {

	private final SocketIOServer server;

	@Autowired
	public ServerCommandLineRunner(SocketIOServer server) {
		System.out.println("server instance created in commandline runner");
		this.server = server;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("In run method of commandline runner");
		server.addConnectListener(new ConnectListener() {			
			@Override
			public void onConnect(SocketIOClient client) {
				System.out.println("Client connect");
				client.sendEvent("chat", "What's Up Man");				
			}
		});
		
		server.addDisconnectListener(new DisconnectListener() {
			
			@Override
			public void onDisconnect(SocketIOClient client) {
				System.out.println("Client disconnect");
				client.disconnect();				
			}
		});
		
		server.start();
	}

}
