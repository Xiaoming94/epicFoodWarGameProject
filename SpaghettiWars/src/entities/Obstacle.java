package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Obstacle extends Entity {
	
	private boolean absoluteCollision;
	
	public Obstacle(double x, double y, Sprite sprite, boolean absoluteCollision){
		super(x, y, sprite);
		this.absoluteCollision = absoluteCollision;
	}
	
	public boolean getAbsoluteCollision(){
		return absoluteCollision;
	}
	
}
