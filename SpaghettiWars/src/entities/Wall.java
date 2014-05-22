//Author: Louise & jimmy

//A class to represent a Wall obstacle.

package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Wall extends Obstacle {
	

	public Wall(double x, double y, Sprite sprite){
		
		super(x, y,sprite);
	}

	@Override
	public boolean collides(Pizza p) {
		return true;
	}
	
	
	
}
