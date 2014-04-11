package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

import utilities.Position;
import utilities.Vector;

public class Pizza extends Projectile{
	
	private Position targetPos;
	
	public Pizza(double x, double y, Vector v, Sprite sprite, Position targetPos){

        super(x, y, v, sprite, 5, targetPos.distanceTo(new Position(x,y)));

        Position myPosition = new Position(x,y);

        double maxRange = 500;
        double distanceToMouse = targetPos.distanceTo(myPosition);
        double travelDistance;

        if ( maxRange > distanceToMouse){
            travelDistance = distanceToMouse;
        }else{
            travelDistance = maxRange;
        }

        super.setRange(travelDistance);

        this.targetPos = targetPos;

    }
	
	public Position getTargetPosition(){
		return targetPos;
	}
}
