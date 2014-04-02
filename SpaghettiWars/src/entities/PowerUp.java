package entities;

import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class PowerUp extends Entity{
	
	private int duration;
	
	public PowerUp(double x, double y, Sprite sprite, int duration){
		super(x, y, sprite);
		this.duration = duration;
	}
	
	public PowerUp(double x, double y, Vector vector, Sprite sprite, int duration){
		super(x, y, vector, sprite);
		this.duration = duration;
	}
	
	public void setActive(){
		//????
	}
	
	// what is this? 
	public abstract void applyEffects(Entity player);
	
	public abstract void stopEffects(Entity player);

}
