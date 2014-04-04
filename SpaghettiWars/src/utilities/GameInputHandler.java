package utilities;

import com.badlogic.gdx.InputProcessor;
import gamecomponent.Model;

public class GameInputHandler implements InputProcessor {
	
	private final Model gameModel;
	
	public GameInputHandler (Model gameModel){
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
		return false;
	}

	@Override
	public boolean mouseMoved(int mouse1, int mouse2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
