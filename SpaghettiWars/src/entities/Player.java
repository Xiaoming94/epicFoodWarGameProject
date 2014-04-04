package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Entity {
	
	private String name;
	
	private int fatPoint = 0;
	private boolean isDead = false;
	
	private double speed;
	//private PowerUp powerUp = null;
	
	
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
	
//	public void setPlayerDirection(Controller.Direction dir){
//		switch(dir){
//		case NORTH:
//			getVector().setVector(0, 1*speed);
//			
//			break;
//		case NORTHEAST:
//			getVector().setVectorByDegree(1*speed, 45);
//			break;
//		case EAST:
//			getVector().setVector(1*speed, 0);
//			break;
//		case SOUTHEAST:
//			getVector().setVectorByDegree(1*speed, 315);
//			break;
//		case SOUTH:
//			getVector().setVector(0, -1*speed);
//			break;
//		case SOUTHWEST:
//			getVector().setVectorByDegree(1*speed, 225);
//			break;
//		case WEST:
//			getVector().setVector(-1*speed,0);
//			break;
//		case NORTHWEST:
//			getVector().setVectorByDegree(1*speed, 135);
//			break;
//		case STAY:
//			getVector().setVector(0,0);
//			break;
//		}
//	}
	
	/*public void collectPowerUp(PowerUp powerUp){
		if(this.powerUp == null){
			this.powerUp = powerUp;
		}else{
			//somethingsomething...
		}
	}
	
	public void usePowerUp(){
		powerUp.applyEffects(this); // or setActive() ? i have no idea what i'm doing...
		this.powerUp = null;
	}*/
	
}
