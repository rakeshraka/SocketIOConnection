package com.raka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;

@SpringBootApplication
public class SocketIoTestApplication {
	
	 @Value("${rt-server.host}")
	    private String host;

	    @Value("${rt-server.port}")
	    private Integer port;

	    
	    @Bean
	    public SocketIOServer socketIOServer() {

	        SocketConfig socketConfig = new SocketConfig();
	        socketConfig.setTcpNoDelay(true);
	        socketConfig.setSoLinger(0);
	    	
	    	Configuration config = new Configuration();
	        config.setHostname(host);
	        config.setPort(port);
	        config.setSocketConfig(socketConfig);
	        System.out.println("Connection stablished with post"+port);
	        
	        
	        
	        return new SocketIOServer(config);
	        
	    }

	public static void main(String[] args) {
		SpringApplication.run(SocketIoTestApplication.class, args);
	}

}
