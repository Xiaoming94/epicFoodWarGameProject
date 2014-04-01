package entities;

import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {
	
	private double x;
	private double y;
	private Vector vector;
	private Sprite sprite;
	
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
	
	public void setVector(double dx, double dy){
		vector.setDeltaX(dx);
		vector.setDeltaY(dy);
	}
	
	public Sprite getSprite(){
		return sprite;
	}


}
