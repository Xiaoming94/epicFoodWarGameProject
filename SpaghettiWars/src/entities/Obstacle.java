package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Obstacle extends Entity {
	
	public Obstacle(double x, double y, Sprite sprite){
		super(x, y, sprite);
	}
	
	public abstract boolean collides(Pizza p);
}
