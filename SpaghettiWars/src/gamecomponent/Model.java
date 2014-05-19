package gamecomponent;

import java.util.*;

import sun.awt.Mutex;
import utilities.PizzaSlicer;
import utilities.Position;
import utilities.TextureHandler;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.DietPill;
import entities.Energydrink;
import entities.Entity;
import entities.Player;
import entities.PowerUp;
import entities.Projectile;

/**
 * This class is for the Model of this Game (working title) SpaghettiWars
 * 
 * @author Jimmy Eliasson Malmer Model class holds the data which the View uses
 *         to paint the graphics of the game using LibGDX
 */
public class Model extends Observable {

	private boolean upKeyPressed, downKeyPressed, leftKeyPressed,
			rightKeyPressed;
	private final ArrayList<Projectile> projectiles;
	private final ArrayList<Entity> stillEntities;
	private final Map<Integer, Player> otherPlayers;
	private final List<PowerUp> pickUps;
	private final ArrayList<Projectile> temporaryProjectiles;

	private Player player;
	private Texture actionBar, actionBarSelection, powerUpBar;

	private final Mutex pickUpsMutex;
	private final Mutex stillEntitiesMutex;

	private final Mutex projectilesMutex;
	private final Mutex otherPlayersMutex;

	private final Mutex temporaryProjectilesMutex;

	private TextureHandler textureHandler;

	private double width, height;
	private double startWidth, startHeight;

	private int selectedWeapon = 0;

	private PizzaSlicer pizzaSlicer;
	
	private boolean isControllerRunning;

	// private SpaghettiFace networkObject;

	private GameMap map;
	private boolean gameActive;

	public final int playerSpawnX = 1000;
	public final int playerSpawnY = 1000;

	/**
	 * The first Constructor of the Model Object Constucts the Model and
	 * initiates the components of the Model
	 */
	public Model() {
		stillEntities = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();
		otherPlayers = new HashMap<Integer, Player>();
		pickUps = new ArrayList<PowerUp>();
		temporaryProjectiles = new ArrayList<Projectile>();
		stillEntitiesMutex = new Mutex();
		pickUpsMutex = new Mutex();

		projectilesMutex = new Mutex();
		otherPlayersMutex = new Mutex();

		temporaryProjectilesMutex = new Mutex();
		
		isControllerRunning = true;

		// textures = new ArrayList<NameTexture>();
		textureHandler = TextureHandler.getTextureHandler();

		pizzaSlicer = new PizzaSlicer(this);
	}

	// kind of temporary implementation

	/**
	 * Method for creating basic gui elements
	 */

	public void createGUI() {
		actionBar = textureHandler.getTextureByName("actionbar2.png");
		actionBarSelection = textureHandler
				.getTextureByName("actionbarselection.png");
		powerUpBar = textureHandler.getTextureByName("powerupholder.png");
	}

	/**
	 * Getter Accessor for the action bar
	 * 
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
	 * 
	 * @return actionBarSelection - the game Action bar selection
	 */

	public Texture getActionBarSelection() {
		return actionBarSelection;
	}

	public Map<Integer, Player> getOtherPlayers() {
		return otherPlayers;
	}

	public ArrayList<Entity> getStillEntitys() {
		return stillEntities;
	}

	public Player getPlayer() {
		return player;
	}

//	public Mutex getEntitiesMutex() {
//		return entitiesMutex;
//	}

	public Mutex getStillEntitiesMutex() {
		return stillEntitiesMutex;
	}

	public List<PowerUp> getPickUps() {
		return pickUps;
	}

	public Mutex getPickUpsMutex() {
		return pickUpsMutex;
	}

	public Mutex getProjectilesMutex() {
		return projectilesMutex;
	}

	public Mutex getOtherPlayersMutex() {
		return otherPlayersMutex;
	}

	public PizzaSlicer getPizzaSlicer() {
		return pizzaSlicer;
	}

	public void createPlayer(int x, int y) {

		PowerUp testPowerUp = new Energydrink(5, 5, new Sprite(
				textureHandler.getTextureByName("extremelyuglydrink.png")));
		player = new Player("Sir Eatalot", x, y, new Sprite(
				textureHandler.getTextureByName("ful.png")), 3,
				this.getTextureHandler());
		player.setPowerUp(testPowerUp);
	}

	public void createEnergyDrink(Position pos) {
		PowerUp pu = new Energydrink(pos.getX(), pos.getY(), new Sprite(
				textureHandler.getTextureByName("extremelyuglydrink.png")));
		pickUps.add(pu);
		setChanged();//ny
		notifyObservers(pu);//ny
	}

	public void createDietPill(Position pos) {
		PowerUp pu = new DietPill(pos.getX(), pos.getY(), new Sprite(
				textureHandler.getTextureByName("dietpill.png")));
		pickUps.add(pu);
		setChanged();
		notifyObservers(pu);
	}

	// Author: Jimmy - wtf function, please help it with its life
	public void killProjectile(Entity e) {
		int i = 0;
		boolean found = false;
		projectilesMutex.lock();
		for (Entity ent : projectiles) {
			if (ent.equals(e)) {
				found = true;
				break;
			}
			i++;
		}
		projectilesMutex.unlock();

		if (found) {
			getProjectilesMutex().lock();// ny
			projectiles.remove(i);
			getProjectilesMutex().unlock();// ny
			getStillEntitiesMutex().lock();
			stillEntities.add(e);
			getStillEntitiesMutex().unlock();
		}
	}

	public void removeProjectile(Entity e) {
		int i = 0;
		boolean found = false;
		this.projectilesMutex.lock();
		for (Entity ent : projectiles) {
			if (ent.equals(e)) {
				found = true;
				break;
			}
			i++;
		}
		this.projectilesMutex.unlock();

		if (found) {
			// this.networkObject.killProjectile(projectiles.get(i));
			this.setChanged();
			this.notifyObservers(projectiles.get(i));
			getProjectilesMutex().lock(); // ny
			projectiles.remove(i);
			getProjectilesMutex().unlock(); // ny
		}

	}

	public void killPlayer(Entity e) {
		boolean found = false;
		Iterator<Integer> iterator = otherPlayers.keySet().iterator();
		int toBeRemoved = 0;
		int key;
		while (iterator.hasNext()) {
			key = iterator.next();
			if (otherPlayers.get(key).equals(e)) {
				found = true;
				toBeRemoved = key;
				break;
			}
		}

		if (found) {
			getOtherPlayersMutex().lock();
			otherPlayers.remove(toBeRemoved);
			getOtherPlayersMutex().unlock();

			getStillEntitiesMutex().lock();
			stillEntities.add(e);
			getStillEntitiesMutex().unlock();
		}
	}

	public void removePickUp(Entity e) {
		int i = 0;
		boolean found = false;
		for (Entity ent : pickUps) {
			if (ent.equals(e)) {
				found = true;
				break;
			}
			i++;
		}

		if (found) {
			getPickUpsMutex().lock();
			pickUps.remove(i);
			getPickUpsMutex().unlock();// ny
		}
	}

	public int getSelectedWeapon() {
		return selectedWeapon;
	}

	public void checkPressedKey(int keyCode) {
		switch (keyCode) {
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
			selectedWeapon = keyCode - 8;
			break;
		case Keys.SPACE:
			this.setChanged();
			this.notifyObservers(player.usePowerUp());
			break;
		case Keys.ESCAPE:
			this.setChanged();
			this.notifyObservers(new Integer(keyCode));
			break;
		default:
			return;
		}

		updatePlayerMovingDirection();
		player.changeWeapon(selectedWeapon); // NY

	}

	public void checkReleasedKey(int keyCode) {
		switch (keyCode) {
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
		if (upKeyPressed && !downKeyPressed) {
			if (leftKeyPressed && !rightKeyPressed) {
				player.getVector().setVectorByDegree(player.getSpeed(), 135);
			} else if (rightKeyPressed && !leftKeyPressed) {
				player.getVector().setVectorByDegree(player.getSpeed(), 45);
			} else {
				player.setVector(0, player.getSpeed());
			}
		} else if (downKeyPressed && !upKeyPressed) {
			if (leftKeyPressed && !rightKeyPressed) {
				player.getVector().setVectorByDegree(player.getSpeed(), 225);
			} else if (rightKeyPressed && !leftKeyPressed) {
				player.getVector().setVectorByDegree(player.getSpeed(), 315);
			} else {
				player.setVector(0, (-1) * player.getSpeed());
			}
		} else if (rightKeyPressed && !leftKeyPressed) {
			player.setVector(player.getSpeed(), 0);
		} else if (leftKeyPressed && !rightKeyPressed) {
			player.setVector((-1) * player.getSpeed(), 0);
		} else {
			player.setVector(0, 0);
		}
	}

	public void mouseButtonPressed(double x, double y, int mouseButton) {
		if (mouseButton == Buttons.LEFT) {
			Projectile p = player.shoot(new Position((startWidth / width)
					* (x - this.width / 2) + this.player.getX(),
					(startHeight / height) * (this.height / 2 - y)
							+ this.player.getY()), pizzaSlicer);
			if (p != null) {
				this.addProjectile(p);

				// this.getNetworkObject().sendProjectile(p);
				this.setChanged();
				this.notifyObservers(p);
			}
		}
	}

	public void setStartViewSize(int width, int height) {
		this.startHeight = height;
		this.startWidth = width;
	}

	public void setViewSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public TextureHandler getTextureHandler() {
		return textureHandler;
	}

	public void createMap() {
		map = new GameMap(textureHandler);
	}

	public GameMap getMap() {
		return map;
	}

	public void mouseMoved(int mouse1, int mouse2) {

		double playerX = mouse1 - this.width / 2;
		double playerY = this.height / 2 - mouse2;

		double rot = 0;

		if (playerX != 0 || playerY != 0)
			rot = Math.atan(playerX / playerY);

		if (playerY >= 0) {
			player.getSprite().setRotation((float) (360 - Math.toDegrees(rot)));
		} else {
			player.getSprite().setRotation((float) (180 - Math.toDegrees(rot)));
		}
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile p) {
		if (p != null) {
			getProjectilesMutex().lock();// ny
			this.projectiles.add(p);
			getProjectilesMutex().unlock();// ny
		}
	}

	public void addTempProjectiles() {
		temporaryProjectilesMutex.lock();
		for (Projectile p : temporaryProjectiles) {
			addProjectile(p);
			setChanged();
			notifyObservers(p);
		}
		temporaryProjectiles.clear();
		temporaryProjectilesMutex.unlock();
	}

	public void addTempProjectile(Projectile p) {
		temporaryProjectilesMutex.lock();
		temporaryProjectiles.add(p);
		temporaryProjectilesMutex.unlock();
	}

	public void setGameActive(boolean b) {
		this.gameActive = b;
	}

	public boolean isGameActive() {
		return this.gameActive;
	}

	@Override
	public void setChanged() {
		super.setChanged();
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	public void disconnect(){
		setChanged();
		notifyObservers("disconnect");
	}
	
	public void setControllerRunning(boolean isRunning){
		isControllerRunning = isRunning;
	}
	
	public boolean isControllerRunning(){
		return isControllerRunning;
	}

	public void reset() {
		projectiles.clear();
		stillEntities.clear();
		otherPlayers.clear();
		pickUps.clear();
		temporaryProjectiles.clear();
		map = new GameMap(textureHandler);
	}
	
}
