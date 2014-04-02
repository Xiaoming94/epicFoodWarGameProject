package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import utilities.Vector;

public class Meatball extends Projectile {
	
	private static final Texture TEXTURE = new Texture("assets/bucket.png");
	private static final int DAMAGE = 1;
	private static final int RANGE = 10;
	
	public Meatball(double x, double y, Vector vector){
		super(x, y, vector, new Sprite(TEXTURE), DAMAGE, RANGE);
	}
	
}
