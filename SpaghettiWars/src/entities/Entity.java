package entities;

import utilities.Position;
import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {
	
	private Position position;
	private Vector vector;
	private Sprite sprite;
	private double speedFactor = 2;
	
	private static int thisClientID;
	private static int objectIDgenerator = 0;

	private int ID;
	
	public Entity(double x, double y, Sprite sprite){
		this(x, y, new Vector(0,0), sprite);
	}
	
	public Entity(double x, double y, Vector vector, Sprite sprite){
		this(x,y,vector, sprite, thisClientID, ++objectIDgenerator);
	}	
	
	public Entity(double x, double y, Vector vector, Sprite sprite, int clientID, int objectID){
		position = new Position(x,y);
		this.sprite = sprite;
		this.vector = vector;
		
		this.sprite.setX((float)x-this.sprite.getWidth()/2);
		this.sprite.setY((float)y-this.sprite.getHeight()/2);
		
		ID = clientID*1000000 + objectID;
	}
	
	public void move(){
		position.setX(position.getX() + vector.getDeltaX());
		position.setY(position.getY() + vector.getDeltaY());
		
		this.sprite.setX((float)position.getX()-this.sprite.getWidth()/2);
		this.sprite.setY((float)position.getY()-this.sprite.getHeight()/2);
	}
	
	public void updateSpritePos(){
		this.sprite.setX((float)position.getX()-this.sprite.getWidth()/2);
		this.sprite.setY((float)position.getY()-this.sprite.getHeight()/2);
	}
	
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void stop(){
		vector = new Vector(0,0);
	}
	
	public double getX(){
		return position.getX();
	}
	
	public double getY(){
		return position.getY();
	}
	
	public void setX(double x){
		this.position.setX(x);
	}
	
	public void setY(double y){
		this.position.setY(y);
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

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
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

	public int getID() {
		return ID;
	}
	
	public static void setThisClientID(int thisClientID){
		Entity.thisClientID = thisClientID;
		System.out.println("this client id: " + thisClientID);
	}
	
	public static int getThisClientID(){
		return Entity.thisClientID;
	}
}