package utilities;

import com.badlogic.gdx.InputProcessor;

import gamecomponent.Model;
import gamecomponent.View;

public class GameInputHandler implements InputProcessor {
	
	private final Model gameModel;
	
	public GameInputHandler(Model gameModel){
		this.gameModel = gameModel;
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		gameModel.checkPressedKey(keyCode);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keyCode) {
		gameModel.checkReleasedKey(keyCode);
		return true;
	}

	@Override
	public boolean mouseMoved(int mouse1, int mouse2) {
		gameModel.mouseMoved(mouse1, mouse2);
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		gameModel.mouseButtonPressed(screenX, screenY, button);
		return true;
	}
	
	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		mouseMoved(arg0, arg1);
		return false;
	}
	
	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
