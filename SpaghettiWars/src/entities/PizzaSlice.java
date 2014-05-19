package entities;

import java.util.List;
import java.util.Map;

import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class PizzaSlice extends Projectile{
	
	private int typeNumber;
	
	public PizzaSlice(double x, double y, Vector vector, Sprite sprite, int typeNumber){
		super(x,y,vector,sprite,1,50);
		createPizzaSlice(typeNumber);
	}
	
	public PizzaSlice(double x, double y, Vector vector, Sprite sprite,
			int clientID, int objectID, int typeNumber) {
		super(x, y, vector, sprite, 1, 50, clientID, objectID);	
		createPizzaSlice(typeNumber);
	}
	
	private void createPizzaSlice(int typeNumber){
		this.typeNumber = typeNumber;
		this.setSpeed(10);
		this.setState(ProjectileState.FLYING);
	}
	
	public int getTypeNumber(){
		return typeNumber;
	}

	public void update(List<Obstacle> obstacles,
			Map<Integer, Player> playerlist, Player player) {
		
		super.update();
		
		for (Obstacle o : obstacles){
            if (collidingWith(o)) {
                this.kill();
                this.setState(ProjectileState.STILL);
            }
        }
	}
	
	private boolean collidingWith(Obstacle o){
		return this.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle());
	}
}
