package entities;

import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

public abstract class Projectile extends Entity{
	
	private final int damage;
	private double range;
	private boolean dead;
    private ProjectileState state;

    public Projectile(double x, double y, Vector vector, Sprite sprite, int damage, double range){
		super(x, y, vector, sprite);
		this.damage = damage;
		this.range = range;
		this.dead = false;
	}
	
	public Projectile(double x, double y, Vector vector, Sprite sprite, int damage, double range, int clientID, int objectID){
		super(x,y,vector,sprite, clientID, objectID);
		this.damage = damage;
		this.range = range;
		this.dead = false;
	}

    public void setRange(double range){ 
    	this.range = range; 
    }
	
	public int getDamage(){
		return damage;
	}
	
	public double getRange(){
		return range;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public void kill(){
		dead = true;
		this.stop();
	}
	
	public void update() {
        this.range -= getSpeed()/2; //why the fuck do we need to divide by 2?!

        if (range < 1)
            this.kill();
        
        if(!isDead())
        	this.move();
    }

    public abstract void checkColliding(List<Obstacle> obstacles, List<Player> playerlist);

    public ProjectileState getState() {
        return state;
    }

    public void setState(ProjectileState state) {
        this.state = state;
    }
}
