package gamecomponent;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Entity;
import entities.Player;

public class Model {
	
	ArrayList<Entity> entities;
	
	Player player;
	
	public Model (){
		entities = new ArrayList<Entity>();
		
		player = new Player("Sir Eatalot", 5, 5, new Sprite(new Texture("bucket.png")), 1));
	}
	
	public ArrayList<Entity> getEntitys(){
		return entities;
	}
	
	public Player getPlayer(){
		return player;
	}

}
