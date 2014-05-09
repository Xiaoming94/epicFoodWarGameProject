package networking;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import utilities.Position;
import utilities.Vector;
import networking.Network.FatSender;
import networking.Network.IDgiver;
import networking.Network.PlayerKiller;
import networking.Network.PlayerSender;
import networking.Network.ProjectileRemover;
import networking.Network.ProjectileSender;
import networking.Network.RequestConnection;
import networking.Network.RequestDisconnection;
import networking.Network.SimpleMessage;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import entities.Entity;
import entities.Meatball;
import entities.Pizza;
import entities.Player;
import entities.Projectile;
import gamecomponent.Model;
import gamecomponent.controllerparts.Controller;
import gamecomponent.controllerparts.ControllerUtilClient;

public class SpaghettiClient implements Runnable, SpaghettiFace {
	private Client client;
	private Map<Integer, Player> playerMap;
	private Model model;
	private boolean running = false;
	private Thread thread;

	// connectionTimeBlock is the maximum number of milliseconds the connect
	// method will block, if it times out or connection otherwise fails, an
	// exeption is thrown.
	public SpaghettiClient(int TCPPort, int UDPPort, int connectionTimeBlock,
			String IPAddress, String clientName, Model mod,
			Map<Integer, Player> otherPlayerMap) throws IOException {
		client = new Client();
		client.start();

		Network.register(client);

		model = mod;

		playerMap = otherPlayerMap;

		client.connect(connectionTimeBlock, IPAddress, TCPPort, UDPPort);

		RequestConnection request = new RequestConnection();
		request.name = clientName;
		client.sendTCP(request);

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {

				if (object instanceof SimpleMessage) {
					System.out.println(((SimpleMessage) object).text);
				} else if (object instanceof IDgiver) {
					IDgiver idgiver = (IDgiver) object;
					Entity.setThisClientID(idgiver.ID);
					Thread ct = new Thread(new Controller(model,
							new ControllerUtilClient()));
					ct.start();
					model.setGameActive(true);
				} else if (object instanceof PlayerSender) {
					PlayerSender playerSender = (PlayerSender) object;
					
					model.getOtherPlayersMutex().lock();
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
						((Player) playerMap.get(playerSender.ID))
								.setWeight(playerSender.fatPoints);
					} else {
						playerMap.put(playerSender.ID, new Player("Sir Derp",
								playerSender.xPos, playerSender.yPos,
								(new Sprite(model.getTextureHandler()
										.getTextureByName("ful.png"))),
								playerSender.speed));
					}
					model.getOtherPlayersMutex().unlock();
				} else if (object instanceof ProjectileSender) {
					ProjectileSender projectileSender = (ProjectileSender) object;

					Projectile p;
					if (projectileSender.projectileTypeNumber == 2) {
						p = new Pizza(projectileSender.xPos,
								projectileSender.yPos, new Vector(0, 0),
								new Sprite(model.getTextureHandler()
										.getTextureByName("pizza.png")),
								new Position(projectileSender.targetPosX,
										projectileSender.targetPosY),
								projectileSender.ID / 1000000,
								projectileSender.ID % 1000000);
						p.setVector(new Position(projectileSender.targetPosX,
								projectileSender.targetPosY));
					} else {
						p = new Meatball(projectileSender.xPos,
								projectileSender.yPos, new Vector(
										projectileSender.vectorDX,
										projectileSender.vectorDY), new Sprite(
										model.getTextureHandler()
												.getTextureByName(
														"Kottbulle.png")),
								projectileSender.ID / 1000000,
								projectileSender.ID % 1000000);
						// p.setVector(new Position(projectileSender.vectorDX,
						// projectileSender.vectorDY));
					}
					model.addProjectile(p);
				} else if (object instanceof FatSender) {
					FatSender fatSender = (FatSender) object;
					if (fatSender.ID == model.getPlayer().getID()) {
						model.getPlayer().setWeight(fatSender.fatPoints);
					}
				} else if (object instanceof RequestDisconnection) {
					RequestDisconnection request = (RequestDisconnection) object;
					if (request.clientID == 0) {
						stop();
						model.getOtherPlayersMutex().lock();
						playerMap.clear();
						model.getOtherPlayersMutex().unlock();
					} else {
						model.getOtherPlayersMutex().lock();
						playerMap.remove(request.playerID);
						model.getOtherPlayersMutex().unlock();
					}
				}else if(object instanceof ProjectileRemover){
					System.out.println("client receives eating message");
					int i = 0;
					boolean found = false;
					ProjectileRemover pr = (ProjectileRemover) object;
					for(Projectile p: model.getProjectiles()){
						if(p.getID() == pr.projectileID){
							found = true;
							break;
						}
						i++;
		
					}
					System.out.println(found);
					System.out.println(pr.projectileID);
					if(found){
						model.getProjectiles().remove(i);
					}
				}else if(object instanceof PlayerKiller){
					System.out.println("playerkiller recieved");
					PlayerKiller pk = (PlayerKiller) object;
					
					if(model.getPlayer().getID() == pk.ID){
						model.getStillEntitys().add(model.getPlayer());
						model.createPlayer(model.playerSpawnX, model.playerSpawnY);
					}
					
					else{
						int i = 0;
						boolean found = false;
						Iterator<Integer> playersIterator = model.getOtherPlayers().keySet().iterator();
						while (playersIterator.hasNext())
							if(model.getOtherPlayers().get(playersIterator.next()).getID() == pk.ID){
								found = true;
								break;
							}
						if(found){
							model.getStillEntitys().add(model.getOtherPlayers().get(i));
							model.getOtherPlayers().remove(i);
						}
					}
				}
			}
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendPlayerPosition(double xPos, double yPos, Vector vector,
			double rotation, double speed) {
		PlayerSender playerSender = new PlayerSender();
		playerSender.xPos = xPos;
		playerSender.yPos = yPos;
		playerSender.vectorDX = vector.getDeltaX();
		playerSender.vectorDY = vector.getDeltaY();
		playerSender.rotation = rotation;
		playerSender.speed = speed;
		playerSender.ID = model.getPlayer().getID();

		client.sendUDP(playerSender);
	}

	public void sendProjectile(Projectile p) {
		ProjectileSender projectileSender = new ProjectileSender();
		projectileSender.xPos = p.getX();
		projectileSender.yPos = p.getY();
		projectileSender.vectorDX = p.getVector().getDeltaX();
		projectileSender.vectorDY = p.getVector().getDeltaY();
		projectileSender.ID = p.getID();
		if (p instanceof Pizza) {
			projectileSender.projectileTypeNumber = 2;
			projectileSender.targetPosX = ((Pizza) p).getTargetPosition()
					.getX();
			projectileSender.targetPosY = ((Pizza) p).getTargetPosition()
					.getY();
		} else {
			projectileSender.projectileTypeNumber = 1;
		}
		client.sendUDP(projectileSender);
	}

	public Map<Integer, Player> getPlayerMap() {
		return playerMap;
	}

	@Override
	public void run() {

		while (running) {

			sendPlayerPosition(model.getPlayer().getX(), model.getPlayer()
					.getY(), model.getPlayer().getVector(), model.getPlayer()
					.getSprite().getRotation(), model.getPlayer().getSpeed());

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("Server got interuppted");
			}
		}
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "ClientThread");
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

	public void disconnect() {
		stop();
		RequestDisconnection request = new RequestDisconnection();
		request.playerID = model.getPlayer().getID();
		request.clientID = Entity.getThisClientID();
		client.sendTCP(request);
	}

	@Override
	public void killProjectile(Projectile p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void killPlayer(Player p) {
		// TODO Auto-generated method stub
		
	}
}
