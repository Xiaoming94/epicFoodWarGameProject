package gamecomponent;

import java.util.ArrayList;

import utilities.TextureHandler;

import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Obstacle;
import entities.Wall;

public class Map {
	
	private TextureHandler textureHandler;

	ArrayList <Obstacle> obstacles = new ArrayList <Obstacle>();
	ArrayList <Obstacle> decorations = new ArrayList <Obstacle>();
	
	private Sprite simpleWall;
	
	public Map(TextureHandler textureHandler){
		this.textureHandler = textureHandler;
		simpleWall = new Sprite(textureHandler.getTextureByName("wall.png"));
		createNewRoom(0,0);
		createNewRoom(simpleWall.getHeight() - simpleWall.getWidth(), simpleWall.getHeight() - simpleWall.getWidth());

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
}
