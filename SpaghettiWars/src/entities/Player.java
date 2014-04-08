package entities;

import utilities.Position;
import utilities.TextureHandler;
import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Entity {
	
	private String name;
	
	private int fatPoint = 0;
	private boolean isDead = false;
	
	private TextureHandler textureHandler;
	
	private PowerUp powerUp = null;
	
	
	public Player(String name, double x, double y, Sprite sprite, int speed, TextureHandler th){
		super(x, y, sprite);
		this.name = name;
		this.setSpeed(speed);
		textureHandler = th;
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
	
	public void modifySpeed(double k){
		this.setSpeed(this.getSpeed()+k);
	}
	
	public Projectile shoot(double x, double y){
		Meatball mb = new Meatball(this.getX(), this.getY(), new Vector(0,0), new Sprite(textureHandler.getTextureByName("Kottbulle.png")));
		mb.setVector(new Position(x,y));
		return mb;
	}
	
	
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
