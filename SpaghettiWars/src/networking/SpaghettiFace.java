//Author: Joakim

//An interface for the client and server classes.

package networking;

import java.util.Observer;

import entities.Player;
import entities.Projectile;

public interface SpaghettiFace extends Observer {
	
	public void disconnect();
	public void sendProjectile(Projectile p);
}
