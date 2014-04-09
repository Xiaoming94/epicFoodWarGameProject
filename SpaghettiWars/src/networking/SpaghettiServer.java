package networking;

import java.io.IOException;

import networking.Network.RequestConnection;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SpaghettiServer {
	
	Server server;
	
	public SpaghettiServer(int TCPPort, int UDPPort) throws IOException{
		server = new Server();
		server.start();
		Network.register(server);
		
		server.bind(TCPPort, UDPPort);
		
		server.addListener(new Listener(){
			public void received(Connection connection, Object object){
				//RECIEVE handling here
				
				if(object instanceof RequestConnection){
					System.out.println("registerNameClass recieved from: " + connection.getID());
					
				}
			}
		});
	}
	
	
}
