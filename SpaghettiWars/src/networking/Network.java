package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import entities.Entity;

public class Network {

	public static void register(EndPoint endPoint){
		Kryo kryo = endPoint.getKryo();
		kryo.register(RequestConnection.class);
		kryo.register(EntitySender.class);
		kryo.register(simpleMessage.class);
	}
	
	public static class simpleMessage{
		public String text;
	}
	
	public static class RequestConnection{
		public String name;
	}
	
	public static class EntitySender{
		public Entity entity;
	}
}
