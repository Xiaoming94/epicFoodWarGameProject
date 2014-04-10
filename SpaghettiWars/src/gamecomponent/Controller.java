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
			
			
			//Lalalalalalala, testing stuff. original code below in comments.
			for(Projectile e: model.getProjectiles()){
				
				//if meatball, impact whenever it hits something
				if(e instanceof entities.Meatball){
					for(Entity o: model.getMap().getObstacles()){
						if(e.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle())){
							e.kill();
							bufferList.add(e);
						}else{
							e.update();
							if(e.isDead()){
								bufferList.add(e);
							}
						}
					}
				}
				
				//stuff that pizza should do:
				//if pizza, impact with walls always and with other things when they've been targeted
				if(e instanceof entities.Pizza){
					for(Entity o: model.getMap().getObstacles()){
						if(e.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle())){
							
							//if collision with wall or with thing at target position kill the pizza
							float targetX = (float)((entities.Pizza)e).getTargetPosition().getX();
							float targetY = (float)((entities.Pizza)e).getTargetPosition().getY();
							System.out.println("pizza target, x: " + targetX);
							System.out.println("pizza target, y: " + targetY);
							double leftXOfObstacle = o.getX();
							double rightXOfObstacle = o.getX() + o.getSprite().getWidth();
							
							if(o instanceof entities.Wall){
								e.kill();
								bufferList.add(e);
							}
									
						
						}else{
							e.update();
							if(e.isDead()){
								bufferList.add(e);
							}
						}
					}
					
				}
			}
			
			
			//this is what was here before I messed with it...
			
//			for(Projectile e : model.getProjectiles())
//			{
//				for(Entity o : model.getMap().getObstacles())
//					if(e.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle())){
//						e.kill();
//						bufferList.add(e);
//				}
//				else{
//					e.update();
//					if(e.isDead())
//						bufferList.add(e);
//				}
//			}
			
			
			model.getEntitiesMutex().unlock();
			
			model.getEntitiesMutex().lock();
			for(Entity e : bufferList)
				model.killProjectile(e);
			model.getEntitiesMutex().unlock();
			
			time = System.currentTimeMillis() - time;
			

			
			//very ugly solution, but first round the while loop takes longer than 10 ms
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
