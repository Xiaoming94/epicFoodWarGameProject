package gamecomponent;

import java.util.ArrayList;

import sun.awt.Mutex;
import utilities.TextureHandler;

import com.badlogic.gdx.graphics.g2d.Sprite;

//import entities.Furniture;
import entities.Obstacle;
import entities.Wall;

public class GameMap {
	
	private TextureHandler textureHandler;

	private ArrayList <Obstacle> obstacles = new ArrayList <Obstacle>();
	private ArrayList <Obstacle> decorations = new ArrayList <Obstacle>();
	
	private Mutex obstacleMutex;
	private Mutex decorationMutex;
	
	private Sprite simpleWall;
	
	public GameMap(TextureHandler textureHandler){
		this.textureHandler = textureHandler;
		simpleWall = new Sprite(textureHandler.getTextureByName("wall.png"));
		createDiner(100,0);
		createKitchen(100, 0);
		
		
		
		obstacleMutex = new Mutex();
		decorationMutex = new Mutex();

//		Sprite greenThing = new Sprite(textureHandler.getTextureByName("greenfurniture.png"));
//		greenThing.setSize(200,100);
//		addObstacle(new Furniture(0, -700, greenThing));
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
		
		
	}
	
	public void createKitchen(double x, double y){
		Sprite wallSprite = new Sprite(simpleWall);
		
		
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
		addObstacle(new Wall(x+wallSprite.getHeight()*3.5, y+ wallSprite.getHeight()*3 + wallSprite.getWidth()*1.5 + 200, new Sprite(wallSprite)));
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
}
