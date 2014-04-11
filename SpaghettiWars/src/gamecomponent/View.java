/*
 *		Author: Jimmy Eliason Malmer
 */
package gamecomponent;

import org.lwjgl.opengl.GL11;

import utilities.GameInputHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Rectangle;


import entities.Entity;
import entities.Obstacle;

public class View implements ApplicationListener{
	Model model;
	LwjglApplication app;
	GameInputHandler gih;
	
	OrthographicCamera camera;
	SpriteBatch batch;

    enum ViewStates{
        MENUS,INGAME
    }

    private MenuScreen menuScreen;

    private ViewStates viewstate;
	public View(Model m){
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	    cfg.title = "Spaghetti Wars";
	    cfg.width = 800;
	    cfg.height = 480;
		app = new LwjglApplication(this, cfg);
        viewstate = ViewStates.INGAME;
		model = m;
		model.setViewSize(cfg.width, cfg.height);
	}
	
	public void reciveInputHandler(GameInputHandler gih){
		this.gih = gih;
	}

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 960);
		
		batch = new SpriteBatch();
		
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



	@Override
	public void resize(int width, int height) {
		model.setViewSize(width, height);
		
	}

	@Override
	public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
        if(viewstate == ViewStates.MENUS){
            renderMenu();
        }
		if (viewstate == ViewStates.INGAME){
            renderGame();
        }
		
	}

    private void renderMenu() {
        if (menuScreen == null) {
            menuScreen = new MenuScreen(this);
        }
        if (menuScreen != null){
            menuScreen.render(1);
        }

    }

    public void startGame(){
    	Gdx.input.setInputProcessor(gih);
        viewstate = ViewStates.INGAME;
    }

    public void renderGame(){
    	
    

        camera.position.set((float)model.getPlayer().getX(), (float)model.getPlayer().getY(),0);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for(Obstacle o : model.getMap().getDecorations())
        	batch.draw(o.getSprite(), o.getSprite().getX(), o.getSprite().getY(), o.getSprite().getOriginX(), o.getSprite().getOriginY(), o.getSprite().getWidth(), o.getSprite().getHeight(), 1, 1, o.getSprite().getRotation());
        
        for(Obstacle o: model.getMap().getObstacles())

            batch.draw(o.getSprite(), o.getSprite().getX(), o.getSprite().getY(), o.getSprite().getOriginX(), o.getSprite().getOriginY(), o.getSprite().getWidth(), o.getSprite().getHeight(), 1, 1, o.getSprite().getRotation());

        model.getEntitiesMutex().lock();
        for(Entity e : model.getProjectiles())
            batch.draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY());
        model.getEntitiesMutex().unlock();

        model.getStillEntitiesMutex().lock();
        for(Entity e : model.getStillEntitys())
            batch.draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY());
        model.getStillEntitiesMutex().unlock();
        
        for(Entity e : model.getOtherPlayers())
            batch.draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY(), e.getSprite().getOriginX(), e.getSprite().getOriginY(), e.getSprite().getWidth(), e.getSprite().getHeight(), 1, 1, e.getSprite().getRotation());

        batch.draw(model.getPlayer().getSprite(), model.getPlayer().getSprite().getX(), model.getPlayer().getSprite().getY(), model.getPlayer().getSprite().getOriginX(), model.getPlayer().getSprite().getOriginY(), model.getPlayer().getSprite().getWidth(), model.getPlayer().getSprite().getHeight(), 1, 1, model.getPlayer().getSprite().getRotation());
        batch.draw(model.getActionBar(), camera.position.x-180, camera.position.y-camera.viewportHeight/2);
        batch.draw(model.getActionBarSelection(), camera.position.x-180+40*model.getSelectedWeapon(), camera.position.y-camera.viewportHeight/2);
        
        batch.end();
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
		
	}
}
