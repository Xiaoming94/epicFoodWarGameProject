//Author: Jimmy

//A class to represent a meatball in the game

package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

import utilities.Vector;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Meatball extends Projectile {
	
	public Meatball(double x, double y, Vector vector, Sprite sprite){
		super(x, y, vector, sprite, 5, 300);
		this.setSpeed(20);
		this.setCooldown(25);
	}
	
	public Meatball(double x, double y, Vector vector, Sprite sprite, int clientID, int objectID){
		super(x,y,vector,sprite,5, 300, clientID, objectID);
		this.setSpeed(20);
        this.setState(ProjectileState.FLYING);
        this.setCooldown(25);
	}

    @Override
    public void update(List<Obstacle> obstacles, Map<Integer, Player> playerlist, Player player) {
    	if(super.update())
    		this.setState(ProjectileState.STILL);
    	
        for (Obstacle o : obstacles){
            if (collidingWith(o)) {
                this.kill();
                this.setState(ProjectileState.STILL);
            }
        }
        Iterator<Integer> iterator = playerlist.keySet().iterator();
        Integer key;
        while(iterator.hasNext()){
        	key = iterator.next();
            if (collidingWith(playerlist.get(key))){
                this.kill();
                playerlist.get(key).gainWeight(this.getDamage());
                this.setState(ProjectileState.EATEN);
            }
        }
    }

    private boolean collidingWith(Player p) {
        return p.overlaps(this);
    }

    private boolean collidingWith(Obstacle o) {
        return this.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle());
    }
}
