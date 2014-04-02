package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Wall extends Obstacle {
	
	//kommer behova double startX, double startY, double endX, double endY?
	//eller nagon slags riktning?
	public Wall(double x, double y){
		//ja, jag vet att bucket inte ar en vagg...
		super(x, y, new Sprite(new Texture("assets/bucket.png")), true);
	}
	
}
