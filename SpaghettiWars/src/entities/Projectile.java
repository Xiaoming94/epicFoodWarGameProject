package entities;

import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Projectile extends Entity{
	
	private final int damage;
	private double range;
	private boolean dead;
	
	public Projectile(double x, double y, Vector vector, Sprite sprite, int damage, double range){
		super(x, y, vector, sprite);
		this.damage = damage;
		this.range = range;
		this.dead = false;
	}
	
	public Projectile(double x, double y, Vector vector, Sprite sprite, int damage, double range, int clientID, int objectID){
		super(x,y,vector,sprite, clientID, objectID);
		this.damage = damage;
		this.range = range;
		this.dead = false;
	}

    public void setRange(double range){ this.range = range; }
	
	public int getDamage(){
		return damage;
	}
	
	public double getRange(){
		return range;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public void kill(){
		dead = true;
	}
	
	public void update(){
		this.range--;
		
		if(range < 1)
			this.dead = true;
		
		this.move();
	}
}
