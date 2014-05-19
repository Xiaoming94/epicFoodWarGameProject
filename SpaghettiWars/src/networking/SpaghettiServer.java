package networking;

import entities.DietPill;
import entities.Energydrink;
import entities.Entity;
import entities.Meatball;
import entities.Pizza;
import entities.PizzaSlice;
import entities.Player;
import entities.PowerUp;
import entities.Projectile;
import entities.ProjectileState;
import gamecomponent.GameMap;
import gamecomponent.Model;
import gamecomponent.controllerparts.Controller;
import gamecomponent.controllerparts.ControllerUtilServer;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import utilities.NameTexture;
import utilities.Position;
import utilities.Vector;
import networking.Network.DietPillSender;
import networking.Network.FatSender;
import networking.Network.IDgiver;
import networking.Network.ObstacleSender;
import networking.Network.PlayerKiller;
import networking.Network.PlayerSender;
import networking.Network.PowerUpSender;
import networking.Network.ProjectileRemover;
import networking.Network.ProjectileSender;
import networking.Network.RequestConnection;
import networking.Network.RequestDisconnection;
import networking.Network.SimpleMessage;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SpaghettiServer implements Runnable, SpaghettiFace {

	private Server server;
	private Map<Integer, Connection> clientsConnected;
	private Map<Integer, Integer> polling;
	private Integer clientCounter = 1;
	private Map<Integer, Player> playerMap;
	private Model model;
	private boolean running = false;
	private Thread thread;

	// TODO FIX MUTEX
	public SpaghettiServer(int TCPPort, int UDPPort, Model mod,
			Map<Integer, Player> otherPlayerMap) throws IOException {

		server = new Server();
		server.start();
		Network.register(server);

		this.model = mod;
		
		model.addObserver(this);

		Entity.setThisClientID(1);
		model.setControllerRunning(true);
		ControllerUtilServer cus = new ControllerUtilServer();
		Controller con = new Controller(model, cus);
		cus.setParent(con);
		Thread ct = new Thread(con);
		ct.start();

		this.playerMap = otherPlayerMap;

		server.bind(TCPPort, UDPPort);
		clientsConnected = new HashMap<Integer, Connection>();
		polling = new HashMap<Integer, Integer>();

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				// RECIEVE handling here

				if (object instanceof RequestConnection) {
					System.out.println("Connection request recieved from: "
							+ ((RequestConnection) object).name);

					clientCounter++;
					clientsConnected.put(clientCounter, connection);
					polling.put(
							clientCounter,
							(int) (System.currentTimeMillis() % Integer.MAX_VALUE));

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
					
					for(int i = 0; i < model.getPickUps().size(); i++){
						PowerUpSender pus = new PowerUpSender();
						pus.x = model.getPickUps().get(i).getX();
						pus.y = model.getPickUps().get(i).getY();
						pus.powerupTypeNumber = -1;
						if(model.getPickUps().get(i) instanceof Energydrink){
							pus.powerupTypeNumber = 1;
						}else if(model.getPickUps().get(i) instanceof DietPill){
							pus.powerupTypeNumber = 2;
						}
						
						
						connection.sendUDP(pus);
					}

				} else if (object instanceof ObstacleSender) {

				} else if (object instanceof SimpleMessage) {
					System.out.println(((SimpleMessage) object).text);

				} else if (object instanceof PlayerSender) {
					PlayerSender playerSender = (PlayerSender) object;

					polling.put(
							playerSender.ID / 1000000,
							(int) (System.currentTimeMillis() % Integer.MAX_VALUE));

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
					} else{
						boolean isDead = false;
						
						model.getStillEntitiesMutex().lock();
						for(Entity e : model.getStillEntitys())
							if(e.getID() == playerSender.ID)
								isDead = true;
						model.getStillEntitiesMutex().unlock();
						
						if(!isDead){
							playerMap.put(playerSender.ID, new Player("sir derp",
									playerSender.xPos, playerSender.yPos,
									(new Sprite(model.getTextureHandler()
											.getTextureByName("ful.png"))),
									playerSender.speed, playerSender.ID/1000000, playerSender.ID%1000000));
						}
					}
					model.getOtherPlayersMutex().unlock();
				} else if (object instanceof ProjectileSender) {
					ProjectileSender projectileSender = (ProjectileSender) object;

					Projectile p = null;
					model.getProjectilesMutex().lock();
					if (projectileSender.projectileTypeNumber == 2) {
						p = new Pizza(projectileSender.xPos,
								projectileSender.yPos, new Vector(0, 0),
								new Sprite(model.getTextureHandler()
										.getTextureByName("pizza.png")),
								new Position(projectileSender.targetPosX,
										projectileSender.targetPosY),
								projectileSender.ID / 1000000,
								projectileSender.ID % 100000, model.getPizzaSlicer());
						p.setVector(new Position(projectileSender.targetPosX,
								projectileSender.targetPosY));
					} else if(projectileSender.projectileTypeNumber == 1){
						p = new Meatball(projectileSender.xPos,
								projectileSender.yPos, new Vector(
										projectileSender.vectorDX,
										projectileSender.vectorDY), new Sprite(
										model.getTextureHandler()
												.getTextureByName(
														"Kottbulle.png")),
								projectileSender.ID / 1000000,
								projectileSender.ID % 100000);
					} else if(projectileSender.projectileTypeNumber > 2 && projectileSender.projectileTypeNumber < 11){// 3-10 should be pizzaslices, fix dat shit yo
						p = new PizzaSlice(projectileSender.xPos,
								projectileSender.yPos, new Vector(
										projectileSender.vectorDX,
										projectileSender.vectorDY), new Sprite(
										model.getTextureHandler().getTextureByName("PizzaSlice" + (projectileSender.projectileTypeNumber -2) + ".png")),
								projectileSender.ID / 1000000,
								projectileSender.ID % 1000000, projectileSender.projectileTypeNumber);
						p.getVector().setVectorByDegree(p.getSpeed(), 68-45*(projectileSender.projectileTypeNumber-3));
					}
					model.getProjectilesMutex().unlock();
					model.addProjectile(p);
					forwardClientObjectUDP(object, p.getID());
				} else if (object instanceof RequestDisconnection) {
					RequestDisconnection request = (RequestDisconnection) object;
					forwardClientObjectTCP(request, request.playerID);
					model.getOtherPlayersMutex().lock();
					playerMap.remove(request.playerID);
					model.getOtherPlayersMutex().unlock();
					clientsConnected.remove(request.clientID);
					polling.remove(request.clientID);
				} else if(object instanceof DietPillSender){
					DietPillSender dps = (DietPillSender)object;
					Collection<Player> players = model.getOtherPlayers().values();
					for(Player p: players){
						if(p.getID() == dps.playerID){
							p.looseWeight(DietPill.getFatPointsLost());
						}
					}
				}
			}
			
		});
	}

	// not in use, or ever tested
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
			
			model.getOtherPlayersMutex().lock();
			Iterator<Integer> playerIterator = playerMap.keySet().iterator();
			while (playerIterator.hasNext()) {

				Integer playerKey = playerIterator.next();
				if (playerKey / 1000000 != connectionKey) {
					playerSender.xPos = playerMap.get(playerKey).getX();
					playerSender.yPos = playerMap.get(playerKey).getY();
					playerSender.speed = (int) playerMap.get(playerKey)
							.getSpeed();
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

				} else {
					FatSender fatSender = new FatSender();
					fatSender.fatPoints = playerMap.get(playerKey)
							.getFatPoint();
					fatSender.ID = playerMap.get(playerKey).getID();
					clientsConnected.get(connectionKey).sendUDP(fatSender);
				}

			}
			model.getOtherPlayersMutex().unlock();
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
		Iterator<Integer> connectionIterator = clientsConnected.keySet()
				.iterator();
		while (connectionIterator.hasNext())
			clientsConnected.get(connectionIterator.next()).sendUDP(
					projectileSender);

	}

	private void forwardClientObjectUDP(Object object, int clientID) {
		Iterator<Integer> iterator = clientsConnected.keySet().iterator();
		while (iterator.hasNext()) {
			int key = iterator.next();
			if (clientID / 1000000 != key) {
				System.out.println("inside");
				clientsConnected.get(key).sendUDP(object);
			}
		}
	}

	private void forwardClientObjectTCP(Object object, int clientID) {
		Iterator<Integer> iterator = clientsConnected.keySet().iterator();
		while (iterator.hasNext()) {
			int key = iterator.next();
			System.out.println("key: " + key);
			System.out.println("clientID: " + clientID);
			if (clientID / 1000000 != key) {
				clientsConnected.get(key).sendTCP(object);
			}
		}
	}

	public void checkClientPolling() {
		Iterator<Integer> iterator = polling.keySet().iterator();
		while (iterator.hasNext()) {
			int key = iterator.next();
			if (polling.get(key) < (int) (System.currentTimeMillis() % Integer.MAX_VALUE) - 5000) {
				System.out.println("client " + key + " disconnected");
				removeClient(key);
				break;
			}
		}
	}

	private void removeClient(int key) {
		clientsConnected.remove(key);
		polling.remove(key);
		
		model.getOtherPlayersMutex().lock();
		Iterator<Integer> iterator = playerMap.keySet().iterator();
		while (iterator.hasNext()) {
			int playerKey = iterator.next();
			if (key == playerKey / 1000000) {
				playerMap.remove(playerKey);
				break;
			}
		}
		model.getOtherPlayersMutex().unlock();
	}

	@Override
	public void run() {

		while (running) {

			sendPlayersToAll();

			checkClientPolling();

			try {
				Thread.sleep(10);
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
			e.printStackTrace();
		}
	}

	public void disconnect() {
		stop();
		Iterator<Integer> connectionIterator = clientsConnected.keySet()
				.iterator();
		while (connectionIterator.hasNext()) {
			Integer connectionKey = connectionIterator.next();
			RequestDisconnection request = new RequestDisconnection();
			request.clientID = 0;
			clientsConnected.get(connectionKey).sendTCP(request);
		}
		server.close();
	}

	@Override
	public void killProjectile(Projectile p) {
		ProjectileRemover projectileRemover = new ProjectileRemover();
		projectileRemover.projectileID = p.getID();
		Iterator<Integer> connectionIterator = clientsConnected.keySet()
				.iterator();
		while (connectionIterator.hasNext())
			clientsConnected.get(connectionIterator.next()).sendUDP(
					projectileRemover);
		
	}
	
	@Override
	public void killPlayer(Player p){
		PlayerKiller playerKiller = new PlayerKiller();
		playerKiller.ID = p.getID();
		
		Iterator<Integer> connectionIterator = clientsConnected.keySet()
				.iterator();
		while (connectionIterator.hasNext())
			clientsConnected.get(connectionIterator.next()).sendUDP(
					playerKiller);
	}
	
	public void sendPowerUp(PowerUp pu){
		PowerUpSender pus = new PowerUpSender();
		pus.x = pu.getX();
		pus.y = pu.getY();

		if(pu instanceof Energydrink){
			pus.powerupTypeNumber = 1;
		}else if(pu instanceof DietPill){
			pus.powerupTypeNumber = 2;
		}
		
		Iterator<Integer> connectionIterator = clientsConnected.keySet()
				.iterator();
		while (connectionIterator.hasNext()){
			clientsConnected.get(connectionIterator.next()).sendUDP(pus);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Projectile){
			Projectile p = (Projectile) arg;
			
			if(p.getState() == ProjectileState.EATEN)
				killProjectile(p);
			
			else if(p.getState() == ProjectileState.FLYING)
				sendProjectile(p);
		}else if(arg instanceof Player){
			Player player = (Player) arg;
			killPlayer(player);
		}else if(arg instanceof String){
			String s = (String) arg;
			
			if(s == "disconnect")
				this.disconnect();
		}
		
		else if(arg instanceof PowerUp){
			sendPowerUp((PowerUp)arg);
			System.out.println("powerup sent");
		}
	}
}
