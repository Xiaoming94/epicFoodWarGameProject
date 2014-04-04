package utilities;

import com.badlogic.gdx.graphics.Texture;

public class NameTexture extends Texture {

	String name;
	
	public NameTexture(String internalPath) {
		super("assets/" + internalPath);
		
		name = internalPath;
	}

	public String getName(){
		return name;
	}
}
