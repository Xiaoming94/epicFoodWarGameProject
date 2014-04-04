package entities;



import utilities.Position;
import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {
	
	private double x;
	private double y;
	private Vector vector;
	private Sprite sprite;
	private double speedFactor;
	
	public Entity(double x, double y, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		vector = new Vector(0.0,0.0);
	}
	
	public Entity(double x, double y, Vector vector, Sprite sprite){
		this.x = x;
		this.y = y;
		this.vector = vector;
		this.sprite = sprite;
	}
	
	public Entity(double x, double y, double dx, double dy, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		vector = new Vector(dx, dy);
	}
	
	public void move(){
		x += vector.getDeltaX();
		y += vector.getDeltaY();
		
		this.sprite.setX((float)x);
		this.sprite.setY((float)y);
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


}
