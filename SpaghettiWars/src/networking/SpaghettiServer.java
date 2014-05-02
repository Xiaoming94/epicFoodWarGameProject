package networking;

import entities.Entity;
import entities.Meatball;
import entities.Pizza;
import entities.Player;
import entities.Projectile;
import gamecomponent.GameMap;
import gamecomponent.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import utilities.NameTexture;
import utilities.Position;
import utilities.Vector;
import networking.Network.FatSender;
import networking.Network.IDgiver;
import networking.Network.ObstacleSender;
import networking.Network.PlayerSender;
import networking.Network.ProjectileSender;
import networking.Network.RequestConnection;
import networking.Network.RequestDisconnection;
import networking.Network.SimpleMessage;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SpaghettiServer implements Runnable {

	private Server server;
	private Map<Integer, Connection> clientsConnected;
	private Integer clientCounter = 1;
	private Map<Integer, Player> playerMap;
	private ArrayList<Projectile> unsentProjectiles;
	private Model model;
	private boolean running = false;
	private Thread thread;

	// TODO FIX MUTEX
	public SpaghettiServer(int TCPPort, int UDPPort, Model mod,
			Map<Integer, Player> otherPlayerMap,
			ArrayList<Projectile> unsentProjectiles) throws IOException {

		server = new Server();
		server.start();
		Network.register(server);

		this.model = mod;

		this.playerMap = otherPlayerMap;
		this.unsentProjectiles = unsentProjectiles;

		server.bind(TCPPort, UDPPort);
		clientsConnected = new HashMap<Integer, Connection>();

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				// RECIEVE handling here

				if (object instanceof RequestConnection) {
					System.out.println("Connection request recieved from: "
							+ ((RequestConnection) object).name);

					clientCounter++;
					clientsConnected.put(clientCounter, connection);

					IDgiver idgiver = new IDgiver();
					idgiver.ID = clientCounter;

					connection.sendTCP(idgiver);

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

					if (playerMap.containsKey(playerSender.ID)) {
						((Player) playerMap.get(playerSender.ID))
								.setX(playerSender.xPos);
						((Player) playerMap.get(playerSender.ID))
								.setY(playerSender.yPos);
						((Player) playerMap.get(playerSender.ID))
								.setSpeed(playerSender.speed);
						((Player) playerMap.get(playerSender.ID)).setVector(
								playerSender.vectorDX, playerSender.vectorDY);
						((Player) playerMap.get(playerSender.ID))
								.setRotation(playerSender.rotation);
					} else {
						// playerMap.add()
						playerMap.put(playerSender.ID, new Player("sir derp",
								playerSender.xPos, playerSender.yPos,
								(new Sprite(model.getTextureHandler()
										.getTextureByName("ful.png"))),
								playerSender.speed));
					}
				} else if (object instanceof ProjectileSender) {
					ProjectileSender projectileSender = (ProjectileSender) object;

					Projectile p;
					if (projectileSender.projectileTypeNumber == 2) {
						p = new Pizza(projectileSender.xPos,
								projectileSender.yPos, new Vector(0, 0),
								new Sprite(model.getTextureHandler()
										.getTextureByName("pizza.png")),
								new Position(projectileSender.targetPosX,
										projectileSender.targetPosY));
						p.setVector(new Position(projectileSender.targetPosX,
								projectileSender.targetPosY));
					} else {
						p = new Meatball(projectileSender.xPos,
								projectileSender.yPos, new Vector(
										projectileSender.vectorDX,
										projectileSender.vectorDY), new Sprite(
										model.getTextureHandler()
												.getTextureByName(
														"Kottbulle.png")));
					}
					model.addProjectile(p);
					forwardClientObject(p, p.getID() / 1000000);
				} else if (object instanceof RequestDisconnection) {
					playerMap.remove(connection.getID());
					clientsConnected.remove(connection);
				}
			}
		});
	}

	// prototyp..
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

	public void sendPlayersToAll() {
		PlayerSender playerSender = new PlayerSender();
		Iterator<Integer> connectionIterator = clientsConnected.keySet()
				.iterator();
		while (connectionIterator.hasNext()) {

			Integer connectionKey = connectionIterator.next();
			Iterator<Integer> playerIterator = playerMap.keySet().iterator();
			while (playerIterator.hasNext()) {

				Integer playerKey = playerIterator.next();

				if (playerKey / 1000000 != connectionKey / 1000000) {
					playerSender.xPos = playerMap.get(playerKey).getX();
					playerSender.yPos = playerMap.get(playerKey).getY();
					playerSender.speed = (int) playerMap.get(playerKey)
							.getSpeed();
					// FIX that speed is a double in entity and a int in player
					playerSender.vectorDX = playerMap.get(playerKey)
							.getVector().getDeltaX();
					playerSender.vectorDY = playerMap.get(playerKey)
							.getVector().getDeltaY();
					playerSender.ID = playerMap.get(playerKey).getID();
					playerSender.rotation = playerMap.get(playerKey)
							.getSprite().getRotation();
					playerSender.fatPoints = playerMap.get(playerKey)
							.getFatPoint();
					clientsConnected.get(connectionKey).sendUDP(playerSender);

					// DONT FORGET TO SEND THE HOST PLAYER TOO! :) done
				} else {
					FatSender fatSender = new FatSender();
					fatSender.fatPoints = playerMap.get(playerKey).getFatPoint();
					clientsConnected.get(connectionKey).sendUDP(fatSender);
				}
			}
			playerSender.xPos = model.getPlayer().getX();
			playerSender.yPos = model.getPlayer().getY();
			playerSender.speed = (int) model.getPlayer().getSpeed();
			playerSender.vectorDX = model.getPlayer().getVector().getDeltaX();
			playerSender.vectorDY = model.getPlayer().getVector().getDeltaY();
			playerSender.rotation = model.getPlayer().getSprite().getRotation();
			playerSender.fatPoints = model.getPlayer().getFatPoint();

			playerSender.ID = model.getPlayer().getID();
			clientsConnected.get(connectionKey).sendUDP(playerSender);
		}
	}

	public void sendProjectiles() {
		for (Projectile p : unsentProjectiles) {
			ProjectileSender projectileSender = new ProjectileSender();
			projectileSender.xPos = p.getX();
			projectileSender.yPos = p.getY();
			projectileSender.vectorDX = p.getVector().getDeltaX();
			projectileSender.vectorDY = p.getVector().getDeltaY();
			if (p instanceof Pizza) {
				projectileSender.projectileTypeNumber = 2;
				projectileSender.targetPosX = ((Pizza) p).getTargetPosition()
						.getX();
				projectileSender.targetPosY = ((Pizza) p).getTargetPosition()
						.getY();
			} else {
				projectileSender.projectileTypeNumber = 1;
			}
			Iterator<Integer> connectionIterator = clientsConnected.keySet()
					.iterator();
			while (connectionIterator.hasNext())
				clientsConnected.get(connectionIterator.next()).sendUDP(
						projectileSender);
		}
		unsentProjectiles.clear();
	}

	private void forwardClientObject(Object object, int clientID) {
		Iterator<Integer> iterator = clientsConnected.keySet().iterator();
		while (iterator.hasNext()) {
			int key = iterator.next();
			if (clientID != key / 1000000) {
				clientsConnected.get(key).sendUDP(object);
			}
		}
	}

	@Override
	public void run() {

		while (running) {

			sendPlayersToAll();
			sendProjectiles();

			// projectiles is supposed to be sent, both those from other
			// clients, and those from myself

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.out.println("Server got interuppted");
			}
		}
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "ServerThread");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
