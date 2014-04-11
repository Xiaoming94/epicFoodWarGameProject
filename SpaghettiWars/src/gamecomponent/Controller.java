package gamecomponent;

import java.util.ArrayList;

import utilities.GameInputHandler;

import com.badlogic.gdx.Gdx;

import entities.Entity;
import entities.Projectile;

public class Controller implements Runnable {

	Model model;
	View view;
	
	private final GameInputHandler gih;

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
		model.createGUI();
		model.createPlayer();
		
		ArrayList<Entity> playerObstructed = new ArrayList<Entity>();
		long time;
		while (true) {
			//measure starttime
			time = System.currentTimeMillis();
			
			
			playerObstructed.clear();
			for(Entity o : model.getMap().getObstacles())
				if(model.getPlayer().overlaps(o.getSprite().getBoundingRectangle())){
					playerObstructed.add(o);
				}
			
			if(playerObstructed.isEmpty())
				model.getPlayer().move();
			else
				model.getPlayer().obstructedMove(playerObstructed);
			
			playerObstructed.clear();
			
			model.getEntitiesMutex().lock();
			for(Projectile e : model.getProjectiles())
			{
				for(Entity o : model.getMap().getObstacles())
					if(e.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle())){
						e.kill();
						bufferList.add(e);
				}
				else{
					e.update();
					if(e.isDead())
						bufferList.add(e);
				}
			}
			model.getEntitiesMutex().unlock();
			
			model.getEntitiesMutex().lock();
			for(Entity e : bufferList)
				model.killProjectile(e);
			model.getEntitiesMutex().unlock();
			
			time = System.currentTimeMillis() - time;
			
			try {
				Thread.sleep(10 - time);
			}catch(InterruptedException e){
				System.out.println("got interrupted!");
			}catch(IllegalArgumentException e){
				;
			}
		}
	}
}
