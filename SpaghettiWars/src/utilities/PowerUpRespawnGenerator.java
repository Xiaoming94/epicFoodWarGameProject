//Author: Henry
//Modified heavily by Jimmy

//A class for generating powerup respawn places.

package utilities;

import entities.PowerUp;
import gamecomponent.Model;

import java.util.Random;

/**
 * Created by xiaoming on 08/05/14.
 */
public class PowerUpRespawnGenerator {
	private static final int INITIAL_COUNTDOWN = 1000;
	private static final int INITIAL_MODULO = 1000;
	private int modulo;
	private Model model;

	private final Random spawningNumberGenerator = new Random();

	private int countDown = INITIAL_COUNTDOWN;

	public PowerUpRespawnGenerator(Model model) {
		this.model = model;
		resetModulo();
	}

	public void generateSpawningTime() {
		if (isTime()) {
			if (isValidSpawningNumber(Math.abs(spawningNumberGenerator
					.nextInt()) + 1)) {
				Position pos;
				boolean found = false;
				resetModulo();
				resetCountDown();
				if (model.getMap().getMaxPowerUps() > model.getPickUps().size()) {
					while (true) {
						found = false;
						pos = model
								.getMap()
								.getPowerUpSpawnLocations()
								.get(Math.abs(spawningNumberGenerator.nextInt())
										% model.getMap()
												.getPowerUpSpawnLocations()
												.size());
						MutexHandler.getInstance().getPickUpsMutex().lock();
						for (PowerUp pu : model.getPickUps())
							if (pu.getPosition().equals(pos)) {
								found = true;
								break;
							}
						MutexHandler.getInstance().getPickUpsMutex().unlock();
						if (found == false) {
							int randomNbr = 0;
							randomNbr = (Math.abs(spawningNumberGenerator.nextInt())) % 2;
							if(randomNbr == 0){
								model.createEnergyDrink(pos);
							}else{
								model.createDietPill(pos);
							}
							System.out.println("Energy drink spawned");
							break;
						}
					}
				}
			} else {
				countDownModulo();
			}
		} else {
			countDown();
		}
	}

	private void resetCountDown() {
		countDown = INITIAL_COUNTDOWN;
	}

	private void countDown() {
		countDown--;
	}

	private void resetModulo() {
		modulo = INITIAL_MODULO;
	}

	private void countDownModulo() {
		if (modulo > 1)
			modulo--;
	}

	private boolean isTime() {
		return countDown < 1;
	}

	private boolean isValidSpawningNumber(int number) {
		return number % modulo == 0;
	}

}
