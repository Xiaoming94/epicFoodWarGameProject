package entities;

import utilities.Position;
import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {
	
	private double x;
	private double y;
	private Vector vector;
	private Sprite sprite;
	private double speedFactor = 2;
	
	private static int thisClientID;
	private static int objectIDgenerator = 0;

	private int clientID;
	private int objectID;
	
	public Entity(double x, double y, Sprite sprite){
		this(x, y, new Vector(0,0), sprite);
	}
	
	public Entity(double x, double y, Vector vector, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.vector = vector;
		
		this.sprite.setX((float)x-this.sprite.getWidth()/2);
		this.sprite.setY((float)y-this.sprite.getHeight()/2);
		
		clientID = thisClientID;
		objectID = ++objectIDgenerator;
	}	
	
	public Entity(double x, double y, Vector vector, Sprite sprite, int clientID, int objectID){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.vector = vector;
		
		this.sprite.setX((float)x-this.sprite.getWidth()/2);
		this.sprite.setY((float)y-this.sprite.getHeight()/2);
		
		this.clientID = clientID;
		this.objectID = objectID;
	}
	
	public void move(){
		x += vector.getDeltaX();
		y += vector.getDeltaY();
		
		this.sprite.setX((float)x-this.sprite.getWidth()/2);
		this.sprite.setY((float)y-this.sprite.getHeight()/2);
	}
	
	
	public void stop(){
		vector = new Vector(0,0);
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public Vector getVector(){
		return vector; 
	}
	
	//author: Louise & Jimmy
	public void setVector(Position pos){
		double hyp = Math.sqrt(Math.pow(pos.getX() - this.getX(), 2) + Math.pow(pos.getY() - this.getY(), 2));
		double factor = speedFactor/hyp;
		this.vector.setVector((pos.getX() - this.getX()) * factor,(pos.getY() - this.getY()) * factor );
	}
	
	public void setVector(double dx, double dy){
		vector.setDeltaX(dx);
		vector.setDeltaY(dy);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void setSpeed(double speed){
		speedFactor = speed;
	}
	
	public void setRotation(double rotation){
		sprite.setRotation((float) rotation);
	}
	
	public void updateVector(){
		this.getVector().setLengthTo(this.getSpeed());
	}
	
	public double getSpeed(){
		return speedFactor;
	}

	public int getClientID() {
		return clientID;
	}

	public int getObjectID() {
		return objectID;
	}
	
	public static void setThisClientID(int thisClientID){
		Entity.thisClientID = thisClientID;
	}
}