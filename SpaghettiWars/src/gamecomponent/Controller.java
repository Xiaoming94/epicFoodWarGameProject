package gamecomponent;

import utilities.GameInputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Player;
import entities.Projectile;

public class Controller implements Runnable {

	Model model;
	
	/*public enum Direction{
		NORTH,SOUTH,WEST,EAST,NORTHWEST,NORTHEAST,SOUTHWEST,SOUTHEAST,STAY
	}*/
	
	public Controller(Model m) {
		model = m;
		Gdx.input.setInputProcessor(new GameInputHandler(model));
	}
	

	@Override
	public void run() {
		// wait for View to load textures before controller try to create player
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("got interrupted!");
		}
		model.createPlayer();
		
		model.addEntity(new Player("Sir derp", 50, 100, new Sprite(model.getTextureByName("dummylogo.png")), 0));

		while (true) {
			model.getPlayer().move();

			try {
				Thread.sleep(50);
				//TODO synched sleep
			}catch(InterruptedException e){
				System.out.println("got interrupted!");
			}
		}

	}
}
