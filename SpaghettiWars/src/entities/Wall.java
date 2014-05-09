package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Wall extends Obstacle {
	

	public Wall(double x, double y, Sprite sprite){
		
		super(x, y,sprite, true);
        this.setType(Type.WALL);
	}
	
}
