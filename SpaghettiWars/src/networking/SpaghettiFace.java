package networking;

import entities.Projectile;

public interface SpaghettiFace {
	
	public void disconnect();
	public void sendProjectile(Projectile p);
	public void killProjectile(Projectile p);
}
