/*
 *		Author: Jimmy Eliason Malmer
 */
package gamecomponent;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import utilities.NameTexture;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import entities.Entity;

public class View implements ApplicationListener{
    
	Model model;
	LwjglApplication app;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	Rectangle bucket;
	Texture bucketImage;
	
	public View(Model m){
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	    cfg.title = "Spaghetti Wars";
	    cfg.width = 800;
	    cfg.height = 480;
		app = new LwjglApplication(this, cfg);
		model = m;
		model.setViewSize(cfg.width, cfg.height);
	}

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
		model.setTextureList(loadTextures());
		
		//sleep to wait for player to be created by controller
		try{
		    Thread.sleep(1000);
		}catch(InterruptedException e){
		    System.out.println("got interrupted!");
		}
	}

	@Override
	public void resize(int width, int height) {
		model.setViewSize(width, height);
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
	    
	    camera.position.set((float)model.getPlayer().getX(), (float)model.getPlayer().getY(),0);
	    
	    camera.update();
	    
	    batch.setProjectionMatrix(camera.combined);
	    
	    //batch.enableBlending();
	    batch.begin();
	    for(Entity e : model.getEntitys())
	    	batch.draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY());
	    //batch.draw(model.getPlayer().getSprite(), model.getPlayer().getSprite().getX(), model.getPlayer().getSprite().getY());
	    batch.draw(model.getPlayer().getSprite(), model.getPlayer().getSprite().getX(), model.getPlayer().getSprite().getY(), model.getPlayer().getSprite().getOriginX(), model.getPlayer().getSprite().getOriginY(), model.getPlayer().getSprite().getWidth(), model.getPlayer().getSprite().getHeight(), 1, 1, model.getPlayer().getSprite().getRotation());
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
	
	private ArrayList<NameTexture> loadTextures(){
		ArrayList<NameTexture> l = new ArrayList<NameTexture>();
		
		l.add(new NameTexture("ful.png"));
		l.add(new NameTexture("dummylogo.png"));
		l.add(new NameTexture("Kottbulle.png"));
		
		return l;
	}
}
