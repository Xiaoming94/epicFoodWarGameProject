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
	MainView view;

	public Controller(Model m, MainView view) {
		model = m;
		this.view = view;

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
		ArrayList<Entity> killPlayerList = new ArrayList<Entity>();

		model.createMap();
		model.createGUI();
		model.createPlayer();

		// model.addPlayer("Sir Eatalot", 100, -600, "ful.png", 2);

		ArrayList<Entity> playerObstructed = new ArrayList<Entity>();
		
		
		
		long time;
		while (true) {

			// measure starttime
			time = System.currentTimeMillis();

			
			
			//handle player movement
			playerObstructed.clear();
			for (Entity o : model.getMap().getObstacles())
				if (model.getPlayer().overlaps(
						o.getSprite().getBoundingRectangle())) {
					playerObstructed.add(o);
				}
			if (playerObstructed.isEmpty())
				model.getPlayer().move();
			else
				model.getPlayer().obstructedMove(playerObstructed);

			playerObstructed.clear();

			model.getEntitiesMutex().lock();
			
			
			
			// for loop checks projectiles, and what they have hit
			for (Projectile e : model.getProjectiles()) {

				//meatball
				if (e instanceof entities.Meatball) {
					for (Entity o : model.getMap().getObstacles()) {
						if (e.getSprite().getBoundingRectangle()
								.overlaps(o.getSprite().getBoundingRectangle())) {
							e.kill();
							killProjectileList.add(e);
						} else {
							e.update();
							if (e.isDead()) {
								killProjectileList.add(e);
							}
						}
					}
				}

				//pizza
				if (e instanceof entities.Pizza) {
					for (Entity o : model.getMap().getObstacles()) {
						if (e.getSprite().getBoundingRectangle()
								.overlaps(o.getSprite().getBoundingRectangle())) {
							if (o instanceof entities.Wall) {
								e.kill();
								killProjectileList.add(e);
								explodePizza((Pizza) e);
								break;
							}
						} else {
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

			
			//killing projectiles
			model.getEntitiesMutex().lock();
			for (Entity e : killProjectileList)
				model.killProjectile(e);
			model.getEntitiesMutex().unlock();

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
		if (model.getPlayer().overlaps(collidingPizza)) {
			model.getPlayer().gainWeight(collidingPizza.getDamage());
		}

		Collection<Player> otherPlayers = model.getOtherPlayers().values();
		for (Player p : otherPlayers) {
			if (p.overlaps(collidingPizza)) {
				p.gainWeight(collidingPizza.getDamage());
			}
		}
	}
	
	
	

	public void startGame() {
		view.setScreen(new GameScreen(model));
	}
}
