package utilities;

import java.util.ArrayList;

public class TextureHandler {
	
	private ArrayList<NameTexture> textures;
	
	public TextureHandler(){
		textures = new ArrayList<NameTexture>();
		
		
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
	
	public void loadTextures(){
		textures.add(new NameTexture("ful.png"));
		textures.add(new NameTexture("dummylogo.png"));
		textures.add(new NameTexture("Kottbulle.png"));
		textures.add(new NameTexture("wall.png"));
		textures.add(new NameTexture("uglyfloor.png"));
		textures.add(new NameTexture("actionbar2.png"));
		textures.add(new NameTexture("actionbarselection.png"));
		textures.add(new NameTexture("pizza.png"));
		textures.add(new NameTexture("greenfurniture.png"));
		textures.add(new NameTexture("extremelyuglydrink.png"));
		textures.add(new NameTexture("powerupholder.png"));
		textures.add(new NameTexture("smallWall.png"));
		textures.add(new NameTexture("dietpill.png"));
        textures.add(new NameTexture("FatDeadPlayerSprite.png"));
		
	}
	
	

}
