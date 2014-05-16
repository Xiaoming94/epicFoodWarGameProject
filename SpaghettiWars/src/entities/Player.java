package entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Circle;

import utilities.PizzaSlicer;
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
	private float spriteHeight, spriteWidth;
	private ArrayList<PowerUp> activePowerUps, activePowerUpsTrashBin;
	
	private double speedMod;
	
	private int shootCooldown = 0;
	
	
	public Player(String name, double x, double y, Sprite sprite, double speed){
		super(x, y, sprite);
		createPlayer(name, speed);
	}

	public Player(String name, double x, double y, Sprite sprite, double speed, TextureHandler th){
		this(name, x, y, sprite, speed);
		textureHandler = th;
	}
	
	public Player(String name, double x, double y, Sprite sprite, double speed, int clientID, int objectID){
		super(x,y, new Vector(0,0), sprite, clientID, objectID);
		createPlayer(name, speed);
	}
	
	private void createPlayer(String name, double speed){
		this.name = name;
		this.setSpeed(speed);
		
		activePowerUps = new ArrayList<PowerUp>();
		activePowerUpsTrashBin = new ArrayList<PowerUp>();
		
		spriteWidth = this.getSprite().getWidth();
		spriteHeight = this.getSprite().getHeight();
		speedMod = 0;
		fatPoints = 0;
		isDead = false;
		System.out.println("creating player");
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
		if (damage > fatPoints){
            fatPoints = 0;
        }else {
            fatPoints -= damage;
        }
		weightChanged();
	}
	
	public void setWeight(double weight){
		fatPoints = weight;
		weightChanged();	
	}
	
	private void weightChanged(){
		this.getSprite().setSize(spriteWidth*(float)getScale(), spriteHeight*(float)getScale());
		this.getSprite().setOriginCenter();
		
		this.getSprite().setX((float)this.getX()-this.getSprite().getWidth()/2);
		this.getSprite().setY((float)this.getY()-this.getSprite().getHeight()/2);
		
		this.setSpeed(3-(this.getFatPoint()/50));
		this.updateVector();
		
		if(this.getFatPoint() > 99)
			kill();
	}
	
	@Override
	public void move() {
		super.move();
		
		for(PowerUp pu : activePowerUps)
			pu.update();
		
		for(PowerUp pu : activePowerUpsTrashBin)
			activePowerUps.remove(pu);
		
		if(shootCooldown > 0)
			shootCooldown--;
	}
	
	public void removePowerUpEffect(PowerUp pu){
		activePowerUpsTrashBin.add(pu);
	}
	
	public void kill(){
        //look over this shit. Sprite doesn't show
		//this.setSprite(new Sprite(textureHandler.getTextureByName("FatDeadPlayerSprite.png")));
		isDead = true;
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
	
	public Projectile shoot(Position pos, PizzaSlicer pizzaSlicer){

		if(shootCooldown < 1){
			if(selectedWeapon == "pizza"){
				Pizza p = new Pizza(this.getX(), this.getY(), new Vector(0,0), new Sprite(textureHandler.getTextureByName("pizza.png")), pos, pizzaSlicer);
				p.setVector(pos);
				shootCooldown = 50;
				return p;
			}else{
				Meatball mb = new Meatball(this.getX(), this.getY(), new Vector(0,0), new Sprite(textureHandler.getTextureByName("Kottbulle.png")));
				mb.setVector(pos);
				shootCooldown = 25;
				return mb;
			}
		}
		return null;
	}
	
	public boolean overlaps(Rectangle r){
		return this.getX() + this.getVector().getDeltaX() + this.getSprite().getWidth()/2 > r.getX() && this.getX() + this.getVector().getDeltaX() - this.getSprite().getWidth()/2 < r.getX() + r.getWidth() &&
				this.getY() + this.getVector().getDeltaY() + this.getSprite().getWidth()/2 > r.getY() && this.getY() + this.getVector().getDeltaY() - this.getSprite().getWidth()/2 < r.getY() + r.getHeight();
	}
	
	public boolean stationaryOverlaps(Rectangle r){
		return this.getX() + this.getSprite().getWidth()/2 > r.getX() && this.getX() - this.getSprite().getWidth()/2 < r.getX() + r.getWidth() &&
				this.getY() + this.getSprite().getWidth()/2 > r.getY() && this.getY() - this.getSprite().getWidth()/2 < r.getY() + r.getHeight();
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
		
		if(shootCooldown > 0)
			shootCooldown--;
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
	
	public PowerUp usePowerUp(){
		if(powerUp != null){
			PowerUp temp = powerUp;
			this.activePowerUps.add(powerUp);
			powerUp.applyEffects(this); // or setActive() ? i have no idea what i'm doing...
			this.powerUp = null;
			return temp;
		}
		return null;
	}
	
	public PowerUp getPowerUp(){
		return powerUp;
	}
	
	public void setPowerUp(PowerUp pu){
		powerUp = pu;
	}

	public void moveOutOfWalls(Player player, List<Obstacle> obstacles) {
		boolean top, bottom, left, right;
		
		boolean topLeft, topRight, bottomRight, bottomLeft;//ny
		
		while(true){
			top = bottom = left = right = false;
			
			topLeft = topRight = bottomRight = bottomLeft = false; //ny
			
			//original solutions, works at corner edges, not in corners
			//check if player is obstructed by obstacle
//			for (Entity o : obstacles)
//				if (player.stationaryOverlaps(o.getSprite().getBoundingRectangle())) {
//					if(player.getPosition().getX() > o.getPosition().getX() && player.getSprite().getWidth()/2 + o.getSprite().getWidth()/2 > player.getSprite().getX() - o.getSprite().getX())
//						left = true;
//					if(player.getPosition().getX() < o.getPosition().getX() && player.getSprite().getWidth()/2 + o.getSprite().getWidth()/2 > o.getSprite().getX() - player.getSprite().getX())
//						right = true;
//					if(player.getPosition().getY() > o.getPosition().getY() && player.getSprite().getWidth()/2 + o.getSprite().getHeight()/2 > player.getSprite().getY() - o.getSprite().getY())
//						bottom = true;
//					if(player.getPosition().getY() < o.getPosition().getY() && player.getSprite().getWidth()/2 + o.getSprite().getHeight()/2 > o.getSprite().getY() - player.getSprite().getY())
//						top = true;
//				}
			

			//alterative solution checking middle points works fine for
			//when the player stands in a corner, but doesn't work
			//when the player stands at a corner edge, for example by a table
			//fixing needed
			
			//presumes sprite pos in middle
			//coordinates for sides
			float topX = (float)(player.getX());
			float topY = (float)(player.getY() + player.getSprite().getHeight()/2);
			float rightX = (float)(player.getX() + player.getSprite().getWidth());
			float rightY = (float)(player.getY());
			float bottomX = (float)(player.getX());
			float bottomY = (float) (player.getY() - player.getSprite().getHeight()/2);
			float leftX = (float)(player.getX() - player.getSprite().getWidth()/2);
			float leftY = (float) (player.getY());
			
			//coordinates for corners
			float topLeftCornerX = (float)(player.getX() - player.getSprite().getWidth()/2);
			float topLeftCornerY = (float)(player.getY() + player.getSprite().getHeight()/2);
			float topRightCornerX = (float)(player.getX() + player.getSprite().getWidth()/2);
			float topRightCornerY = (float)(player.getY() + player.getSprite().getHeight()/2);
			float bottomRightCornerX = (float)(player.getX() + player.getSprite().getWidth()/2);
			float bottomRightCornerY = (float)(player.getY() - player.getSprite().getHeight()/2);
			float bottomLeftCornerX = (float)(player.getX() - player.getSprite().getWidth()/2);
			float bottomLeftCornerY = (float)(player.getY() - player.getSprite().getHeight()/2);
			
			for (Entity o : obstacles)
				if (player.stationaryOverlaps(o.getSprite().getBoundingRectangle())) {
					
					//check if middle points are in obstacle
					if(o.getSprite().getBoundingRectangle().contains(leftX, leftY)){
						left = true;
					}
					if(o.getSprite().getBoundingRectangle().contains(rightX, rightY)){
						right = true;
					}
					if(o.getSprite().getBoundingRectangle().contains(bottomX, bottomY)){
						bottom = true;
					}
					if(o.getSprite().getBoundingRectangle().contains(topX,topY)){
						top = true;
					}
					
					//check if corners are in obstacle
					if(o.getSprite().getBoundingRectangle().contains(topLeftCornerX, topLeftCornerY)){
						topLeft = true;
					}
					if(o.getSprite().getBoundingRectangle().contains(topRightCornerX, topRightCornerY)){
						topRight = true;
					}
					if(o.getSprite().getBoundingRectangle().contains(bottomRightCornerX, bottomRightCornerY)){
						bottomRight = true;
					}
					if(o.getSprite().getBoundingRectangle().contains(bottomLeftCornerX, bottomLeftCornerY)){
						bottomLeft = true;
					}
				}

		
			if(!left && !right && !bottom && !top)
				break;
			
			System.out.println("Before vector modification:");
			System.out.println("x: " + player.getPosition().getX());
			System.out.println("y: " + player.getPosition().getY());
			System.out.println("After vector modification:");
			System.out.println("x: " + player.getPosition().getX());
			System.out.println("y: " + player.getPosition().getY());
			
			
			//IN corner move
//			if(left && top){
//				player.getPosition().setX(player.getPosition().getX()+1);
//				player.getPosition().setY(player.getPosition().getY()-1);
//			}
//			if(left && bottom){
//				player.getPosition().setX(player.getPosition().getX()+1);
//				player.getPosition().setY(player.getPosition().getY()+1);
//			}
//			if(right && top){
//				player.getPosition().setX(player.getPosition().getX()-1);
//				player.getPosition().setY(player.getPosition().getY()-1);
//			}
//			if(right && bottom){
//				player.getPosition().setX(player.getPosition().getX()-1);
//				player.getPosition().setY(player.getPosition().getY()+1);
//			}
			
//			//corner edge move
//			if(bottomLeft && !left && !bottom){
//				player.getPosition().setX(player.getPosition().getX()+1);
//				player.getPosition().setY(player.getPosition().getY()+1);
//			}
		


			
			//original solution
			if(left && !right)
				player.getPosition().setX(player.getPosition().getX()+1);
			else if(!left && right)
				player.getPosition().setX(player.getPosition().getX()-1);	
			if(top && !bottom)
				player.getPosition().setY(player.getPosition().getY()-1);
			else if(!top && bottom)
				player.getPosition().setY(player.getPosition().getY()+1);
			

			
			player.updateSpritePos();
			System.out.println("" + top + bottom + left + right);
			
			System.out.println("After vector modification:");
			System.out.println("x: " + player.getPosition().getX());
			System.out.println("y: " + player.getPosition().getY());
		}
		
	}
}
