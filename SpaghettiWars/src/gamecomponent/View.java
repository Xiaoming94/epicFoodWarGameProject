/*
 *		Author: Jimmy Eliason Malmer
 */
package gamecomponent;

import java.awt.List;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import utilities.NameTexture;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import entities.Entity;
import entities.Player;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
	    
	    camera.position.set(model.getPlayer().getSprite().getX(), model.getPlayer().getSprite().getY(),0);
	    
	    camera.update();
	    
	    batch.setProjectionMatrix(camera.combined);
	    
	    //batch.enableBlending();
	    batch.begin();
	    batch.draw(model.getPlayer().getSprite().getTexture(), model.getPlayer().getSprite().getX(), model.getPlayer().getSprite().getY());
	    for(Entity e : model.getEntitys())
	    	batch.draw(e.getSprite().getTexture(), e.getSprite().getX(), e.getSprite().getY());
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
		
		l.add(new NameTexture("assets/ful.png"));
		l.add(new NameTexture("assets/dummylogo.png"));
		
		return l;
	}
}
