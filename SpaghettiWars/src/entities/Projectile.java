package entities;

import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Projectile extends Entity{
	
	private final int damage;
	private final double RANGE;
	
	public Projectile(double x, double y, Vector vector, Sprite sprite, int damage, double range){
		super(x, y, vector, sprite);
		this.damage = damage;
		this.RANGE = range;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public double getRange(){
		return RANGE;
	}
}
