package networking;

import entities.Entity;
import entities.Player;
import gamecomponent.GameMap;
import gamecomponent.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import utilities.NameTexture;
import networking.Network.ObstacleSender;
import networking.Network.PlayerSender;
import networking.Network.RequestConnection;
import networking.Network.RequestDisconnection;
import networking.Network.SimpleMessage;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SpaghettiServer {

	private Server server;
	private ArrayList<Connection> clientsConnected;
	private Map<Integer, Player> playerMap;
	private Model model;

	public SpaghettiServer(int TCPPort, int UDPPort, Model mod)
			throws IOException {

		server = new Server();
		server.start();
		Network.register(server);

		this.model = mod;

		playerMap = new HashMap<Integer, Player>();

		server.bind(TCPPort, UDPPort);
		clientsConnected = new ArrayList<Connection>();

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				// RECIEVE handling here

				if (object instanceof RequestConnection) {
					System.out.println("Connection request recieved from: "
							+ ((RequestConnection) object).name);
					clientsConnected.add(connection);

					SimpleMessage msg = new SimpleMessage();
					msg.text = "Server: message received, response sent by TCP";
					connection.sendTCP(msg);
					msg.text = "Server: message received, response sent by UDP(attempt 1/2)";
					connection.sendUDP(msg);
					msg.text = "Server: message received, response sent by UDP(attempt 2/2)";
					connection.sendUDP(msg);

				} else if (object instanceof ObstacleSender) {

				} else if (object instanceof SimpleMessage) {
					System.out.println(((SimpleMessage) object).text);
				} else if (object instanceof PlayerSender) {
					PlayerSender playerSender = (PlayerSender) object;

					if (playerMap.containsKey(connection.getID())) {
						((Player) playerMap.get(connection.getID()))
								.setX(playerSender.xPos);
						((Player) playerMap.get(connection.getID()))
								.setY(playerSender.yPos);
						((Player) playerMap.get(connection.getID()))
								.setSpeed(playerSender.speed);
						((Player) playerMap.get(connection.getID())).setVector(
								playerSender.vector.getDeltaX(),
								playerSender.vector.getDeltaY());
						// ((Player)
						// playerMap.get(connection.getID())).setRotation(playerSender.rotation);
					} else {
						// playerMap.add()
						playerMap.put(
								connection.getID(),
								new Player("player" + connection.getID(),
										playerSender.xPos, playerSender.yPos,
										(new Sprite(model.getTextureHandler()
												.getTextureByName("ful.png"))),
										playerSender.speed, model
												.getTextureHandler()));
					}
				} else if (object instanceof RequestDisconnection) {
					playerMap.remove(connection.getID());
					clientsConnected.remove(connection);
				}
			}
		});
	}

	// prototyp/hög med gissningar
	public void sendMap(GameMap map) {
		for (int i = 0; i < map.getObstacles().size(); i++) {
			ObstacleSender obs = new ObstacleSender();
			obs.yPos = map.getObstacles().get(i).getY();
			obs.xPos = map.getObstacles().get(i).getX();
			obs.spriteName = ((NameTexture) map.getObstacles().get(i)
					.getSprite().getTexture()).getName();
			obs.rotation = map.getObstacles().get(i).getSprite().getRotation();
		}
	}

	public void messageAllClients(String msg) {
		SimpleMessage text = new SimpleMessage();
		text.text = msg;
		for (int i = 0; i < clientsConnected.size(); i++) {
			clientsConnected.get(i).sendTCP(text);
		}
	}

	public void sendPlayersToAll(){
		for(int i = 0; i < clientsConnected.size(); i++){
			Iterator<Integer> iterator = playerMap.keySet().iterator();
			PlayerSender playerSender = new PlayerSender();
			while(iterator.hasNext()){	
				
				Integer key = iterator.next();
				
				if(key != clientsConnected.get(i).getID()){
					playerSender.xPos = playerMap.get(key).getX();
					playerSender.yPos = playerMap.get(key).getY();
					playerSender.speed = (int) playerMap.get(key).getSpeed();
					//FIX that speed is a double in entity and a int in player
					playerSender.vector = playerMap.get(key).getVector();
//					playerSender.rotation = playerMap.get(key).getRotation(); NO getRotation so far
					clientsConnected.get(i).sendUDP(playerSender);
					
					//DONT FORGET TO SEND THE HOST PLAYER TOO! :)
				}
			}
			playerSender.xPos = model.getPlayer().getX();
			playerSender.yPos = model.getPlayer().getY();
			playerSender.speed = (int) model.getPlayer().getSpeed();
			playerSender.vector = model.getPlayer().getVector();
			clientsConnected.get(i).sendUDP(playerSender);
		}
	}
}
