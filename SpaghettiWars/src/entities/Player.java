package entities;

import java.util.ArrayList;

import utilities.Position;
import utilities.TextureHandler;
import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

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
	
	public boolean overlaps(Rectangle r){
		return this.getX() + this.getVector().getDeltaX() + this.getSprite().getWidth()/2 > r.getX() && this.getX() + this.getVector().getDeltaX() - this.getSprite().getWidth()/2 < r.getX() + r.getWidth() &&
				this.getY() + this.getVector().getDeltaY() + this.getSprite().getWidth()/2 > r.getY() && this.getY() + this.getVector().getDeltaY() - this.getSprite().getWidth()/2 < r.getY() + r.getHeight();
	}
	
	//author: Jimmy, Louise
	public void obstructedMove(ArrayList <Entity> l){
		
		boolean canMoveX = true;
		boolean canMoveY = true;
		for(Entity r: l){
			if(this.getX() + this.getVector().getDeltaX() + this.getSprite().getWidth()/2 > r.getSprite().getBoundingRectangle().getX() && this.getX() + this.getVector().getDeltaX() - this.getSprite().getWidth()/2 < r.getSprite().getBoundingRectangle().getX() + r.getSprite().getBoundingRectangle().getWidth() &&
					this.getY() + this.getSprite().getWidth()/2 > r.getSprite().getBoundingRectangle().getY() && this.getY() - this.getSprite().getWidth()/2 < r.getSprite().getBoundingRectangle().getY() + r.getSprite().getBoundingRectangle().getHeight()){
		
				canMoveX = false;
				break;
			}
		}
		if(canMoveX){
			this.setX(this.getX() + this.getVector().getDeltaX());
			this.getSprite().setX((float)this.getX() - this.getSprite().getWidth()/2);
		}
		for(Entity r: l){
			if(this.getX() + this.getSprite().getWidth()/2 > r.getSprite().getBoundingRectangle().getX() && this.getX() - this.getSprite().getWidth()/2 < r.getSprite().getBoundingRectangle().getX() + r.getSprite().getBoundingRectangle().getWidth() &&
					this.getY() + this.getVector().getDeltaY() + this.getSprite().getWidth()/2 > r.getSprite().getBoundingRectangle().getY() && this.getY() + this.getVector().getDeltaY() - this.getSprite().getWidth()/2 < r.getSprite().getBoundingRectangle().getY() + r.getSprite().getBoundingRectangle().getHeight()){
				canMoveY = false;
				break;
			}
		}
		if(canMoveY){
			this.setY(this.getY() + this.getVector().getDeltaY());
			this.getSprite().setY((float)this.getY() - this.getSprite().getHeight()/2);
		}
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
