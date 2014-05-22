//Author: Louise
//Heavy modifications by Jimmy

//A class to represent all mutual functionality of the Projectile entities.

package entities;

import utilities.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;
import java.util.Map;

public abstract class Projectile extends Entity{
	
	private final int damage;
	private double range;
	private boolean dead;
	private int cooldown;

	private ProjectileState state = ProjectileState.FLYING;
	
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
	
	protected boolean update() {
        this.range -= getSpeed()/2; //why the fuck do we need to divide by 2?!

        if (range < 1)
            this.kill();
        
        if(!isDead())
        	this.move();
        
		return dead;
    }
	
	protected void movePlayerOutOfWalls(Player player, List<Obstacle> obstacles){
		player.moveOutOfWalls(player, obstacles);
	}

    public abstract void update(List<Obstacle> obstacles, Map<Integer, Player> playerlist, Player player);

    public ProjectileState getState() {
        return state;
    }

    public void setState(ProjectileState state) {
        this.state = state;
    }
    
    public int getCooldown() {
		return cooldown;
	}
    
    public void setCooldown(int cd) {
    	cooldown = cd;
    }
}
