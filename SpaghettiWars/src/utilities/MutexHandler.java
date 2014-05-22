//Author: Joakim

package utilities;

import sun.awt.Mutex;

public class MutexHandler {
	
	private static MutexHandler mutexes;
	
	private final Mutex pickUpsMutex;
	private final Mutex stillEntitiesMutex;

	private final Mutex projectilesMutex;
	private final Mutex otherPlayersMutex;

	private final Mutex temporaryProjectilesMutex;

	
	private MutexHandler(){
		stillEntitiesMutex = new Mutex();
		pickUpsMutex = new Mutex();

		projectilesMutex = new Mutex();
		otherPlayersMutex = new Mutex();

		temporaryProjectilesMutex = new Mutex();

	}
	
	public static MutexHandler getInstance(){
		if(mutexes == null){
			mutexes = new MutexHandler();
		}
		return mutexes;
	}

	public Mutex getPickUpsMutex() {
		return pickUpsMutex;
	}

	public Mutex getStillEntitiesMutex() {
		return stillEntitiesMutex;
	}

	public Mutex getProjectilesMutex() {
		return projectilesMutex;
	}

	public Mutex getOtherPlayersMutex() {
		return otherPlayersMutex;
	}

	public Mutex getTemporaryProjectilesMutex() {
		return temporaryProjectilesMutex;
	}
}
