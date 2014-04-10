package networking;

import java.io.IOException;

import utilities.Vector;
import networking.Network.PlayerSender;
import networking.Network.RequestConnection;
import networking.Network.SimpleMessage;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class SpaghettiClient {
	private Client client;
	private String clientName;
		
	
		//connectionTimeBlock is the maximum number of milliseconds the connect method will block, if it times out or connection otherwise fails, an exeption is thrown.
	public SpaghettiClient(int TCPPort, int UDPPort, int connectionTimeBlock, String IPAddress, String clientName) throws IOException{
		client = new Client();
		client.start();
		
		Network.register(client);
		
		client.connect(connectionTimeBlock, IPAddress, TCPPort, UDPPort);
		
		RequestConnection request = new RequestConnection();
		this.clientName = clientName;
		request.name = clientName;
		client.sendTCP(request);
		
		
		client.addListener(new Listener(){
			public void received(Connection connection, Object object){
				if(object instanceof SimpleMessage){
					System.out.println(((SimpleMessage)object).text);
				}
			}
		});
	}
	
	public void sendPlayerPosition(int xPos, int yPos, Vector vector, double rotation){
		PlayerSender playerSender = new PlayerSender();
		playerSender.xPos = xPos;
		playerSender.yPos = yPos;
		playerSender.vector = vector;
		playerSender.rotation = rotation;
		client.sendUDP(playerSender);
	}
}
