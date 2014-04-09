package networking;

import java.io.IOException;

import networking.Network.RequestConnection;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class SpaghettiClient {
	Client client;
	String clientName;
		
	
		//connectionTimeBlock is the maximum number of milliseconds the connect method will block, if it times out or connection otherwise fails, an exeption is thrown.
	public SpaghettiClient(int TCPPort, int UDPPort, int connectionTimeBlock, String IPAddress, String clientName) throws IOException{
		client = new Client();
		client.start();
		
		Network.register(client);
		
		client.connect(connectionTimeBlock, IPAddress, TCPPort, UDPPort);
		
		RequestConnection request = new RequestConnection();
		request.name = clientName;
		client.sendTCP(request);
		
		
		client.addListener(new Listener(){
			public void received(Connection connection, Object object){
				//receive handling here
			}
		});
	}
}
