package entities;

import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Projectile extends Entity{
	
	private final int damage;
	private double RANGE;
	private boolean dead;
	
	public Projectile(double x, double y, Vector vector, Sprite sprite, int damage, double range){
		super(x, y, vector, sprite);
		this.damage = damage;
		this.RANGE = range;
		this.dead = false;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public double getRange(){
		return RANGE;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public void kill(){
		dead = true;
	}
	
	public void update(){
		this.RANGE--;
		
		if(RANGE < 1)
			this.dead = true;
		
		this.move();
	}
}
