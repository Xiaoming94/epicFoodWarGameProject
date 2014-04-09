package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import utilities.Vector;

public class Meatball extends Projectile {
	
	public Meatball(double x, double y, Vector vector, Sprite sprite){
		super(x, y, vector, sprite, 1, 300);
	}
}
