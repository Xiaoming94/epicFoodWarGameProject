package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import utilities.Vector;

import java.util.List;

public class Meatball extends Projectile {
	
	public Meatball(double x, double y, Vector vector, Sprite sprite){
		super(x, y, vector, sprite, 5, 300);
		this.setSpeed(20);
	}
	
	public Meatball(double x, double y, Vector vector, Sprite sprite, int clientID, int objectID){
		super(x,y,vector,sprite,5, 300, clientID, objectID);
		this.setSpeed(20);
        this.setState(ProjectileState.FLYING);
	}

    @Override
    public void checkColliding(List<Obstacle> obstacles, List<Player> playerlist) {
        for (Obstacle o : obstacles){
            if (collidingWith(o)) {
                this.kill();
                this.setState(ProjectileState.STILL);
            }
        }
        for (Player p : playerlist){
            if (collidingWith(p)){
                this.kill();
                p.gainWeight(this.getDamage());
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
