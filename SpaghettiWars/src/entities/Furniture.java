//Author: Louise & Jimmy

//A class to represent a Furniture obstacle in the game.

package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Furniture extends Obstacle{
	public Furniture(double x, double y, Sprite sprite){
		super(x, y, sprite);
	}

	@Override
	public boolean collides(Pizza p) {
		return false;
	}
}
