package gamecomponent.controllerparts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import entities.*;
import gamecomponent.Model;
import utilities.PowerUpRespawnGenerator;

public class Controller implements Runnable {

	Model model;
	IControllerUtil utilobject;

    private final PowerUpRespawnGenerator purg;
	private ArrayList<Entity> killProjectileList = new ArrayList<Entity>();
	private ArrayList<Entity> eatProjectileList = new ArrayList<Entity>();
	private ArrayList<Entity> killPlayerList = new ArrayList<Entity>();
	private ArrayList<Entity> removePickUpsList = new ArrayList<Entity>();
	private ArrayList<Entity> playerObstructed = new ArrayList<Entity>();

	public ArrayList<Entity> getKillProjectileList() {
		return killProjectileList;
	}
	
	public ArrayList<Entity> getEatProjectileList(){
		return eatProjectileList;
	}

	public Controller(Model m, IControllerUtil uo) {
		model = m;
		utilobject = uo;

        purg = new PowerUpRespawnGenerator(model);
		
		utilobject.addModel(model);//ny
	}

	@Override
	public void run() {
		
		model.createMap();
		model.createGUI();
		model.createPlayer(model.playerSpawnX, model.playerSpawnY);


		
		long time;
		while (true) {

			// measure starttime for loop
			time = System.currentTimeMillis();

			
			
			//handle player movement 
			playerObstructed.clear();
			
			killProjectileList.clear();
			eatProjectileList.clear();
			getKillPlayerList().clear();
			
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
				e.update();
				//meatball
				if (e instanceof entities.Meatball) {
					for (Entity o : model.getMap().getObstacles()) {
						//if projectile e has hit obstacle o, put it on deathlist
						if (e.getSprite().getBoundingRectangle().overlaps(o.getSprite().getBoundingRectangle())) {
							e.kill();
							killProjectileList.add(e);
						//if not, check if it's reached its maximum range, 
						//if so, put it on deathlist
						} else {
							if (e.isDead()) {
								killProjectileList.add(e);
							}
						}
					}		
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

            model.getPickUpsMutex().lock();

            for (PowerUp o : model.getPickUps()){
                if (model.getPlayer().overlaps(o.getSprite().getBoundingRectangle())) {
                    model.getPlayer().setPowerUp(o);
                    removePickUpsList.add(o);
                }

                otherPlayerPicksPowerUp(o);
            }

            model.getPickUpsMutex().unlock();

            purg.generateSpawningTime();


            for(Entity e : removePickUpsList){
            	model.removePickUp(e);
            }

			
			//killing projectiles on deathlist
			model.getEntitiesMutex().lock();
			for (Entity e : killProjectileList)
				model.killProjectile(e);
			model.getEntitiesMutex().unlock();
			//removing projectiles that have been eaten from projectile list
			for (Entity e : eatProjectileList)
				model.removeProjectile(e);
			
			this.utilobject.run();
			

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


	public ArrayList<Entity> getKillPlayerList() {
		return killPlayerList;
	}

	public void setKillPlayerList(ArrayList<Entity> killPlayerList) {
		this.killPlayerList = killPlayerList;
	}

    private void otherPlayerPicksPowerUp(PowerUp pu){
        Iterator <Integer> iterator = model.getOtherPlayers().keySet().iterator();
        while (iterator.hasNext()){
            int key = iterator.next();
            if (model.getOtherPlayers().get(key).overlaps(pu.getSprite().getBoundingRectangle())){
                model.getOtherPlayers().get(key).setPowerUp(pu);
                removePickUpsList.add(pu);
            }
        }
    }

}
