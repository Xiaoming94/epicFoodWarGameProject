package gamecomponent.controllerstuff;

import java.util.Collection;

import entities.Player;
import entities.Projectile;
import gamecomponent.Model;

public class ControllerUtilServer implements IControllerUtil {

	private Model model = null;
	
	@Override
	//public void run(){
	public void run() {
		
		
		for(Projectile p: model.getProjectiles()){
		
			//Collision detection for meatballs won't work if meatball
			//hits the thrower
			//what the fuck is going on with IDs?
			Collection <Player> opponents = model.getOtherPlayers().values();
			for(Player opp: opponents){
				System.out.println("model player ID: " + model.getPlayer().getID());
				System.out.println("meatball ID: " + p.getID());
				System.out.println("player ID: " + opp.getID());
				System.out.println(opponents.size());
				if(p.getSprite().getBoundingRectangle().overlaps(opp.getSprite().getBoundingRectangle()) && p.getID()/1000 != opp.getID()/1000){
					p.kill();
					//killProjectileList.add(p); //TODO: needs to be fixed?
					opp.gainWeight(p.getDamage());
				}
			}
			if(model.getPlayer().getSprite().getBoundingRectangle().overlaps(p.getSprite().getBoundingRectangle()) && p.getID()/1000!= model.getPlayer().getID()/1000){
				p.kill();
				//killProjectileList.add(p);
				model.getPlayer().gainWeight(p.getDamage());
			}
			//end of meatball detection
		
		}
		
		
	}

	public void addModel(Model m){
		this.model = m;
	}
}
