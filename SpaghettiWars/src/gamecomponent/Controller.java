package gamecomponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import entities.Pizza;
import gamecomponent.views.*;
import gamecomponent.views.MenuScreen;
import utilities.GameInputHandler;
import utilities.Position;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Entity;
import entities.Player;
import entities.Projectile;

public class Controller implements Runnable {

	Model model;

	public Controller(Model m) {
		model = m;
		
		System.out.println("controller skapas");
	}

	@Override
	public void run() {
		
		ArrayList<Entity> killProjectileList = new ArrayList<Entity>();
		ArrayList<Entity> eatProjectileList = new ArrayList<Entity>();
		ArrayList<Entity> killPlayerList = new ArrayList<Entity>();
		ArrayList<Entity> playerObstructed = new ArrayList<Entity>();
		
		model.createMap();
		model.createGUI();
		model.createPlayer();

		// model.addPlayer("Sir Eatalot", 100, -600, "ful.png", 2);


		
		long time;
		while (true) {

			// measure starttime for loop
			time = System.currentTimeMillis();

			
			
			//handle player movement 
			playerObstructed.clear();
			
			killProjectileList.clear();
			eatProjectileList.clear();
			killPlayerList.clear();
			
			//check if player is obstructed by obstacle
			for (Entity o : model.getMap().getObstacles())
				if (model.getPlayer().overlaps(
						o.getSprite().getBoundingRectangle())) {
					playerObstructed.add(o);
				}
			
			//if not obstructed by any obstacles, move normally
			if (playerObstructed.isEmpty())
				model.getPlayer().move();
			//if player is obstructed, use obstructedMove method
			else
				model.getPlayer().obstructedMove(playerObstructed);

			playerObstructed.clear();

			model.getEntitiesMutex().lock();
			
			
			
			// for loop checks projectiles, and what they have hit
			for (Projectile e : model.getProjectiles()) {

				//meatball
				if (e instanceof entities.Meatball) {
					for (Entity o : model.getMap().getObstacles()) {
						//if projectile e has hit obstacle o, put it on deathlist
						if (e.getSprite().getBoundingRectangle()
								.overlaps(o.getSprite().getBoundingRectangle())) {
							e.kill();
							killProjectileList.add(e);
					

					
						//if not, check if it's reached its maximum range, 
						//if so, put it on deathlist
						} else {
							e.update();
							if (e.isDead()) {
								killProjectileList.add(e);
							}
						}
					}
					
//					
//					//Collision detection for meatballs won't work if meatball
//					//hits the thrower
//					//what the fuck is going on with IDs?
//					Collection <Player> opponents = model.getOtherPlayers().values();
//					for(Player opp: opponents){
//						System.out.println("model player ID: " + model.getPlayer().getID());
//						System.out.println("meatball ID: " + e.getID());
//						System.out.println("player ID: " + opp.getID());
//						if(e.getSprite().getBoundingRectangle().overlaps(opp.getSprite().getBoundingRectangle()) && e.getID()/1000 != opp.getID()/1000){
//							e.kill();
//							killProjectileList.add(e);
//							opp.gainWeight(e.getDamage());
//						}
//					}
//					if(model.getPlayer().getSprite().getBoundingRectangle().overlaps(e.getSprite().getBoundingRectangle()) && e.getID()/1000!= model.getPlayer().getID()/1000){
//						e.kill();
//						killProjectileList.add(e);
//						model.getPlayer().gainWeight(e.getDamage());
//					}
//					//end of meatball detection
					
				}

				//pizza
				if (e instanceof entities.Pizza) {
					for (Entity o : model.getMap().getObstacles()) {
						//check if projectile e has hit an obstacle
						if (e.getSprite().getBoundingRectangle()
								.overlaps(o.getSprite().getBoundingRectangle())) {
							//if e has hit a wall, put it on death list and explode it
							if (o instanceof entities.Wall) {
								e.kill();
								killProjectileList.add(e);
								explodePizza((Pizza) e);
								break;
							}
						} else {
						//if target destination has been reached,
						//the pizza is to be killed and explode
							e.update();
							if (e.isDead()) {
								killProjectileList.add(e);
								explodePizza((Pizza) e);
								break;
							}
						}
					}
				}
			}

			model.getEntitiesMutex().unlock();

			
			//killing players
			Iterator<Integer> iterator = model.getOtherPlayers().keySet()
					.iterator();
			while (iterator.hasNext()) {
				int key = iterator.next();
				if (model.getOtherPlayers().get(key).isDead()) {
					model.getOtherPlayers().get(key).setVector(0, 0);
					killPlayerList.add(model.getOtherPlayers().get(key));

				} else
					model.getOtherPlayers().get(key).move();
			}
			for (Entity e : killPlayerList) {
				model.killPlayer(e);
			}
			
			//kill self and respawn if dead
			if(model.getPlayer().isDead()){
				model.getStillEntitys().add(model.getPlayer());
				model.createPlayer();
			}

			
			//killing projectiles on deathlist
			model.getEntitiesMutex().lock();
			for (Entity e : killProjectileList)
				model.killProjectile(e);
			model.getEntitiesMutex().unlock();
			//removing projectiles that have been eaten from projectile list
			for (Entity e : eatProjectileList)
				model.removeProjectile(e);
			
			
			

			time = System.currentTimeMillis() - time;
			try {
				Thread.sleep(10 - time);
			} catch (InterruptedException e) {
				System.out.println("got interrupted!");
			} catch (IllegalArgumentException e) {
				;
			}
		}
	}

	
	//method for making pizza victims fat
	private void explodePizza(Pizza collidingPizza) {
		//check if the player has hit himself with exploding pizza, if so make fat
		if (model.getPlayer().overlaps(collidingPizza)) {
			model.getPlayer().gainWeight(collidingPizza.getDamage());
		}
		
		Collection<Player> otherPlayers = model.getOtherPlayers().values();
		//check if other players have been hit by exploding pizza
		for (Player p : otherPlayers) {
			if (p.overlaps(collidingPizza)) {
				p.gainWeight(collidingPizza.getDamage());
			}
		}
	}
}
