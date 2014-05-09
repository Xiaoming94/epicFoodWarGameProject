package gamecomponent.controllerparts;

import java.util.Collection;
import java.util.Iterator;

import entities.Entity;
import entities.Meatball;
import entities.Player;
import entities.Projectile;
import gamecomponent.Model;

public class ControllerUtilServer implements IControllerUtil {

	private Model model = null;
	private Controller parent = null;
	
	public void setParent(Controller c){
		parent = c;
	}

	
	@Override
	//public void run(){
	public void run() {
		
		
		for(Projectile p: model.getProjectiles()){
		
			//Collision detection for meatballs won't work if meatball
			//hits the thrower
			//what the fuck is going on with IDs?
			Collection<Player> opponents = model.getOtherPlayers().values();
			for(Player opp: opponents){
				if(p.getSprite().getBoundingRectangle().overlaps(opp.getSprite().getBoundingRectangle()) && p.getID()/1000000 != opp.getID()/1000000 && p instanceof Meatball){
					p.kill();
					
					parent.getEatProjectileList().add(p);
					opp.gainWeight(p.getDamage());
				}
			}
			if(model.getPlayer().getSprite().getBoundingRectangle().overlaps(p.getSprite().getBoundingRectangle()) && p.getID()/1000000!= model.getPlayer().getID()/1000000){
				p.kill();
				
				parent.getEatProjectileList().add(p);
				model.getPlayer().gainWeight(p.getDamage());
			}
			//end of meatball detection
		
		}
		
		//killing players
		Iterator<Integer> iterator = model.getOtherPlayers().keySet()
				.iterator();
		while (iterator.hasNext()) {
			int key = iterator.next();
			if (model.getOtherPlayers().get(key).isDead()) {
				model.getOtherPlayers().get(key).setVector(0, 0);
				parent.getKillPlayerList().add(model.getOtherPlayers().get(key));

			} else
				model.getOtherPlayers().get(key).move();
		}
		for (Entity e : parent.getKillPlayerList()) {
			model.killPlayer(e);
			model.getNetworkObject().killPlayer((Player)e);
		}
		
		//kill self and respawn if dead
		if(model.getPlayer().isDead()){
			System.out.println("player is dead");
			model.getStillEntitys().add(model.getPlayer());
			model.getNetworkObject().killPlayer(model.getPlayer());
			model.createPlayer();
		}
	}

	public void addModel(Model m){
		this.model = m;
	}
}
