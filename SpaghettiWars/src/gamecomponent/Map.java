package gamecomponent;

import java.util.ArrayList;

import utilities.TextureHandler;

import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Obstacle;
import entities.Wall;

public class Map {
	
	private TextureHandler textureHandler;

	ArrayList <Obstacle> obstacles = new ArrayList <Obstacle>();
	
	public Map(TextureHandler textureHandler){
		this.textureHandler = textureHandler;
		addObstacle(new Wall(50, 300, new Sprite(textureHandler.getTextureByName("ful.png"))));
	}
	
	public void addObstacle(Obstacle o){
		obstacles.add(o);
	}
	
	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}
}
