package com.raka.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;


@Component
public class ChatModule {
	private static final Logger log = LoggerFactory.getLogger(ChatModule.class);

    private final SocketIONamespace namespace;

    @Autowired
    public ChatModule(SocketIOServer server) {
    	System.out.println("In cathModule");
        this.namespace = server.addNamespace("/chat");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("chat", ChatMessage.class, onChatReceived());
    }

    private DataListener<ChatMessage> onChatReceived() {
        return (client, data, ackSender) -> {
            log.debug("Client[{}] - Received chat message '{}'", client.getSessionId().toString(), data);
            System.out.println("Client[{}]"+ client.getSessionId().toString()+" - Received chat message: "+ data);
            namespace.getBroadcastOperations().sendEvent("chat", data);
        };
    }

    private ConnectListener onConnected() {
    	System.out.println("In onConnected");
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            log.debug("Client[{}] - Connected to chat module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
            System.out.println("Client[{}] "+ client.getSessionId().toString() +"- Connected to chat module through: "+handshakeData.getUrl());
        };
    }

    private DisconnectListener onDisconnected() {
    	System.out.println("In onDisconnected");
        return client -> {
            log.debug("Client[{}] - Disconnected from chat module.", client.getSessionId().toString());
            System.out.println("Client[{}] "+ client.getSessionId().toString()+"- Disconnected from chat module.");
        };
    }
}
