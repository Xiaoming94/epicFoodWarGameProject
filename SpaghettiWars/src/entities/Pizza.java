package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

import utilities.Position;
import utilities.Vector;

import java.util.List;

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
    public void checkColliding(List<Obstacle> obstacles, List<Player> playerlist) {
        for (Obstacle o : obstacles){
            if (collidingWith(o)){
                this.kill();
                this.setState(ProjectileState.STILL);
            }
        }
    }

    private boolean collidingWith(Obstacle o) {
        return this.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle()) && o.collides(this);
    }

}
