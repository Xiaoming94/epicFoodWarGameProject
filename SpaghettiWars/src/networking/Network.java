package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import entities.Entity;

public class Network {

	

	public static void register(EndPoint endPoint){
		Kryo kryo = endPoint.getKryo();
		kryo.register(RequestConnection.class);
		kryo.register(EntitySender.class);
		kryo.register(SimpleMessage.class);
		kryo.register(ObstacleSender.class);
		kryo.register(PlayerSender.class);
		kryo.register(ProjectileSender.class);
		kryo.register(RequestDisconnection.class);
		kryo.register(IDgiver.class);
		kryo.register(FatSender.class);
		kryo.register(ProjectileRemover.class);
		kryo.register(PlayerKiller.class);
		kryo.register(DietPillSender.class);
		kryo.register(PowerUpSender.class); //NY
	}
	
	public static class SimpleMessage{
		public String text;
	}
	
	public static class RequestConnection{
		public String name;
	}
	
	//dont use this
	public static class EntitySender{
		public Entity entity;
	}
	
	public static class ObstacleSender{
		public double xPos;
		public double yPos;
		public String spriteName;
		public double rotation;
	}
	
	public static class PlayerSender{
		public double xPos;
		public double yPos;
		public double rotation;
		
		//public Vector vector;
		public double vectorDX;
		public double vectorDY;
		public double speed;
		public int ID;
		public double fatPoints;
	}
	
	public static class ProjectileSender{
		public double xPos;
		public double yPos;
		public double vectorDX;
		public double vectorDY;
		public int projectileTypeNumber;
		public double targetPosX;
		public double targetPosY;
		public int ID;
	}
	
	public static class IDgiver{
		public int ID;
	}
	
	public static class RequestDisconnection{
		public int playerID;
		public int clientID;
	}
	
	public static class FatSender{
		public double fatPoints;
		public int ID;
	}
	
	public static class ProjectileRemover {
		public int projectileID;
	}
	
	public static class PlayerKiller{
		public int ID;
	}
	
	public static class DietPillSender{
		public int playerID;
	}
	
	//ny
	public static class PowerUpSender{
		public double x;
		public double y;
		public int powerupTypeNumber;
	}
}
