package gamecomponent.controllerparts;

import java.util.Collection;
import java.util.Iterator;

import entities.Entity;
import entities.Meatball;
import entities.PizzaSlice;
import entities.Player;
import entities.Projectile;
import entities.ProjectileState;
import gamecomponent.Model;

public class ControllerUtilServer implements IControllerUtil {

	private Model model = null;
	private Controller parent = null;

	public void setParent(Controller c) {
		parent = c;
	}

	@Override
	// public void run(){
	public void run() {

		model.getProjectilesMutex().lock();
		for (Projectile p : model.getProjectiles()) {

			Collection<Player> opponents = model.getOtherPlayers().values();
			for (Player opp : opponents) {
				if (p.getSprite().getBoundingRectangle()
						.overlaps(opp.getSprite().getBoundingRectangle())
						&& p.getID() / 1000000 != opp.getID() / 1000000) {
					if (p instanceof Meatball) {

						p.kill();

						parent.getEatProjectileList().add(p);
						p.setState(ProjectileState.EATEN);
						opp.gainWeight(p.getDamage());
					}else if(p instanceof PizzaSlice){
						p.kill();
						parent.getEatProjectileList().add(p);
						p.setState(ProjectileState.EATEN);
						opp.gainWeight(p.getDamage());
					}
				}
			}
			if (model.getPlayer().getSprite().getBoundingRectangle()
					.overlaps(p.getSprite().getBoundingRectangle())
					&& p.getID() / 1000000 != model.getPlayer().getID() / 1000000) {
				p.kill();

				parent.getEatProjectileList().add(p);
				p.setState(ProjectileState.EATEN);
				model.getPlayer().gainWeight(p.getDamage());
			}
			// end of meatball detection

		}
		model.getProjectilesMutex().unlock();

		// killing players
		Iterator<Integer> iterator = model.getOtherPlayers().keySet()
				.iterator();
		while (iterator.hasNext()) {
			int key = iterator.next();
			if (model.getOtherPlayers().get(key).isDead()) {
				model.getOtherPlayers().get(key).setVector(0, 0);
				parent.getKillPlayerList()
						.add(model.getOtherPlayers().get(key));

			} else
				model.getOtherPlayers().get(key).move();
		}
		for (Entity e : parent.getKillPlayerList()) {
			model.killPlayer(e);
			parent.model.setChanged();
			parent.model.notifyObservers((Player) e);
		}

		// kill self and respawn if dead
		if (model.getPlayer().isDead()) {
			model.getStillEntitys().add(model.getPlayer());
			parent.model.setChanged();
			parent.model.notifyObservers(model.getPlayer());
			model.createPlayer(model.playerSpawnX, model.playerSpawnY);
		}
		
		parent.getPowerUpRespawnGenerator().generateSpawningTime();
	}

	public void addModel(Model m) {
		this.model = m;
	}
}
