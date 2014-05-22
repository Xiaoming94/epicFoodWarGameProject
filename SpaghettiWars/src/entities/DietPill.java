// Author: Louise

//A class to represent a dietpill powerup in the game

package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class DietPill extends PowerUp {
	private static final int fatPointsLost = 40;

	
	public DietPill(double x, double y, Sprite sprite){
		super(x,y,sprite);
	}

	@Override
	public void applyEffects(Entity player) {
		if(player.getClass() == Player.class){

			Player p= (Player)player;
			if(p.getFatPoint() >= fatPointsLost)
				p.looseWeight(fatPointsLost);
			else
				p.setWeight(0);
			p.removePowerUpEffect(this);
		}
	}

	@Override
	public void stopEffects(Entity player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	public static int getFatPointsLost(){
		return fatPointsLost;
	}
	

}
