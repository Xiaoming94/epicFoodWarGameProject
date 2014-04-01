package gamecomponent;

import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Player;

public class Controller implements Runnable{
	
	Model model;
	
	public Controller(Model m){
		model = m;
		model.createPlayer();
	}

	@Override
	public void run() {
		while(true){
			;
		}
		
	}
}
