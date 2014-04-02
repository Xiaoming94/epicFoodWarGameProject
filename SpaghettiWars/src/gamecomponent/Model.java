package gamecomponent;

import java.util.ArrayList;

import utilities.NameTexture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Entity;
import entities.Player;

public class Model {
	
	ArrayList<Entity> entities;
	Player player;
	
	ArrayList<NameTexture> textures;
	
	public Model (){
		entities = new ArrayList<Entity>();
		
		textures = new ArrayList<NameTexture>();
	}
	
	public ArrayList<Entity> getEntitys(){
		return entities;
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void createPlayer(){
		player = new Player("Sir Eatalot", 5, 5, new Sprite(getTextureByName("ful.png")), 10);
	}
	
	public void setTextureList(ArrayList<NameTexture> l){
		textures = l;
	}
	
	public NameTexture getTextureByName(String name){
		for(NameTexture e : textures)
			if(e.getName().equals(name))
				return e;
		return null;
	}

}
