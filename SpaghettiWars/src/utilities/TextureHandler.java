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

}
