package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Energydrink extends PowerUp {
	
	private int DURATION = 100;
	private static Sprite SPRITE = new Sprite(new Texture("ful.png"));
	private Player isActiveOn;
	
	public Energydrink(double x, double y, Sprite sprite){
		super(x, y, SPRITE);
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