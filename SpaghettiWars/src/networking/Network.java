package networking;


import utilities.Vector;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import entities.Entity;

public class Network {

	public static void register(EndPoint endPoint){
		Kryo kryo = endPoint.getKryo();
		kryo.register(RequestConnection.class);
		kryo.register(EntitySender.class);
		kryo.register(SimpleMessage.class);
		kryo.register(ObstacleSender.class);
		kryo.register(PlayerSender.class);
		kryo.register(RequestDisconnection.class);
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
	}
	
	public static class ProjectileSender{
		public double xPos;
		public double yPos;
		public double vectorDX;
		public double vectorDY;
		public int projectileTypeNumber;
		public double targetPosX;
		public double targetPosY;
	}
	
	public static class RequestDisconnection{
		public String name;
	}
}
