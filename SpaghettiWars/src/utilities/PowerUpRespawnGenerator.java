package utilities;

import gamecomponent.Model;

import java.util.Random;

/**
 * Created by xiaoming on 08/05/14.
 */
public class PowerUpRespawnGenerator {
    private static final int INITIAL_COUNTDOWN = 100;
    private static final int INITIAL_MODULO = 1000;
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
            if (isValidSpawningNumber(spawningNumberGenerator.nextInt() + 1)) {
                model.createEnergyDrink();
                resetModulo();

                System.out.println("Energy drink spawned");
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
            return countDown == 0;
    }



    private boolean isValidSpawningNumber(int number){
        return number % modulo == 0;
    }

}
