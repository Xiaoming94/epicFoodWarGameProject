//Author: Louise

//A class to represent all mutual functionality of the PowerUp entities.

package entities;

import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class PowerUp extends Entity{
	
	public PowerUp(double x, double y, Sprite sprite){
		super(x, y, sprite);
	}
	
	public PowerUp(double x, double y, Vector vector, Sprite sprite, int duration){
		super(x, y, vector, sprite);
	}
	
	public abstract void applyEffects(Entity player);
	
	public abstract void stopEffects(Entity player);

	public abstract void update();
}
