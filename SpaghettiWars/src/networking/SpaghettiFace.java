package networking;

import entities.Player;
import entities.Projectile;

public interface SpaghettiFace {
	
	public void disconnect();
	public void sendProjectile(Projectile p);
	public void killProjectile(Projectile p);
	public void killPlayer(Player p);
}
