package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Furniture extends Obstacle{
	public Furniture(double x, double y, Sprite sprite){
		super(x, y, sprite, false);
        this.setType(Type.FURNITURE);
	}
}
