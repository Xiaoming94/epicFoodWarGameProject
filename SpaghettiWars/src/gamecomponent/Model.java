package gamecomponent;

import java.io.IOException;
import java.util.*;

import networking.SpaghettiClient;
import networking.SpaghettiFace;
import networking.SpaghettiServer;
import sun.awt.Mutex;
import utilities.TextureHandler;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Energydrink;
import entities.Entity;
import entities.Player;
import entities.PowerUp;
import entities.Projectile;

/**
 * This class is for the Model of this Game (working title) SpaghettiWars
 * @author Jimmy Eliasson Malmer
 * Model class holds the data which the View uses to paint the graphics of the game using LibGDX
 */
public class Model {

	private boolean upKeyPressed,downKeyPressed,leftKeyPressed,rightKeyPressed;
	private ArrayList<Entity> entities;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Entity> stillEntities;
	private Map<Integer, Player> otherPlayers;

	private Player player;
	private Texture actionBar, actionBarSelection, powerUpBar;

	private Mutex entitiesMutex;
	private Mutex stillEntitiesMutex;
	
	private Mutex projectilesMutex;
	private Mutex otherPlayersMutex;

//	ArrayList<NameTexture> textures;
	private TextureHandler textureHandler;

	private double width, height;
	private double startWidth, startHeight;

	private int selectedWeapon = 0;
	
	private SpaghettiFace networkObject;

	private GameMap map;
	private boolean gameActive;

    /**
     * The first Constructor of the Model Object
     * Constucts the Model and initiates the components of the Model
     */
	public Model (){

		entities = new ArrayList<Entity>();
		stillEntities = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();
		otherPlayers = new HashMap<Integer, Player>();
		entitiesMutex = new Mutex();
		stillEntitiesMutex = new Mutex();
		
		projectilesMutex = new Mutex();
		otherPlayersMutex = new Mutex();

//		textures = new ArrayList<NameTexture>();
		textureHandler = new TextureHandler();

	}
	
	//kind of temporary implementation
	

    /**
     * Method for creating basic gui elements
     */

	public void createGUI(){
		actionBar = textureHandler.getTextureByName("actionbar2.png");
		actionBarSelection = textureHandler.getTextureByName("actionbarselection.png");
		powerUpBar = textureHandler.getTextureByName("powerupholder.png");
	}

    /**
     * Getter Accessor for the action bar
     * @return actionBar - the game Action bar
     */

	public Texture getActionBar() {
		return actionBar;
	}
	
	public Texture getPowerUpBar() {
		return powerUpBar;
	}

    /**
     * Getter Accessor for the action bar selection
     * @return actionBarSelection - the game Action bar selection
     */

	public Texture getActionBarSelection() {
		return actionBarSelection;
	}

	public Map<Integer, Player> getOtherPlayers() {
		return otherPlayers;
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
	
	
	public Mutex getProjectilesMutex(){
		return projectilesMutex;
	}
	
	public Mutex getOtherPlayersMutex(){
		return otherPlayersMutex;
	}

	public void createPlayer(){
		//testing powerup energydrink
		PowerUp testPowerUp = new Energydrink(5, 5, new Sprite(textureHandler.getTextureByName("extremelyuglydrink.png")));
		player = new Player("Sir Eatalot", 5, 5, new Sprite(textureHandler.getTextureByName("ful.png")), 3, this.getTextureHandler());
		player.setPowerUp(testPowerUp);
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
		
		
		if(found){
			getProjectilesMutex().lock();//ny
			projectiles.remove(i);
			getProjectilesMutex().unlock();//ny
			getStillEntitiesMutex().lock();
			stillEntities.add(e);
			getStillEntitiesMutex().unlock();
		}
		
		
	}

	public void removeProjectile(Entity e){
		int i = 0;
		boolean found = false;
		for(Entity ent : projectiles){
			if(ent.equals(e)){
				found = true;
				break;
			}
			i++;
		}
		
		
		if(found){
			getProjectilesMutex().lock(); //ny
			projectiles.remove(i);
			getProjectilesMutex().unlock(); //ny
		}
		
		
	}

	
	public void killPlayer(Entity e){
		boolean found = false;
		Iterator<Integer> iterator = otherPlayers.keySet().iterator();
		int toBeRemoved = 0;
		int key;
		while(iterator.hasNext()){
			key = iterator.next();
			if(otherPlayers.get(key).equals(e)){
				found = true;
				toBeRemoved = key;
				break;
			}
		}
		
		if(found){
			getOtherPlayersMutex().lock();
			otherPlayers.remove(toBeRemoved);
			getOtherPlayersMutex().unlock();
			
			getStillEntitiesMutex().lock();
			stillEntities.add(e);
			getStillEntitiesMutex().unlock();
		}
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
		case Keys.SPACE:
			player.usePowerUp();
			break;
		case Keys.ESCAPE:
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
				player.setVector(0, player.getSpeed());
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

	public void mouseButtonPressed(double x, double y, int mouseButton){
		if (mouseButton == Buttons.LEFT){
			Projectile p = player.shoot((startWidth/width)*(x-this.width/2)+this.player.getX(), (startHeight/height)*(this.height/2-y)+this.player.getY());
			if(p != null){
				this.getEntitiesMutex().lock();
				this.addProjectile(p);
				this.getEntitiesMutex().unlock();
				
				this.getNetworkObject().sendProjectile(p);
			}
		}
	}

	public void setStartViewSize(int width, int height){
		this.startHeight = height;
		this.startWidth = width;
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
		
		double rot = 0;
		
		if(playerX != 0 || playerY != 0)
			rot = Math.atan(playerX/playerY);

		if(playerY >= 0){
			player.getSprite().setRotation((float) (360-Math.toDegrees(rot)));
		}else{
			player.getSprite().setRotation((float) (180-Math.toDegrees(rot)));
		}
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile p) {
		getProjectilesMutex().lock();//ny
		this.projectiles.add(p);
		getProjectilesMutex().unlock();//ny
	}
	
	public SpaghettiFace getNetworkObject(){
		return networkObject;
	}
	
	public void setNetworkObject(SpaghettiFace networkObject){
		this.networkObject = networkObject;
	}

	public void setGameActive(boolean b) {
		this.gameActive = b;
	}
	
	public boolean isGameActive(){
		return this.gameActive;
	}
}
