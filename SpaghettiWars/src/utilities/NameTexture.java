package utilities;

import com.badlogic.gdx.graphics.Texture;

public class NameTexture extends Texture {

	String name;
	
	public NameTexture(String internalPath) {
		super(internalPath);
		
		name = internalPath.substring(7);
		System.out.print(name);
	}

	public String getName(){
		return name;
	}
}
