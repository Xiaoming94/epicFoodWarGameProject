package networking;

import java.io.IOException;
import java.util.ArrayList;

import networking.Network.EntitySender;
import networking.Network.RequestConnection;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SpaghettiServer {
	
	private Server server;
	private ArrayList<Connection> clientsConnected;
	
	public SpaghettiServer(int TCPPort, int UDPPort) throws IOException{
		server = new Server();
		server.start();
		Network.register(server);
		
		
		server.bind(TCPPort, UDPPort);
		clientsConnected = new ArrayList();
		
		server.addListener(new Listener(){
			public void received(Connection connection, Object object){
				//RECIEVE handling here
				
				if(object instanceof RequestConnection){
					System.out.println("Connection request recieved from: " + ((RequestConnection)object).name);
					clientsConnected.add(connection);
					
				}else if(object instanceof EntitySender){
					
				}
			}
		});
	}
	
	
}
