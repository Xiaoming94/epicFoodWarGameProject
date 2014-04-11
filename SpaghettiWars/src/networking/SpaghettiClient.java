package networking;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utilities.Vector;
import networking.Network.PlayerSender;
import networking.Network.RequestConnection;
import networking.Network.SimpleMessage;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import entities.Player;
import gamecomponent.Model;

public class SpaghettiClient implements Runnable{
	private Client client;
	private String clientName;
	private Map<Integer, Player> playerMap;
	private Model model;
	private boolean running = false;
	private Thread thread;

	// connectionTimeBlock is the maximum number of milliseconds the connect
	// method will block, if it times out or connection otherwise fails, an
	// exeption is thrown.
	public SpaghettiClient(int TCPPort, int UDPPort, int connectionTimeBlock,
			String IPAddress, String clientName, Model mod) throws IOException {
		client = new Client();
		client.start();

		Network.register(client);

		model = mod;

		playerMap = new HashMap<Integer, Player>();

		client.connect(connectionTimeBlock, IPAddress, TCPPort, UDPPort);

		RequestConnection request = new RequestConnection();
		this.clientName = clientName;
		request.name = clientName;
		client.sendTCP(request);

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof SimpleMessage) {
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
								playerSender.vector.getDeltaX(),
								playerSender.vector.getDeltaY());
						// ((Player)
						// playerMap.get(playerSender.ID)).setRotation(playerSender.rotation);
					} else {
						// playerMap.add()
						playerMap.put(
								playerSender.ID,
								new Player("player" + playerSender.ID,
										playerSender.xPos, playerSender.yPos,
										(new Sprite(model.getTextureHandler()
												.getTextureByName("ful.png"))),
										playerSender.speed));
					}
				}
			}
		});
	}

	public void sendPlayerPosition(double xPos, double yPos, Vector vector,
			double rotation, double speed) {
		PlayerSender playerSender = new PlayerSender();
		playerSender.xPos = xPos;
		playerSender.yPos = yPos;
		playerSender.vector = vector;
		playerSender.rotation = rotation;
		playerSender.speed = speed;
		client.sendUDP(playerSender);
	}
	
	public Map getPlayerMap(){
		return playerMap;
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
				sendPlayerPosition(model.getPlayer().getX(), model.getPlayer().getY(), model.getPlayer().getVector(), model.getPlayer().getSprite().getRotation(), model.getPlayer().getSpeed());				
			}
		}
	}
	
	public synchronized void start(){
		running = true;
		thread = new Thread(this, "ClientThread");
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
