package gamecomponent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Player;
import entities.Projectile;

public class Controller implements Runnable, InputProcessor {

	Model model;
	private boolean up, down, left, right;
	
	private Direction currentDirection;

	public enum Direction{
		NORTH,SOUTH,WEST,EAST,NORTHWEST,NORTHEAST,SOUTHWEST,SOUTHEAST,STAY
	}
	public Controller(Model m) {
		model = m;
		Gdx.input.setInputProcessor(this);
	}
	

	@Override
	public void run() {
		// wait for View to load textures before controller try to create player
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("got interrupted!");
		}
		model.createPlayer();
		
		model.addEntity(new Player("Sir derp", 50, 100, new Sprite(model.getTextureByName("dummylogo.png")), 0));

		model.getPlayer().setVector(0.5, 1);
		while (true) {
			model.getPlayer().move();

			try {
				Thread.sleep(50);
				//TODO synched sleep
			}catch(InterruptedException e){
				System.out.println("got interrupted!");
			}
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		System.out.println(keycode);

		switch (keycode) {
		case (Keys.W):
			up = true;
			break;
		case (Keys.S):
			down = true;
			break;
		case (Keys.A):
			left = true;
			break;
		case (Keys.D):
			right = true;
			break;
		}
		updateDirection();
		model.getPlayer().setPlayerDirection(currentDirection);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		switch (keycode) {
		case (Keys.W):
			up = false;
			break;
		case (Keys.S):
			down = false;
			break;
		case (Keys.A):
			left = false;
			break;
		case (Keys.D):
			right = false;
			break;
		}
		updateDirection();
		model.getPlayer().setPlayerDirection(currentDirection);
		
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
	public void updateDirection(){
		currentDirection = Direction.STAY;

		if(up && !down){
			if(left && !right){
				currentDirection = Direction.NORTHWEST;
			}else if(right && !left){
				currentDirection = Direction.NORTHEAST;
			}else{
				currentDirection = Direction.NORTH;
			}
		}
		else if(down && !up){
			if(left && !right){
				currentDirection = Direction.SOUTHWEST;
			}else if(right && !left){
				currentDirection = Direction.SOUTHEAST;
			}else{
				currentDirection = Direction.SOUTH;
			}
		}else if(right && !left){
			currentDirection = Direction.EAST;
		}else if(left && !right){
			currentDirection = Direction.WEST;
		}
		
		//return currentDirection;
		
	}
}
