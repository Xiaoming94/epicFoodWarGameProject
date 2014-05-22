/*
 *		Author: Jimmy Eliason Malmer
 *		Modified heavily by Henry
 */

//A class that renders the current screen.

package gamecomponent.views;

import java.util.Observable;
import java.util.Observer;

import utilities.TextureHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import gamecomponent.Model;

public class MainView implements ApplicationListener, Observer{
	Model model;
	LwjglApplication app;
	
	private GameScreen gameScreen;
	private PauseScreen pauseScreen;
	private MenuScreen menuScreen;
	
    private IGameScreen screen;
    
	public MainView(Model m){
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	    cfg.title = "Spaghetti Wars";
	    cfg.width = 800;
	    cfg.height = 480;
		app = new LwjglApplication(this, cfg);
		model = m;
		model.setStartViewSize(cfg.width, cfg.height);
		model.setViewSize(cfg.width, cfg.height);
		model.addObserver(this);
	}

	@Override
	public void create() {
        createMenuScreen();
		
		TextureHandler.getInstance().loadTextures();

		//sleep to wait for player to be created by controller
		try{
		    Thread.sleep(1000);
		}catch(InterruptedException e){
		    System.out.println("got interrupted!");
		}
	}

    private void createMenuScreen() {
    	if(this.menuScreen == null)
    		menuScreen = new MenuScreen(model,this);
    	
        this.screen = menuScreen;
        screen.setToCorrectInputProcessor();
    }

    @Override
	public void resize(int width, int height) {
		screen.resize(width, height);
		
	}

	@Override
	public void render() {

		screen.render();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		model.notifyObservers("dissconnect");

	}

    public IGameScreen getScreen() {
		return screen;
	}

	public void setScreen(IGameScreen screen){
        this.screen = screen;
        this.screen.setToCorrectInputProcessor();
    }

    public void startGame() {
    	if(pauseScreen == null || gameScreen == null){
	    	pauseScreen = new PauseScreen(this);
	    	this.setScreen(gameScreen = new GameScreen(this));
    	}
    	else{
    		this.setScreen(gameScreen);
    	}
    	
    	model.reset();
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Integer)
			if((Integer)arg1 == Keys.ESCAPE){
				this.setScreen(pauseScreen);
			}
	}
	
	public void disconnect(){
		this.createMenuScreen();
	}
	
	protected Model getModel(){
		return model;
	}
}
