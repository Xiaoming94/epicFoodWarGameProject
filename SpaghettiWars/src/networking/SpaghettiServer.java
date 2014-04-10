package networking;

import gamecomponent.GameMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utilities.NameTexture;
import networking.Network.ObstacleSender;
import networking.Network.PlayerSender;
import networking.Network.RequestConnection;
import networking.Network.SimpleMessage;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SpaghettiServer {
	
	private Server server;
	private ArrayList<Connection> clientsConnected;
	private Map playerMap;
	
	public SpaghettiServer(int TCPPort, int UDPPort) throws IOException{
		server = new Server();
		server.start();
		Network.register(server);
		
		playerMap = new HashMap();
		
		server.bind(TCPPort, UDPPort);
		clientsConnected = new ArrayList<Connection>();
		
		server.addListener(new Listener(){
			public void received(Connection connection, Object object){
				//RECIEVE handling here
				
				if(object instanceof RequestConnection){
					System.out.println("Connection request recieved from: " + ((RequestConnection)object).name);
					clientsConnected.add(connection);
					
					SimpleMessage msg = new SimpleMessage();
					msg.text = "Server: message received, response sent by TCP";
					connection.sendTCP(msg);
					msg.text = "Server: message received, response sent by UDP(attempt 1/2)";
					connection.sendUDP(msg);
					msg.text = "Server: message received, response sent by UDP(attempt 2/2)";
					connection.sendUDP(msg);
					
				}else if(object instanceof ObstacleSender){
					
				}else if(object instanceof SimpleMessage){
					System.out.println(((SimpleMessage)object).text);
				}else if(object instanceof PlayerSender){
					
				}
			}
		});
	}
	
	//prototyp
	public void sendMap(GameMap map){
		for(int i = 0; i < map.getObstacles().size(); i++){
			ObstacleSender obs = new ObstacleSender();
			obs.yPos = map.getObstacles().get(i).getY();
			obs.xPos = map.getObstacles().get(i).getX();
			obs.spriteName = ((NameTexture)map.getObstacles().get(i).getSprite().getTexture()).getName();
			obs.rotation = map.getObstacles().get(i).getSprite().getRotation();
		}
	}
	
	public void messageAllClients(String msg){
		SimpleMessage text = new SimpleMessage();
		text.text = msg;
		for(int i = 0; i < clientsConnected.size(); i++){
			clientsConnected.get(i).sendTCP(text);
		}
	}
}
