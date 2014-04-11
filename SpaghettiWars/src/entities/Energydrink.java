/*
 * author: Louise
 * 
 * modified: Jimmy 8/4 - 14
 */

package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Energydrink extends PowerUp {
	
	private int DURATION = 500;
	private Player isActiveOn;
	
	public Energydrink(double x, double y, Sprite sprite){
		super(x, y, sprite);
	}

	@Override
	public void applyEffects(Entity player) {
		if(player.getClass() == Player.class){
			((Player)player).modifySpeed(2);
			isActiveOn = (Player)player;
		}
	}
	
	public void stopEffects(Entity player){
		System.out.println("stopEffects");
		((Player)player).modifySpeed(-2);
		((Player)player).setAffectedByPower(false);
		((Player)player).setPowerUp(null);
	}
	
	public void update(){
		if(isActiveOn != null)
			DURATION--;
		//if(DURATION > 1)
		if(DURATION < 1)
			stopEffects(isActiveOn);
	}
}