package gamecomponent;

import java.util.ArrayList;

import sun.awt.Mutex;
import utilities.TextureHandler;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Entity;
import entities.Player;
import entities.Projectile;

public class Model {
	
	
	private boolean upKeyPressed,downKeyPressed,leftKeyPressed,rightKeyPressed;
	private ArrayList<Entity> entities;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Entity> stillEntities;
	private Player player;
	private Texture actionBar, actionBarSelection;
	
	private Mutex entitiesMutex;
	private Mutex stillEntitiesMutex;
	
//	ArrayList<NameTexture> textures;
	private TextureHandler textureHandler;
	
	private int width, height;
	
	int selectedWeapon = 0;
	
	private GameMap map;
	
	public Model (){
		
		entities = new ArrayList<Entity>();
		stillEntities = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();
		entitiesMutex = new Mutex();
		stillEntitiesMutex = new Mutex();
		
//		textures = new ArrayList<NameTexture>();
		textureHandler = new TextureHandler();
		
	}
	
	public void createGUI(){
		actionBar = textureHandler.getTextureByName("actionbar2.png");
		actionBarSelection = textureHandler.getTextureByName("actionbarselection.png");
	}
	
	
	public Texture getActionBar() {
		return actionBar;
	}



	public Texture getActionBarSelection() {
		return actionBarSelection;
	}



	public ArrayList<Entity> getEntitys(){
		return entities;
	}
	
	public ArrayList<Entity> getStillEntitys(){
		return stillEntities;
	}
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Mutex getEntitiesMutex() {
		return entitiesMutex;
	}
	
	public Mutex getStillEntitiesMutex(){
		return stillEntitiesMutex;
	}
	public void createPlayer(){
		player = new Player("Sir Eatalot", 5, 5, new Sprite(textureHandler.getTextureByName("ful.png")), 2, this.getTextureHandler());
	}
	
	//Author: Jimmy - wtf function, please help it with its life
	public void killProjectile(Entity e){
		int i = 0;
		boolean found = false;
		for(Entity ent : projectiles){
			if(ent.equals(e)){
				found = true;
				break;
			}
			i++;
		}
		getStillEntitiesMutex().lock();
		if(found){
			projectiles.remove(i);
			stillEntities.add(e);
		}
		getStillEntitiesMutex().unlock();
	}
	
	public int getSelectedWeapon(){
		return selectedWeapon;
	}
	
	public void checkPressedKey (int keyCode){
		switch (keyCode){
		case Keys.W: 
			upKeyPressed = true;
			break;
		case Keys.A:
			leftKeyPressed = true;
			break;
		case Keys.S:
			downKeyPressed = true;
			break;
		case Keys.D:
			rightKeyPressed = true;
			break;
		case Keys.NUM_1:
		case Keys.NUM_2:
		case Keys.NUM_3:
		case Keys.NUM_4:
		case Keys.NUM_5:
		case Keys.NUM_6:
		case Keys.NUM_7:
		case Keys.NUM_8:
		case Keys.NUM_9:
			selectedWeapon = keyCode-8;
			break;
		default:
			return;
		}
		
		updatePlayerMovingDirection();
		player.changeWeapon(selectedWeapon); //NY
		
	}
	
	public void checkReleasedKey (int keyCode){
		switch (keyCode){
		case Keys.W: 
			upKeyPressed = false;
			break;
		case Keys.A:
			leftKeyPressed = false;
			break;
		case Keys.S:
			downKeyPressed = false;
			break;
		case Keys.D:
			rightKeyPressed = false;
			break;
		default:
			return;
		}
		
		updatePlayerMovingDirection();
	}

	private void updatePlayerMovingDirection() {
		if(upKeyPressed && !downKeyPressed){
			if(leftKeyPressed && !rightKeyPressed){
				player.getVector().setVectorByDegree(player.getSpeed(), 135);
			}else if(rightKeyPressed && !leftKeyPressed){
				player.getVector().setVectorByDegree(player.getSpeed(), 45);
			}else{
				player.setVector(0, 1*player.getSpeed());
			}
		}
		else if(downKeyPressed && !upKeyPressed){
			if(leftKeyPressed && !rightKeyPressed){
				player.getVector().setVectorByDegree(player.getSpeed(), 225);
			}else if(rightKeyPressed && !leftKeyPressed){
				player.getVector().setVectorByDegree(player.getSpeed(), 315);
			}else{
				player.setVector(0, (-1)*player.getSpeed());
			}
		}else if(rightKeyPressed && !leftKeyPressed){
			player.setVector(player.getSpeed(), 0);
		}else if(leftKeyPressed && !rightKeyPressed){
			player.setVector((-1)*player.getSpeed(), 0);
		}else{
			player.setVector(0, 0);
		}
	}
	
	public void mouseButtonPressed(int x, int y, int mouseButton){
		if (mouseButton == Buttons.LEFT){
			this.getEntitiesMutex().lock();
			this.addProjectile(player.shoot(x+this.player.getX()-this.width/2, this.height/2+this.player.getY()-y));
			this.getEntitiesMutex().unlock();
		}
	}
	
	public void setViewSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public TextureHandler getTextureHandler(){
		return textureHandler;
	}

	public void createMap(){
		map = new GameMap(textureHandler);
	}
	
	public GameMap getMap(){
		return map;
	}
	
	public void mouseMoved(int mouse1, int mouse2) {
		
		double playerX =  mouse1-this.width/2;
		double playerY =  this.height/2-mouse2;
		
		double rot = Math.atan(playerX/playerY);

		if(playerY > 0){
			player.getSprite().setRotation((float) (360-Math.toDegrees(rot)));
		}else{
			player.getSprite().setRotation((float) (180-Math.toDegrees(rot)));
		}
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile p) {
		this.projectiles.add(p);
	}
}
