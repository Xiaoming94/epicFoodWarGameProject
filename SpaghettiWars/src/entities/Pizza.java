package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

import utilities.Position;
import utilities.Vector;

public class Pizza extends Projectile{
	
	private Position targetPos;
	
	public Pizza(double x, double y, Vector v, Sprite sprite, Position targetPos){
		super(x, y, v, sprite, 5, 1000);
		this.targetPos = targetPos;
	}
	
	public Position getTargetPosition(){
		return targetPos;
	}
}
