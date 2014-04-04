package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Energydrink extends PowerUp {
	
	private final static int DURATION = 5;
	private final static Sprite SPRITE = new Sprite(new Texture("asset/bucket.png"));
	
	public Energydrink(double x, double y, Sprite sprite){
		super(x, y, SPRITE, DURATION);
	}

	@Override
	public void applyEffects(Entity player) {
		// might wanna check that player is a player..
		((Player)player).setSpeed(10);
	}
	
	public void stopEffects(Entity player){
		
	}
}