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
	private boolean used = false;
	
	public Energydrink(double x, double y, Sprite sprite){
		super(x, y, sprite);
	}

	@Override
	public void applyEffects(Entity player) {
		System.out.println("powerup activated");
		if(player.getClass() == Player.class && !used){
			used = true;
			((Player)player).modifySpeed(2);
			isActiveOn = (Player)player;
		}
	}
	
	public void stopEffects(Entity player){
		((Player)player).modifySpeed(-2);
		((Player)player).removePowerUpEffect(this);
	}
	
	public void update(){
		if(isActiveOn != null)
			DURATION--;
		
		if(DURATION < 1)
			stopEffects(isActiveOn);
	}
}