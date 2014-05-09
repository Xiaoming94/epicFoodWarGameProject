package utilities;

import entities.PowerUp;
import gamecomponent.Model;

import java.util.Random;

/**
 * Created by xiaoming on 08/05/14.
 */
public class PowerUpRespawnGenerator {
    private static final int INITIAL_COUNTDOWN = 10;
    private static final int INITIAL_MODULO = 100;
    private int modulo;
    private Model model;

    private final Random spawningNumberGenerator = new Random();

    private int countDown = INITIAL_COUNTDOWN;

    public PowerUpRespawnGenerator(Model model) {
        this.model = model;
        resetModulo();
    }

    public void generateSpawningTime(){
        if (isTime()) {
            if (isValidSpawningNumber(Math.abs(spawningNumberGenerator.nextInt()) + 1)) {
            	Position pos;
            	boolean found = false;
            	resetModulo();
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
            else{
                countDownModulo();
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

    private void resetModulo (){ modulo = INITIAL_MODULO; }

    private void countDownModulo(){
        if(modulo == 1){

            resetModulo();

        }else{

            modulo--;

        }
    }

    private boolean isTime() {
        return countDown < 1;
    }



    private boolean isValidSpawningNumber(int number){
        return number % modulo == 0;
    }

}
