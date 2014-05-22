//Author: Joakim
//Modified heavily by Jimmy
//Modified slightly by Louise

//A class which handles the network communication when the application acts as a client.

package networking;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import utilities.MutexHandler;
import utilities.Position;
import utilities.TextureHandler;
import utilities.Vector;
import networking.Network.DietPillSender;
import networking.Network.FatSender;
import networking.Network.IDgiver;
import networking.Network.PlayerKiller;
import networking.Network.PlayerSender;
import networking.Network.PowerUpSender;
//import networking.Network.PowerUpSender;
import networking.Network.ProjectileRemover;
import networking.Network.ProjectileSender;
import networking.Network.RequestConnection;
import networking.Network.RequestDisconnection;
import networking.Network.SimpleMessage;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import entities.DietPill;
import entities.Energydrink;
import entities.Entity;
import entities.Meatball;
import entities.Pizza;
import entities.Player;
import entities.PowerUp;
import entities.Projectile;
import entities.ProjectileState;
import entities.PizzaSlice;
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

		model.addObserver(this);

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
					model.setControllerRunning(true);
					Thread ct = new Thread(new Controller(model,
							new ControllerUtilClient()));
					ct.start();
					model.setGameActive(true);
				} else if (object instanceof PlayerSender) {
					PlayerSender playerSender = (PlayerSender) object;

					MutexHandler.getInstance().getOtherPlayersMutex().lock();
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
								(new Sprite(TextureHandler.getInstance()
										.getTextureByName("ful.png"))),
								playerSender.speed, playerSender.ID/1000000, playerSender.ID%1000000));
					}
					MutexHandler.getInstance().getOtherPlayersMutex().unlock();
				} else if (object instanceof ProjectileSender) {
					ProjectileSender projectileSender = (ProjectileSender) object;

					
					Projectile p = null;
					if (projectileSender.projectileTypeNumber == 2) {
						p = new Pizza(projectileSender.xPos,
								projectileSender.yPos, new Vector(0, 0),
								new Sprite(TextureHandler.getInstance()
										.getTextureByName("pizza.png")),
								new Position(projectileSender.targetPosX,
										projectileSender.targetPosY),
								projectileSender.ID / 1000000,
								projectileSender.ID % 1000000, model
										.getPizzaSlicer());
						p.setVector(new Position(projectileSender.targetPosX,
								projectileSender.targetPosY));
					} else if (projectileSender.projectileTypeNumber == 1) {
						p = new Meatball(projectileSender.xPos,
								projectileSender.yPos, new Vector(
										projectileSender.vectorDX,
										projectileSender.vectorDY), new Sprite(
										TextureHandler.getInstance()
												.getTextureByName(
														"Kottbulle.png")),
								projectileSender.ID / 1000000,
								projectileSender.ID % 1000000);
						// p.setVector(new Position(projectileSender.vectorDX,
						// projectileSender.vectorDY));
					} else if(projectileSender.projectileTypeNumber > 2 && projectileSender.projectileTypeNumber < 11){// 3-10 should be pizzaslices, fix dat shit yo
						p = new PizzaSlice(projectileSender.xPos,
								projectileSender.yPos, new Vector(
										projectileSender.vectorDX,
										projectileSender.vectorDY), new Sprite(
										TextureHandler.getInstance().getTextureByName("PizzaSlice" + (projectileSender.projectileTypeNumber -2) + ".png")),
								projectileSender.ID / 1000000,
								projectileSender.ID % 1000000, projectileSender.projectileTypeNumber);
						p.getVector().setVectorByDegree(p.getSpeed(), 68-45*(projectileSender.projectileTypeNumber-3));
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
						MutexHandler.getInstance().getOtherPlayersMutex().lock();
						playerMap.clear();
						MutexHandler.getInstance().getOtherPlayersMutex().unlock();
					} else {
						MutexHandler.getInstance().getOtherPlayersMutex().lock();
						playerMap.remove(request.playerID);
						MutexHandler.getInstance().getOtherPlayersMutex().unlock();
					}
				} else if (object instanceof ProjectileRemover) {
					int i = 0;
					boolean found = false;
					ProjectileRemover pr = (ProjectileRemover) object;
					MutexHandler.getInstance().getProjectilesMutex().lock();
					for (Projectile p : model.getProjectiles()) {
						if (p.getID() == pr.projectileID) {
							found = true;
							break;
						}
						i++;

					}
					if (found) {
						model.getProjectiles().remove(i);
					}
					MutexHandler.getInstance().getProjectilesMutex().unlock();
					
				} else if (object instanceof PlayerKiller) {
					PlayerKiller pk = (PlayerKiller) object;

					if (model.getPlayer().getID() == pk.ID) {
						model.getPlayer().kill();
						MutexHandler.getInstance().getStillEntitiesMutex().lock();
						model.getStillEntitys().add(model.getPlayer());
						MutexHandler.getInstance().getStillEntitiesMutex().unlock();
						model.createPlayer(model.playerSpawnX,
								model.playerSpawnY);
					}
					
					else {
						int i = 0;
						Player found = null;
						MutexHandler.getInstance().getOtherPlayersMutex().lock();
						Collection<Player> players = model.getOtherPlayers().values();
						for (Player p : players){
							System.out.println("Player ID: " + p.getID());
							System.out.println("PlayerKiller ID: " + pk.ID);
							if (p.getID() == pk.ID) {
								p.kill();
								found = p;
								break;
							}
							i++;
						}
						MutexHandler.getInstance().getOtherPlayersMutex().unlock();
						
						if (found != null) {
							MutexHandler.getInstance().getOtherPlayersMutex().lock();
							model.getOtherPlayers().remove(i);
							MutexHandler.getInstance().getOtherPlayersMutex().unlock();
							
							MutexHandler.getInstance().getStillEntitiesMutex().lock();
							model.getStillEntitys().add(found);
							MutexHandler.getInstance().getStillEntitiesMutex().unlock();
						}
					}
				} else if(object instanceof PowerUpSender){
					PowerUpSender pus = (PowerUpSender)object;
					if(pus.powerupTypeNumber == 1){
						
						PowerUp pu = new Energydrink(pus.x, pus.y, new Sprite(
								TextureHandler.getInstance().getTextureByName("extremelyuglydrink.png")));
						model.getPickUps().add(pu);
					}else if(pus.powerupTypeNumber == 2){
						PowerUp pu = new DietPill(pus.x, pus.y, new Sprite(
								TextureHandler.getInstance().getTextureByName("dietpill.png")));
						model.getPickUps().add(pu);
					}
				
				}

			}
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
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
		} else if(p instanceof Meatball){
			projectileSender.projectileTypeNumber = 1;
		}else if(p instanceof PizzaSlice){
			projectileSender.projectileTypeNumber = ((PizzaSlice) p).getTypeNumber();
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
			e.printStackTrace();
		}
	}

	public void disconnect() {
		stop();
		RequestDisconnection request = new RequestDisconnection();
		request.playerID = model.getPlayer().getID();
		request.clientID = Entity.getThisClientID();
		client.sendTCP(request);
		client.close();
	}

	@Override
	public void update(Observable arg0, Object arg) {
		if (arg instanceof Projectile) {
			Projectile p = (Projectile) arg;

			if (p.getState() == ProjectileState.FLYING)
				sendProjectile(p);
		} else if (arg instanceof String) {
			String s = (String) arg;

			if (s == "disconnect")
				this.disconnect();
		} else if (arg instanceof DietPill) {
			DietPillSender dps = new DietPillSender();
			dps.playerID = model.getPlayer().getID();
			client.sendUDP(dps);
		}

	}
}
