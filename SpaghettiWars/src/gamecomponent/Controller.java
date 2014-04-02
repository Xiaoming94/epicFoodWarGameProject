package gamecomponent;

import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Player;

public class Controller implements Runnable{
	
	Model model;
	
	public Controller(Model m){
		model = m;
	}

	@Override
	public void run() {
		//wait for View to load textures before controller try to create player
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			System.out.println("got interrupted!");
		}
		model.createPlayer();
		
		model.getPlayer().setVector(0.5, 1);
		while(true){
			model.getPlayer().move();
			
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){
				System.out.println("got interrupted!");
			}
		}
		
	}
}
