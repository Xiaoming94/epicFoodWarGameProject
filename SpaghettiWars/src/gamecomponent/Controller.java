package gamecomponent;

import java.util.ArrayList;

import utilities.GameInputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Entity;
import entities.Player;

public class Controller implements Runnable {

	Model model;
	View view;
	
	private final GameInputHandler gih;
	
	/*public enum Direction{
		NORTH,SOUTH,WEST,EAST,NORTHWEST,NORTHEAST,SOUTHWEST,SOUTHEAST,STAY
	}*/

	public Controller(Model m, View view) {
		model = m;
		this.view = view;
		gih = new GameInputHandler(model, view);
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
		ArrayList<Entity> bufferList = new ArrayList<Entity>();
		
		model.createMap();
		model.createPlayer();

		while (true) {
			boolean playerObstructed = false;
			for(Entity o : model.getMap().getObstacles())
				if(model.getPlayer().overlaps(o.getSprite().getBoundingRectangle())){
					playerObstructed = true;
					break;
				}
			
			if(!playerObstructed)
				model.getPlayer().move();
			
			playerObstructed = false;
			
			for(Entity e : model.getEntitys())
			{
				for(Entity o : model.getMap().getObstacles())
					if(e.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle())){
						e.stop();
						bufferList.add(e);
				}
				else
					e.move();
			}
			
			model.getEntitiesMutex().lock();
			for(Entity e : bufferList)
				model.killEntity(e);
			model.getEntitiesMutex().unlock();
			
			try {
				Thread.sleep(10);
				//TODO synched sleep
			}catch(InterruptedException e){
				System.out.println("got interrupted!");
			}
		}

	}
}
