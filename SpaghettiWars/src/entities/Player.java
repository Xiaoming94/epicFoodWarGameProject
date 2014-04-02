package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Entity {
	
	private String name;
	
	private int fatPoint = 0;
	private boolean isDead = false;
	
	private double speed;
	private PowerUp powerUp = null;
	
	
	public Player(String name, double x, double y, Sprite sprite, int speed){
		super(x, y, sprite);
		this.name = name;
		this.speed = speed;
	}
	
	public String getName(){
		return name;
	}
	
	public int getFatPoint(){
		return fatPoint;
	}
	
	public void gainWeight(int damage){
		fatPoint += damage;
	}
	
	public void looseWeight(int damage){
		fatPoint -= damage;
	}
	
	public void setWeight(int weight){
		fatPoint = weight;
	}
	
	public boolean checkIfDead(){
		return isDead;
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
//		this.getVector().setLengthTo(speed);
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public void collectPowerUp(PowerUp powerUp){
		if(this.powerUp == null){
			this.powerUp = powerUp;
		}else{
			//somethingsomething...
		}
	}
	
	public void usePowerUp(){
		powerUp.applyEffects(this); // or setActive() ? i have no idea what i'm doing...
		this.powerUp = null;
	}
	
}
