//Author: Joakim

//A class that spawns pizzaslices.

package utilities;

import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.PizzaSlice;
import entities.Projectile;
import gamecomponent.Model;

public class PizzaSlicer {
	
	Model m;
	
	
	public PizzaSlicer(Model m){
		this.m = m;
	}
	
	public void spawnPizzaSlices(Position pos){
		for(int i = 0; i < 8; i++){
			Projectile p = new PizzaSlice(pos.getX(), pos.getY(), new Vector(0,0), new Sprite(TextureHandler.getInstance().getTextureByName("PizzaSlice" + (i+1) + ".png")), i+3);
			
			p.getVector().setVectorByDegree(p.getSpeed(), 68-45*i);
			m.addTempProjectile(p);
		}
	}
}
