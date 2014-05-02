/*
h *		Author: Jimmy Eliason Malmer
 */
package gamecomponent.views;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.Entity;
import entities.Obstacle;
import gamecomponent.Model;
import org.lwjgl.opengl.GL11;
import utilities.GameInputHandler;

import java.util.Iterator;

//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.math.Rectangle;

public class MainView implements ApplicationListener{
	Model model;
	LwjglApplication app;

    private IGameScreen screen;
	public MainView(Model m){
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	    cfg.title = "Spaghetti Wars";
	    cfg.width = 800;
	    cfg.height = 480;
		app = new LwjglApplication(this, cfg);
        //viewstate = ViewStates.INGAME;
		model = m;
		model.setStartViewSize(cfg.width, cfg.height);
		model.setViewSize(cfg.width, cfg.height);
	}

	@Override
	public void create() {
        createMenuScreen();
		
//		model.setTextureList(loadTextures());
		model.getTextureHandler().loadTextures();

		//sleep to wait for player to be created by controller
		try{
		    Thread.sleep(1000);
		}catch(InterruptedException e){
		    System.out.println("got interrupted!");
		}
		
		//System.out.println("ENTERS CREATE IN VIEW");
	}

    private void createMenuScreen() {
        this.screen = new MenuScreen(model, this);
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
		// TODO Auto-generated method stub	
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		model.getNetworkObject().disconnect();
	}

    public void setScreen(IGameScreen screen){
        if (this.screen != null) {
            this.killCurrentScreen();
        }
        this.screen = screen;
        this.screen.setToCorrectInputProcessor();
    }

    private void killCurrentScreen() {
        this.screen.kill();
    }

    public void startGame() {
        this.setScreen(new GameScreen(model));
    }
}
