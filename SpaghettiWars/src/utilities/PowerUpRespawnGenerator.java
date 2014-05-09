package utilities;

import entities.PowerUp;
import gamecomponent.Model;

import java.util.Random;

/**
 * Created by xiaoming on 08/05/14.
 */
public class PowerUpRespawnGenerator {
    private static final int INITIAL_COUNTDOWN = 100;
    private Model model;

    private final Random spawningNumberGenerator = new Random();

    private int countDown = INITIAL_COUNTDOWN;

    public PowerUpRespawnGenerator(Model model) {
        this.model = model;
    }

    public void generateSpawningTime(){
        if (isTime()) {
            if (isValidSpawningNumber(Math.abs(spawningNumberGenerator.nextInt()) + 1)) {
            	Position pos;
            	boolean found = false;
            	if(model.getMap().getMaxPowerUps() >= model.getPickUps().size()){
	            	while(true){
	            		pos = model.getMap().getPowerUpSpawnLocations().get(Math.abs(spawningNumberGenerator.nextInt())%model.getMap().getPowerUpSpawnLocations().size());
		            	for(PowerUp pu : model.getPickUps())
		            		if(pu.getPosition().equals(pos)){
		            			found = true;
		            			break;
		            		}
		            	if(found == false){
		            		model.createEnergyDrink(pos);
		            		System.out.println("Energy drink spawned");
		            		break;
		            	}
	            	}
            	}
            }
            resetCountDown();
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

    private boolean isTime() {
        return countDown < 1;
    }



    private boolean isValidSpawningNumber(int number){
        return number % 97 == 0;
    }

}
