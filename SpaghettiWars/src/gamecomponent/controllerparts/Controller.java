package gamecomponent.controllerparts;

import java.util.*;

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



			model.getProjectilesMutex().lock();
			
			// for loop checks projectiles, and what they have hit
			for (Projectile e : model.getProjectiles()) {
				e.update(model.getMap().getObstacles(), model.getOtherPlayers(), model.getPlayer());
                switch(e.getState()){
                	case EATEN:
                		this.eatProjectileList.add(e);
                		break;
                	case STILL:
                		this.killProjectileList.add(e);
                		break;
                	case FLYING:
                		break;
                }
			}

			model.getProjectilesMutex().unlock();

			model.addTempProjectiles();
			
            model.getPickUpsMutex().lock();

            for (PowerUp o : model.getPickUps()){
                if (model.getPlayer().overlaps(o.getSprite().getBoundingRectangle())) {
                    model.getPlayer().setPowerUp(o);
                    removePickUpsList.add(o);
                }

                otherPlayerPicksPowerUp(o);
            }

            model.getPickUpsMutex().unlock();

           // purg.generateSpawningTime();


            for(Entity e : removePickUpsList){
            	model.removePickUp(e);
            }
            
            this.utilobject.run();

			//killing projectiles on deathlist
			for (Entity e : killProjectileList)
				model.killProjectile(e);
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
    
    //ny
    public PowerUpRespawnGenerator getPowerUpRespawnGenerator(){
    	return purg;
    }

}
