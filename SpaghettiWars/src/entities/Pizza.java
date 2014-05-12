package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

import utilities.Position;
import utilities.Vector;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Pizza extends Projectile{
	
	private Position targetPos;

    private final double explosionRadius = 50;
	
    
	public Pizza(double x, double y, Vector v, Sprite sprite, Position targetPos){

        super(x, y, v, sprite, 5, targetPos.distanceTo(new Position(x,y)));
        createPizza(x,y,targetPos);

    }
	
	public Pizza(double x, double y, Vector v, Sprite sprite, Position targetPos, int clientID, int objectID){
		super(x,y,v,sprite, 5, targetPos.distanceTo(new Position(x,y)), clientID, objectID);
		createPizza(x,y,targetPos);
	}	
	
	private void createPizza(double x, double y, Position targetPos){
		Position myPosition = new Position(x,y);

        double maxRange = 350;
        double distanceToMouse = targetPos.distanceTo(myPosition);
        double travelDistance;
        
        this.setSpeed(10);

        if ( maxRange > distanceToMouse){
            travelDistance = distanceToMouse;
        }else{
            travelDistance = maxRange;
        }

        super.setRange(travelDistance);

        this.targetPos = targetPos;
        this.setState(ProjectileState.FLYING);
	}
	
	
    public double getExplosionRadius(){
        return explosionRadius;
    }
	
	public Position getTargetPosition(){
		return targetPos;
	}

    @Override
    public void update(List<Obstacle> obstacles, Map<Integer, Player> playerlist, Player player) {
    	if(super.update()){
    		this.explode(playerlist, player);
    		this.setState(ProjectileState.STILL);
    	}
    	
        for (Obstacle o : obstacles){
            if (collidingWith(o)){
                this.kill();
                this.explode(playerlist, player);
                this.setState(ProjectileState.STILL);
            }
        }
    }

    private void explode(Map<Integer, Player> playerlist, Player player) {
    	if (player.overlaps(this)) {
			player.gainWeight(this.getDamage());
		}
		
		Collection<Player> otherPlayers = playerlist.values();
		//check if other players have been hit by exploding pizza
		for (Player p : otherPlayers) {
			if (p.overlaps(this)) {
				p.gainWeight(this.getDamage());
			}
		}
		
	}

	private boolean collidingWith(Obstacle o) {
        return this.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle()) && o.collides(this);
    }

}
