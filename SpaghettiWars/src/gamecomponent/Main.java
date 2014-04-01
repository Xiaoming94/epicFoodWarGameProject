package gamecomponent;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class Main{

	public static void main(String[] args){
		ClientModel model = new ClientModel();
		new View(model);
		Thread ct = new Thread(new Controller(model));
		ct.start();
		
		System.out.print("motherfucker");
		
	}
	
}
