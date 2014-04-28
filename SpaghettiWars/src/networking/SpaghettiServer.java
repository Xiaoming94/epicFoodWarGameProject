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

public class SpaghettiServer implements Runnable{

	private Server server;
	private ArrayList<Connection> clientsConnected;
	private Map<Integer, Player> playerMap;
	private Model model;
	private boolean running = false;
	private Thread thread;

	
	//TODO FIX MUTEX
	public SpaghettiServer(int TCPPort, int UDPPort, Model mod, Map<Integer, Player> otherPlayerMap)
			throws IOException {

		server = new Server();
		server.start();
		Network.register(server);

		this.model = mod;

		this.playerMap = otherPlayerMap;

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
								playerSender.vectorDX,
								playerSender.vectorDY);
						 ((Player) playerMap.get(connection.getID())).setRotation(playerSender.rotation);
					} else {
						// playerMap.add()
						playerMap.put(
								connection.getID(),
								new Player("" + connection.getID(),
										playerSender.xPos, playerSender.yPos,
										(new Sprite(model.getTextureHandler()
												.getTextureByName("ful.png"))),
										playerSender.speed));
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
					playerSender.vectorDX = playerMap.get(key).getVector().getDeltaX();
					playerSender.vectorDY = playerMap.get(key).getVector().getDeltaY();
					playerSender.ID = Integer.parseInt(playerMap.get(key).getName());
					playerSender.rotation = playerMap.get(key).getSprite().getRotation();
					clientsConnected.get(i).sendUDP(playerSender);
					
					//DONT FORGET TO SEND THE HOST PLAYER TOO! :) done
				}
			}
			playerSender.xPos = model.getPlayer().getX();
			playerSender.yPos = model.getPlayer().getY();
			playerSender.speed = (int) model.getPlayer().getSpeed();
			playerSender.vectorDX = model.getPlayer().getVector().getDeltaX();
			playerSender.vectorDY = model.getPlayer().getVector().getDeltaY();
			playerSender.rotation = model.getPlayer().getSprite().getRotation();
			int id = 91287;
			while(playerMap.containsKey(id)){
				id++;
			}
			playerSender.ID = id;
			clientsConnected.get(i).sendUDP(playerSender);
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000 / 20.0;
		double delta = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			while(delta >= 1){
				sendPlayersToAll();
			}
		}
	}
	
	public synchronized void start(){
		running = true;
		thread = new Thread(this, "ServerThread");
		thread.start();
	}
	
	public synchronized void stop(){
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
