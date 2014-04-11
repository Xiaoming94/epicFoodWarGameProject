package gamecomponent;

import java.util.ArrayList;

import utilities.GameInputHandler;
import utilities.Position;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Entity;
import entities.Player;
import entities.Projectile;

public class Controller implements Runnable {

	Model model;
	View view;
	
	private final GameInputHandler gih;

	public Controller(Model m, View view) {
		model = m;
		this.view = view;
		gih = new GameInputHandler(model);
		Gdx.input.setInputProcessor(gih);
		view.reciveInputHandler(gih);
	}
	

	@Override
	public void run() {
		
		
		// wait for View to load textures before controller try to create player
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("got interrupted!");
		}
		ArrayList<Entity> killProjectileList = new ArrayList<Entity>();
		ArrayList<Entity> eatProjectileList = new ArrayList<Entity>();
		
		model.createMap();
		model.createGUI();
		model.createPlayer();
		
		model.addPlayer("Sir Eatalot", 100, -600, "ful.png", 2);
		
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
							killProjectileList.add(e);
						}else{
							e.update();
							if(e.isDead()){
								killProjectileList.add(e);
							}
						}
					}
					
					for(Player p : model.getOtherPlayers()){
						if(p.overlaps(e.getSprite().getBoundingRectangle())){
							eatProjectileList.add(e);
							p.gainWeight(e.getDamage());
						}
					}
				}
				
				//stuff that pizza should do:
				//if pizza, impact with walls always and with other things when they've been targeted
				if(e instanceof entities.Pizza){
					for(Entity o: model.getMap().getObstacles()){
						if(e.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle())){
							if(o instanceof entities.Wall){
								e.kill();
								killProjectileList.add(e);
							}
					
						
						}else{
							
							e.update();
							if(e.isDead()){
								killProjectileList.add(e);
							}
							
							//chaos follows in comments below. ignore until later or never.
							
//							//check if we've landed on something... or something like that
//							double obstacleLeftEdge = o.getX();
//							double obstacleRightEdge = o.getX() + o.getSprite().getBoundingRectangle().getWidth();
//							double obstacleTopEdge = o.getY() + o.getSprite().getBoundingRectangle().getHeight();
//							double obstacleBottomEdge = o.getY();
//							Position aim = ((entities.Pizza)e).getTargetPosition();
//							
//							//if true, we've hit something
//							if(e.isDead() && aim.getX() > obstacleLeftEdge && aim.getX() < obstacleRightEdge 
//									&& aim.getY() < obstacleTopEdge && aim.getY() > obstacleBottomEdge){
//								
//								//if we've hit a player, make fat!
//								if(o instanceof entities.Player){
//									//make fatter;
//								}
//							}
							
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
			
			for(Player p : model.getOtherPlayers()){
				if(p.isDead()){
					p.setVector(0, 0);
				}
				else
					p.move();
			}
			
			model.getEntitiesMutex().lock();
			for(Entity e : killProjectileList)
				model.killProjectile(e);
			model.getEntitiesMutex().unlock();
			
			for(Entity e : eatProjectileList)
				model.removeProjectile(e);
			
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
