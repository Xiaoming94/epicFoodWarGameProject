package utilities;

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
            if (isValidSpawningNumber(spawningNumberGenerator.nextInt() + 1)) {
                model.createEnergyDrink();

                System.out.println("Energy drink spawned");
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
        System.out.println(countDown);
    }

    private boolean isTime() {
        return countDown == 0;
    }



    private boolean isValidSpawningNumber(int number){
        return number % 31 == 0;
    }

}
