package gamecomponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import networking.SpaghettiClient;
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

public class Model {
	
	
	private boolean upKeyPressed,downKeyPressed,leftKeyPressed,rightKeyPressed;
	private ArrayList<Entity> entities;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Entity> stillEntities;
	private Map<Integer, Player> otherPlayers;

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
		otherPlayers = new HashMap<Integer, Player>();
		entitiesMutex = new Mutex();
		stillEntitiesMutex = new Mutex();
		
//		textures = new ArrayList<NameTexture>();
		textureHandler = new TextureHandler();
		
	}
	
	//kind of temporary implementation
	public void createServer(){
		try {
			SpaghettiServer server = new SpaghettiServer(54555, 54777, this, otherPlayers);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//kind of temporary implementation
	public void createClient(){
		try {
			SpaghettiClient client = new SpaghettiClient(54555, 54777, 5000, "IPADRESSGOESHERE", "MYNAME", this, otherPlayers);
			client.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public Map<Integer, Player> getOtherPlayers() {
		return otherPlayers;
	}
	
//	public void addPlayer(Player p){
//		otherPlayers.add(p);
//	}

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
		//testing powerup energydrink
		PowerUp testPowerUp = new Energydrink(5, 5, new Sprite(textureHandler.getTextureByName("extremelyuglydrink.png")));
		
		player = new Player("Sir Eatalot", 5, 5, new Sprite(textureHandler.getTextureByName("ful.png")), 2, this.getTextureHandler());
	}
	

//	public void addPlayer(String name, int x, int y, String s, int speed){
//		//test
//		Player test = new Player(name , x, y, new Sprite(this.getTextureHandler().getTextureByName(s)), speed);
//		test.setVector(1, 0);;
//		this.addPlayer(test);
//		
//	}
	
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
		getStillEntitiesMutex().lock();
		if(found){
			projectiles.remove(i);
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
		case Keys.SPACE:
			player.usePowerUp();
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
