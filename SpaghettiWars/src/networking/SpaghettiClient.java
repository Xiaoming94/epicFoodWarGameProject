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

public class SpaghettiClient {
	private Client client;
	private String clientName;
	private Map<Integer, Player> playerMap;
	private Model model;

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
				}
			}
		});
	}

	public void sendPlayerPosition(int xPos, int yPos, Vector vector,
			double rotation, int speed) {
		PlayerSender playerSender = new PlayerSender();
		playerSender.xPos = xPos;
		playerSender.yPos = yPos;
		playerSender.vector = vector;
		playerSender.rotation = rotation;
		playerSender.speed = speed;
		client.sendUDP(playerSender);
	}
}
