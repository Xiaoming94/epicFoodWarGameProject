package gamecomponent;

import java.util.ArrayList;

import sun.awt.Mutex;
import utilities.Position;
import utilities.TextureHandler;

import com.badlogic.gdx.graphics.g2d.Sprite;



import entities.Furniture;
//import entities.Furniture;
import entities.Obstacle;
import entities.Wall;

public class GameMap {
	
	private TextureHandler textureHandler;

	private ArrayList<Obstacle> obstacles = new ArrayList <Obstacle>();
	private ArrayList<Obstacle> decorations = new ArrayList <Obstacle>();
	
	private ArrayList<Position> powerUpSpawnLocations = new ArrayList<Position>();
	private int maxPowerUps;
	
	private Mutex obstacleMutex;
	private Mutex decorationMutex;
	
	private Sprite simpleWall;
	private Sprite smallSimpleWall;
	private Sprite table;
	
	public GameMap(TextureHandler textureHandler){
		this.textureHandler = textureHandler;
		simpleWall = new Sprite(textureHandler.getTextureByName("wall.png"));
		smallSimpleWall = new Sprite(textureHandler.getTextureByName("smallWall.png"));
		table = new Sprite(textureHandler.getTextureByName("greenfurniture.png"));
		createDiner(100,0);
		createKitchen(100, 0);
		
		maxPowerUps = powerUpSpawnLocations.size()/3;
		createEntrence(100, -3300);
		createSmokeSpot(100,0);		
		
		obstacleMutex = new Mutex();
		decorationMutex = new Mutex();

//		Sprite greenThing = new Sprite(textureHandler.getTextureByName("greenfurniture.png"));
//		greenThing.setSize(200,100);
//		addObstacle(new Furniture(0, -700, greenThing));
	}
	
	public int getMaxPowerUps() {
		return maxPowerUps;
	}

	public void addObstacle(Obstacle o){
		obstacles.add(o);
	}
	
	public void addDecoration(Obstacle o){
		decorations.add(o);
	}
	
	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}

	public ArrayList<Obstacle> getDecorations(){
		return decorations;
	}
	
	public void createNewRoom(double x, double y){
		//Sprite sprite = new Sprite(textureHandler.getTextureByName("wall.png"));
		Sprite sprite = new Sprite(simpleWall);
		double x1 = sprite.getHeight()/2.0 - sprite.getWidth()/2 + x;
		double y1 = 0 + y;
		double x2 = -sprite.getHeight()/2.0 + sprite.getWidth()/2 +x;
		double y2 = 0 + y;
		double x3 = 300 + x;
		double y3 = -sprite.getHeight()/2.0 + sprite.getWidth()/2 + y;
		double x4 = 0 + x;
		double y4 = sprite.getHeight()/2.0 - sprite.getWidth()/2 + y;
		addDecoration(new Wall(x2 + sprite.getHeight()/2 - sprite.getWidth()/2, y2, new Sprite(textureHandler.getTextureByName("uglyfloor.png"))));
		addObstacle(new Wall(x1, y1, new Sprite(sprite)));
		addObstacle(new Wall(x2, y2, new Sprite(sprite)));
		sprite.setRotation(90);
		addObstacle(new Wall(x3, y3, new Sprite(sprite)));
		addObstacle(new Wall(x4, y4, new Sprite(sprite)));
	}
	

	public Mutex getObstacleMutex(){
		return obstacleMutex;
	}
	
	public Mutex getDecorationMutex(){
		return decorationMutex;
	}
	
	
	public void createDiner(double x, double y){
		Sprite wallSprite = new Sprite(simpleWall);
		Sprite tableSprite = new Sprite(table);
		
		//east/west walls
		addObstacle(new Wall(x+wallSprite.getWidth()/2, y+wallSprite.getHeight()/2, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getWidth()/2, y+wallSprite.getHeight()*1.5+200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getWidth()/2+wallSprite.getHeight()*3.5, y+wallSprite.getHeight()/2, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getWidth()/2+wallSprite.getHeight()*3.5, y+wallSprite.getHeight()*1.5+200, new Sprite(wallSprite)));
		
		//north/south walls
		wallSprite.rotate(90);
		//south
		addObstacle(new Wall(x+wallSprite.getHeight()/2, y+wallSprite.getWidth()/2, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*1.5+300, y+wallSprite.getWidth()/2, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*2.5+600, y+wallSprite.getWidth()/2, new Sprite(wallSprite)));
		//north
		addObstacle(new Wall(x+wallSprite.getHeight()/2, y+wallSprite.getWidth()/2+wallSprite.getHeight()*2+200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*1.5+200, y+wallSprite.getWidth()/2+wallSprite.getHeight()*2+200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*2.5+200, y+wallSprite.getWidth()/2+wallSprite.getHeight()*2+200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*2.5+600, y+wallSprite.getWidth()/2+wallSprite.getHeight()*2+200, new Sprite(wallSprite)));
		
		//tables
		addObstacle(new Furniture(x+750, y+750, new Sprite(tableSprite)));
		
		addObstacle(new Furniture(x+300, y+1900, new Sprite(tableSprite)));
		addObstacle(new Furniture(x+300+tableSprite.getWidth(), y+1900, new Sprite(tableSprite)));
		addObstacle(new Furniture(x+300+tableSprite.getWidth()*2, y+1900, new Sprite(tableSprite)));
		addObstacle(new Furniture(x+300+tableSprite.getWidth()*3, y+1900, new Sprite(tableSprite)));
		addObstacle(new Furniture(x+300+tableSprite.getWidth()*4, y+1900, new Sprite(tableSprite)));
		addObstacle(new Furniture(x+300+tableSprite.getWidth()*5, y+1900, new Sprite(tableSprite)));
		
		//floor
		Sprite floor = new Sprite(textureHandler.getTextureByName("uglyfloor.png"));
		addDecoration(new Wall(x+wallSprite.getHeight()/2, y+wallSprite.getHeight()/2, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()/2+floor.getHeight(), y+wallSprite.getHeight()/2, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()/2+floor.getHeight()*2, y+wallSprite.getHeight()/2, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()/2+floor.getHeight()*2.5, y+wallSprite.getHeight()/2, new Sprite(floor)));
		
		addDecoration(new Wall(x+wallSprite.getHeight()/2, y+wallSprite.getHeight()/2+floor.getHeight(), new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()/2+floor.getHeight(), y+wallSprite.getHeight()/2+floor.getHeight(), new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()/2+floor.getHeight()*2, y+wallSprite.getHeight()/2+floor.getHeight(), new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()/2+floor.getHeight()*2.5, y+wallSprite.getHeight()/2+floor.getHeight(), new Sprite(floor)));
		
		int floorOffset = 300;
		addDecoration(new Wall(x+wallSprite.getHeight()/2, y+wallSprite.getHeight()/2+floor.getHeight()+floorOffset, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()/2+floor.getHeight(), y+wallSprite.getHeight()/2+floor.getHeight()+floorOffset, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()/2+floor.getHeight()*2, y+wallSprite.getHeight()/2+floor.getHeight()+floorOffset, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()/2+floor.getHeight()*2.5, y+wallSprite.getHeight()/2+floor.getHeight()+floorOffset, new Sprite(floor)));
		
		//spawn locations for powerUps
		powerUpSpawnLocations.add(new Position(x+300, y+2100));
		
		//test
		powerUpSpawnLocations.add(new Position(x+400, y+2100));
		powerUpSpawnLocations.add(new Position(x+500, y+2100));
		powerUpSpawnLocations.add(new Position(x+600, y+2100));
		powerUpSpawnLocations.add(new Position(x+700, y+2100));
		powerUpSpawnLocations.add(new Position(x+800, y+2100));
		powerUpSpawnLocations.add(new Position(x+900, y+2100));
		powerUpSpawnLocations.add(new Position(x+1000, y+2100));
		powerUpSpawnLocations.add(new Position(x+1100, y+2100));
		
		
	}
	
	public ArrayList<Position> getPowerUpSpawnLocations() {
		return powerUpSpawnLocations;
	}

	public void createKitchen(double x, double y){
		Sprite wallSprite = new Sprite(simpleWall);
		Sprite tableSprite = new Sprite(table);
		
	//first off, vertical walls
		
		//west kitchen wall, short one
		addObstacle(new Wall(x+wallSprite.getWidth()/2, y+wallSprite.getHeight()*2.5 + wallSprite.getWidth() +200, new Sprite(wallSprite)));

		//east kitchen walls (from south to north)
		addObstacle(new Wall(x+wallSprite.getHeight()*5 + 200, y+wallSprite.getHeight()/2, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*5 + 200, y+wallSprite.getHeight()*1.5, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*5 + 200, y+wallSprite.getHeight()*2.5, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*5 + 200, y+wallSprite.getHeight()*3 - wallSprite.getWidth(), new Sprite(wallSprite)));
		
		//(same wall as diner east wall)
		addObstacle(new Wall(x+wallSprite.getWidth()/2+wallSprite.getHeight()*3.5, y+wallSprite.getHeight()/2, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getWidth()/2+wallSprite.getHeight()*3.5, y+wallSprite.getHeight()*1.5+200, new Sprite(wallSprite)));
		
		
		tableSprite.rotate(90);
		//working desk
		//south, west
		addObstacle(new Furniture(x + wallSprite.getHeight()*4, y + wallSprite.getHeight()/2, new Sprite(tableSprite)));
		addObstacle(new Furniture(x + wallSprite.getHeight()*4, y + wallSprite.getHeight()/2 + tableSprite.getWidth(), new Sprite(tableSprite)));
		//south, east
		addObstacle(new Furniture(x + wallSprite.getHeight()*4.7, y + wallSprite.getHeight()/2, new Sprite(tableSprite)));
		addObstacle(new Furniture(x + wallSprite.getHeight()*4.7, y + wallSprite.getHeight()/2 + tableSprite.getWidth(), new Sprite(tableSprite)));
	
		//north, west
		addObstacle(new Furniture(x + wallSprite.getHeight()*4, y + wallSprite.getHeight()/2 + tableSprite.getWidth()*2.5, new Sprite(tableSprite)));
		addObstacle(new Furniture(x + wallSprite.getHeight()*4, y + wallSprite.getHeight()/2 + tableSprite.getWidth()*3.5, new Sprite(tableSprite)));
		
		//middle, east
		addObstacle(new Furniture(x + wallSprite.getHeight()*4.7, y + wallSprite.getHeight()/2 + tableSprite.getWidth()*2.5, new Sprite(tableSprite)));
		addObstacle(new Furniture(x + wallSprite.getHeight()*4.7, y + wallSprite.getHeight()/2 + tableSprite.getWidth()*3.5, new Sprite(tableSprite)));
		
		//north, east
		addObstacle(new Furniture(x + wallSprite.getHeight()*4.7, y + wallSprite.getHeight()/2 + tableSprite.getWidth()*4.5, new Sprite(tableSprite)));
		addObstacle(new Furniture(x + wallSprite.getHeight()*4.7, y + wallSprite.getHeight()/2 + tableSprite.getWidth()*5.5, new Sprite(tableSprite)));
		
		//now some horisontal walls
		wallSprite.rotate(90);
		
		//south wall (the same as diner north wall)
		addObstacle(new Wall(x+wallSprite.getHeight()/2, y+wallSprite.getWidth()/2+wallSprite.getHeight()*2+200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*1.5+200, y+wallSprite.getWidth()/2+wallSprite.getHeight()*2+200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*2.5+200, y+wallSprite.getWidth()/2+wallSprite.getHeight()*2+200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*2.5+600, y+wallSprite.getWidth()/2+wallSprite.getHeight()*2+200, new Sprite(wallSprite)));
		
		//north walls, from left to right
		addObstacle(new Wall(x+wallSprite.getHeight()/2, y + wallSprite.getHeight()*3 + wallSprite.getWidth()*1.5 + 200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*1.5, y+ wallSprite.getHeight()*3 + wallSprite.getWidth()*1.5 + 200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*2.5, y+ wallSprite.getHeight()*3 + wallSprite.getWidth()*1.5 + 200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*3.5 - 100, y+ wallSprite.getHeight()*3 + wallSprite.getWidth()*1.5 + 200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*4.5 +200, y+ wallSprite.getHeight()*3 + wallSprite.getWidth()*1.5 + 200, new Sprite(wallSprite)));
		
		//south wall (the right one)
		addObstacle(new Wall(x+wallSprite.getHeight()*3.5 + 600, y+wallSprite.getWidth()/2, new Sprite(wallSprite)));
		
	//floor
		Sprite floor = new Sprite(textureHandler.getTextureByName("uglyfloor.png"));
		//most south floor
		addDecoration(new Wall(x+wallSprite.getHeight()*3.5 + 600 - wallSprite.getWidth(), y+wallSprite.getHeight()/2, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()*5 + 200 - floor.getWidth()/2, y+wallSprite.getHeight()/2, new Sprite(floor)));
		//a little more north
		addDecoration(new Wall(x+wallSprite.getHeight()*3.5 + 600 - wallSprite.getWidth(), y+wallSprite.getHeight()*1.5, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()*5 + 200 - floor.getWidth()/2, y+wallSprite.getHeight()/2 + floor.getHeight(), new Sprite(floor)));
		
		addDecoration(new Wall(x+wallSprite.getHeight()*3.5 + 600 - wallSprite.getWidth(), y+wallSprite.getHeight()*2.5, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()*5 + 200 - floor.getWidth()/2, y+wallSprite.getHeight()*2.5, new Sprite(floor)));
		
		addDecoration(new Wall(x+wallSprite.getHeight()*3.5 + 600 - wallSprite.getWidth(), y+ wallSprite.getHeight()*3 + wallSprite.getWidth()*2 + 200 - floor.getHeight()/2, new Sprite(floor)));
		addDecoration(new Wall(x+wallSprite.getHeight()*5 + 200 - floor.getWidth()/2, y+ wallSprite.getHeight()*3 + wallSprite.getWidth()*2 + 200 - floor.getHeight()/2, new Sprite(floor)));
		
		//west part floor
		addDecoration(new Wall(x + floor.getWidth()/2, y + wallSprite.getHeight()*2 + floor.getHeight()/2 + 200, new Sprite(floor)));
		addDecoration(new Wall(x + floor.getWidth()/2,  y + wallSprite.getHeight()*3 + wallSprite.getWidth()*2 - floor.getHeight()/2 + 200 , new Sprite(floor)));
		
		//middle part
		addDecoration(new Wall(x + floor.getWidth()*1.5, y + wallSprite.getHeight()*2 + floor.getHeight()/2 + 200, new Sprite(floor)));
		addDecoration(new Wall(x + floor.getWidth()*1.5,  y + wallSprite.getHeight()*3 + wallSprite.getWidth()*2 - floor.getHeight()/2 + 200 , new Sprite(floor)));
		
		addDecoration(new Wall(x + floor.getWidth()*2.5, y + wallSprite.getHeight()*2 + floor.getHeight()/2 + 200, new Sprite(floor)));
		addDecoration(new Wall(x + floor.getWidth()*2.5,  y + wallSprite.getHeight()*3 + wallSprite.getWidth()*2 - floor.getHeight()/2 + 200 , new Sprite(floor)));
		
		addDecoration(new Wall(x + floor.getWidth()*3.5, y + wallSprite.getHeight()*2 + floor.getHeight()/2 + 200, new Sprite(floor)));
		addDecoration(new Wall(x + floor.getWidth()*3.5,  y + wallSprite.getHeight()*3 + wallSprite.getWidth()*2 - floor.getHeight()/2 + 200 , new Sprite(floor)));
	}
	

	public void createEntrence(double x, double y){
		Sprite wallSprite = new Sprite(simpleWall);
		
		//east/west walls
		//west
		addObstacle(new Wall(x+wallSprite.getWidth()/2,y+3300-wallSprite.getHeight()/2, new Sprite(wallSprite)));
		
		addObstacle(new Wall(x+wallSprite.getHeight()+wallSprite.getWidth()*1.5, y+wallSprite.getHeight()*1.5+300+wallSprite.getWidth(), new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()+wallSprite.getWidth()*1.5, y+wallSprite.getHeight()/2+wallSprite.getWidth(), new Sprite(wallSprite)));
		
		//east
		addObstacle(new Wall(x+wallSprite.getHeight()*3+600-wallSprite.getWidth()/2,y + 3300-wallSprite.getHeight()/2, new Sprite(wallSprite)));
		
		addObstacle(new Wall(x+wallSprite.getHeight()*2+600-wallSprite.getWidth()*1.5, y+wallSprite.getHeight()*1.5+300+wallSprite.getWidth() ,new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*2+600-wallSprite.getWidth()*1.5, y+wallSprite.getHeight()/2+wallSprite.getWidth(), new Sprite(wallSprite)));
		
		wallSprite.rotate(90);
		//north/middle/south wall
		//north
		addObstacle(new Wall(x+wallSprite.getHeight()/2,y+3300+wallSprite.getWidth()/2, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*1.5+300, y+3300+wallSprite.getWidth()/2, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*2.5+600, y+3300+wallSprite.getWidth()/2, new Sprite(wallSprite)));
		
		//middle
		addObstacle(new Wall(x+wallSprite.getHeight()/2+wallSprite.getWidth(),y+3300-wallSprite.getHeight()+wallSprite.getWidth()/2, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*2.5+600-wallSprite.getWidth(), y+3300-wallSprite.getHeight()+wallSprite.getWidth()/2, new Sprite(wallSprite)));
		
		//south
		Sprite smallWallSprite = new Sprite(smallSimpleWall);
		smallWallSprite.rotate(90);
		addObstacle(new Wall(x+wallSprite.getHeight()+smallWallSprite.getWidth()*2, y+smallWallSprite.getWidth()/2, new Sprite(smallWallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()+smallWallSprite.getWidth()*4, y+smallWallSprite.getWidth()/2, new Sprite(smallWallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()+smallWallSprite.getWidth()*5.5, y+smallWallSprite.getWidth()/2, new Sprite(smallWallSprite)));
		
		addObstacle(new Wall(x+wallSprite.getHeight()+smallWallSprite.getWidth()*10.5, y+smallWallSprite.getWidth()/2, new Sprite(smallWallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()+smallWallSprite.getWidth()*12, y+smallWallSprite.getWidth()/2, new Sprite(smallWallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()+smallWallSprite.getWidth()*14, y+smallWallSprite.getWidth()/2, new Sprite(smallWallSprite)));
		
	//floor
		
		Sprite floor = new Sprite(textureHandler.getTextureByName("uglyfloor.png"));
		//top row of floor
		addDecoration(new Wall(x+floor.getWidth()/2, y+3300-floor.getHeight()/2, new Sprite(floor)));
		addDecoration(new Wall(x+floor.getWidth()*1.5, y+3300-floor.getHeight()/2, new Sprite(floor)));
		addDecoration(new Wall(x+floor.getWidth()*2.5, y+3300-floor.getHeight()/2, new Sprite(floor)));
		addDecoration(new Wall(x+floor.getWidth()*3, y+3300-floor.getHeight()/2, new Sprite(floor)));

		//left side of south part
		addDecoration(new Wall(x+floor.getWidth()*1.5+wallSprite.getWidth(), y+floor.getHeight()*2.5, new Sprite(floor)));
		addDecoration(new Wall(x+floor.getWidth()*1.5+wallSprite.getWidth(), y+floor.getHeight()*1.5, new Sprite(floor)));
		addDecoration(new Wall(x+floor.getWidth()*1.5+wallSprite.getWidth(), y+floor.getHeight()/2, new Sprite(floor)));

		//right side of south part
		addDecoration(new Wall(x+floor.getWidth()*1.9+wallSprite.getWidth(), y+floor.getHeight()/2, new Sprite(floor)));
		addDecoration(new Wall(x+floor.getWidth()*1.9+wallSprite.getWidth(), y+floor.getHeight()*1.5, new Sprite(floor)));
		addDecoration(new Wall(x+floor.getWidth()*1.9+wallSprite.getWidth(), y+floor.getHeight()*2.5, new Sprite(floor)));
		
		powerUpSpawnLocations.add(new Position(x+wallSprite.getWidth()*2, y+2500));
	}
	
	
	public void createSmokeSpot(double x, double y){
		Sprite wallSprite = new Sprite(simpleWall);
		//far east wall
		addObstacle(new Wall(x+wallSprite.getHeight()*5 + 200, y+wallSprite.getHeight()*4 - wallSprite.getWidth(), new Sprite(wallSprite)));
		//west wall
		addObstacle(new Wall(x+ wallSprite.getHeight()*3 + 200, y + wallSprite.getHeight()*4 - wallSprite.getWidth(), new Sprite(wallSprite)));
		
		//vertical walls below
		wallSprite.rotate(90);
		//north wall (from right to left this time)
		addObstacle(new Wall(x+wallSprite.getHeight()*4.5 +200, y + wallSprite.getHeight()*4 + wallSprite.getWidth()*1.5 + 200, new Sprite(wallSprite)));
		addObstacle(new Wall(x+wallSprite.getHeight()*3.5 +200, y + wallSprite.getHeight()*4 + wallSprite.getWidth()*1.5 + 200, new Sprite(wallSprite)));
		
		//south wall (border to kitchen) should there even be one?
		//addObstacle(new Wall(x+wallSprite.getHeight()*4.5 +200 + wallSprite.getWidth()/2, y+ wallSprite.getHeight()*3 + wallSprite.getWidth()*1.5 + 200, new Sprite(wallSprite)));
		//might need small wall here to complete south wall
		
		powerUpSpawnLocations.add(new Position(x + wallSprite.getHeight()*5, y + wallSprite.getHeight()*4));

	}
}
