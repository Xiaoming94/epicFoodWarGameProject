/*
 * author: Louise
 * 
 * modified: Jimmy 8/4 - 14
 */

package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Energydrink extends PowerUp {
	
	private int DURATION = 100;
	private Player isActiveOn;
	
	public Energydrink(double x, double y, Sprite sprite){
		super(x, y, sprite);
	}

	@Override
	public void applyEffects(Entity player) {
		if(player.getClass() == Player.class){
			((Player)player).modifySpeed(50);
			isActiveOn = (Player)player;
		}
	}
	
	public void stopEffects(Entity player){
		((Player)player).modifySpeed(-50);
	}
	
	public void update(){
		if(isActiveOn != null)
			DURATION--;
		if(DURATION > 1)
			stopEffects(isActiveOn);
	}
}