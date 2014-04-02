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
	
	public Player getPlayer(){
		return player;
	}
	
	public void createPlayer(){
		player = new Player("Sir Eatalot", 5, 5, new Sprite(getTextureByName("bucket.png")), 1);
	}
	
	public void setTextureList(ArrayList<NameTexture> l){
		textures = l;
	}
	
	private NameTexture getTextureByName(String name){
		for(NameTexture e : textures)
			if(e.getName().equals(name))
				return e;
		return null;
	}

}
