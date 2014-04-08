package gamecomponent;

import utilities.GameInputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Player;

public class Controller implements Runnable {

	Model model;
	
	private final GameInputHandler gih;
	
	/*public enum Direction{
		NORTH,SOUTH,WEST,EAST,NORTHWEST,NORTHEAST,SOUTHWEST,SOUTHEAST,STAY
	}*/
	
	public Controller(Model m) {
	
		model = m;
		gih = new GameInputHandler(model);
		Gdx.input.setInputProcessor(gih);
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
