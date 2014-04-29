package entities;

import java.util.ArrayList;

import com.badlogic.gdx.math.Circle;
import utilities.Position;
import utilities.TextureHandler;
import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity {
	
	private String name;
	
	private double fatPoints;
	private boolean isDead;
	
	private static TextureHandler textureHandler;
	
	private PowerUp powerUp = null;
	private String selectedWeapon = "meatball";
	private boolean affectedByPowerUp = false;
	private float spriteHeight, spriteWidth;
	private ArrayList<PowerUp> activePowerUps, activePowerUpsTrashBin;
	
	private double speedMod;
	
	public Player(String name, double x, double y, Sprite sprite, double speed){
		super(x, y, sprite);
		this.name = name;
		this.setSpeed(speed);
		
		activePowerUps = new ArrayList<PowerUp>();
		activePowerUpsTrashBin = new ArrayList<PowerUp>();
		
		spriteWidth = this.getSprite().getWidth();
		spriteHeight = this.getSprite().getHeight();
		speedMod = 0;
		fatPoints = 0;
		isDead = false;
	}

	public Player(String name, double x, double y, Sprite sprite, double speed, TextureHandler th){
		this(name, x, y, sprite, speed);
		textureHandler = th;
	}
	
	public String getName(){
		return name;
	}
	
	public double getFatPoint(){
		return fatPoints;
	}
	
	public void gainWeight(int damage){
		fatPoints += damage;
		weightChanged();
	}
	
	public void looseWeight(int damage){
		fatPoints -= damage;
		weightChanged();
	}
	
	public void setWeight(int weight){
		fatPoints = weight;
		weightChanged();
	}
	
	private void weightChanged(){
		this.getSprite().setSize(spriteWidth*(float)getScale(), spriteHeight*(float)getScale());
		this.getSprite().setOriginCenter();
		
		this.getSprite().setX((float)this.getX()-this.getSprite().getWidth()/2);
		this.getSprite().setY((float)this.getY()-this.getSprite().getHeight()/2);
		
		this.setSpeed(1+2*(1/(this.getFatPoint()+1)));
		this.updateVector();
		
		if(this.getFatPoint() > 99)
			isDead = true;
	}
	
	@Override
	public void move() {
		super.move();
		
		for(PowerUp pu : activePowerUps)
			pu.update();
		
		for(PowerUp pu : activePowerUpsTrashBin)
			activePowerUps.remove(pu);
	}
	
	public void removePowerUpEffect(PowerUp pu){
		activePowerUpsTrashBin.add(pu);
	}
	
	public boolean isDead(){
		return isDead;
	}
	
	public void modifySpeed(double k){
		speedMod += k;
	}
	
	@Override
	public double getSpeed(){
		return super.getSpeed() + speedMod;
	}
	
	public double getScale(){
		return (fatPoints + 100) / 100;
	}
	
	public Projectile shoot(double x, double y){

		if(selectedWeapon == "pizza"){
			Pizza p = new Pizza(this.getX(), this.getY(), new Vector(0,0), new Sprite(textureHandler.getTextureByName("pizza.png")), new Position(x,y));
			p.setVector(new Position(x,y));
			return p;
		}else{
			Meatball mb = new Meatball(this.getX(), this.getY(), new Vector(0,0), new Sprite(textureHandler.getTextureByName("Kottbulle.png")));
			mb.setVector(new Position(x,y));
			return mb;
		}
	}
	
	public boolean overlaps(Rectangle r){
		return this.getX() + this.getVector().getDeltaX() + this.getSprite().getWidth()/2 > r.getX() && this.getX() + this.getVector().getDeltaX() - this.getSprite().getWidth()/2 < r.getX() + r.getWidth() &&
				this.getY() + this.getVector().getDeltaY() + this.getSprite().getWidth()/2 > r.getY() && this.getY() + this.getVector().getDeltaY() - this.getSprite().getWidth()/2 < r.getY() + r.getHeight();
	}
    public boolean overlaps(Projectile p){
        if (p instanceof Pizza){
            Pizza tmp = (Pizza) p;
            Position pizzaPos = new Position(tmp.getX(),tmp.getY());
            Position playerPos = new Position(this.getX(),this.getY());
            return pizzaPos.distanceTo(playerPos) < tmp.getExplosionRadius() + this.getSprite().getBoundingRectangle().getWidth()/2;
        }
        else {
            return false;
        }
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
	

	public void changeWeapon(int i){
		switch(i+1){
		case 2: 
			selectedWeapon = "pizza";
			break;
		default:
			selectedWeapon = "meatball";
			break;
		}
	}
	
	/*public void collectPowerUp(PowerUp powerUp){
		if(this.powerUp == null){
			this.powerUp = powerUp;
		}else{
			//somethingsomething...
		}
	}*/
	
	public void usePowerUp(){
		if(powerUp != null){
			this.activePowerUps.add(powerUp);
			powerUp.applyEffects(this); // or setActive() ? i have no idea what i'm doing...
			this.powerUp = null;
			affectedByPowerUp = true;
		}
	}
	
	public PowerUp getPowerUp(){
		return powerUp;
	}
	
	public void setPowerUp(PowerUp pu){
		powerUp = pu;
	}
}
