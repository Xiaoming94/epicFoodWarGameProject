package gamecomponent;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Player;

public class Controller implements Runnable, InputProcessor{
	
	Model model;

	
	public Controller(Model m){
		model = m;
		 Gdx.input.setInputProcessor(this);
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

	@Override
	public boolean keyDown(int keycode) {
		System.out.println("hejsan");
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
