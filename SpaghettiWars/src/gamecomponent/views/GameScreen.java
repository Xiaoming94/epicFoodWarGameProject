package gamecomponent.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Entity;
import entities.Obstacle;
import entities.PowerUp;
import gamecomponent.Model;

import org.lwjgl.opengl.GL11;

import utilities.GameInputHandler;

import java.util.Iterator;

/**
 * Created by xiaoming on 28/04/14.
 */
public class GameScreen implements IGameScreen{

    private Model model;
    SpriteBatch batch;
    private GameInputHandler gih;
    OrthographicCamera camera;

    public GameScreen(Model m){

        model = m;

        create();

    }

    private void create() {
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);

        gih = new GameInputHandler(model);

        batch = new SpriteBatch();
        model.getTextureHandler().loadTextures();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        camera.position.set((float)model.getPlayer().getX(), (float)model.getPlayer().getY(),0);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for(Obstacle o : model.getMap().getDecorations())
            batch.draw(o.getSprite(), o.getSprite().getX(), o.getSprite().getY(), o.getSprite().getOriginX(), o.getSprite().getOriginY(), o.getSprite().getWidth(), o.getSprite().getHeight(), 1, 1, o.getSprite().getRotation());

        for(Obstacle o: model.getMap().getObstacles())

            batch.draw(o.getSprite(), o.getSprite().getX(), o.getSprite().getY(), o.getSprite().getOriginX(), o.getSprite().getOriginY(), o.getSprite().getWidth(), o.getSprite().getHeight(), 1, 1, o.getSprite().getRotation());

        model.getPickUpsMutex().lock();
        for (PowerUp pu : model.getPickUps()){
            batch.draw(pu.getSprite(), pu.getSprite().getX(), pu.getSprite().getY(), pu.getSprite().getOriginX(), pu.getSprite().getOriginY(), pu.getSprite().getWidth(), pu.getSprite().getHeight(), 1, 1, pu.getSprite().getRotation());
        }
        model.getPickUpsMutex().unlock();
        model.getEntitiesMutex().lock();
        for(Entity e : model.getProjectiles())
            batch.draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY());
        model.getEntitiesMutex().unlock();

        model.getStillEntitiesMutex().lock();
        for(Entity e : model.getStillEntitys()){
            //changed this to make dead player still be fat
            batch.draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY(), e.getSprite().getOriginX(), e.getSprite().getOriginY(), e.getSprite().getWidth(), e.getSprite().getHeight(), 1, 1, e.getSprite().getRotation());
        }
        model.getStillEntitiesMutex().unlock();


        Iterator<Integer> iterator = model.getOtherPlayers().keySet().iterator();
        while(iterator.hasNext()){
            Integer key = iterator.next();
            batch.draw(model.getOtherPlayers().get(key).getSprite(), model.getOtherPlayers().get(key).getSprite().getX(), model.getOtherPlayers().get(key).getSprite().getY(), model.getOtherPlayers().get(key).getSprite().getOriginX(), model.getOtherPlayers().get(key).getSprite().getOriginY(), model.getOtherPlayers().get(key).getSprite().getWidth(), model.getOtherPlayers().get(key).getSprite().getHeight(), 1, 1, model.getOtherPlayers().get(key).getSprite().getRotation());
        }


        batch.draw(model.getPlayer().getSprite(), model.getPlayer().getSprite().getX(), model.getPlayer().getSprite().getY(), model.getPlayer().getSprite().getOriginX(), model.getPlayer().getSprite().getOriginY(), model.getPlayer().getSprite().getWidth(), model.getPlayer().getSprite().getHeight(), 1, 1, model.getPlayer().getSprite().getRotation());
        batch.draw(model.getActionBar(), camera.position.x-180, camera.position.y-camera.viewportHeight/2);
        batch.draw(model.getActionBarSelection(), camera.position.x-180+40*model.getSelectedWeapon(), camera.position.y-camera.viewportHeight/2);
        batch.draw(model.getPowerUpBar(), camera.position.x-camera.viewportWidth/2, camera.position.y-camera.viewportHeight/2);
        
        if(model.getPlayer().getPowerUp() != null)
        	batch.draw(model.getPlayer().getPowerUp().getSprite(), camera.position.x-camera.viewportWidth/2 + 50, camera.position.y-camera.viewportHeight/2 + 20);

        batch.end();

    }

    @Override
    public void setToCorrectInputProcessor() {

        Gdx.input.setInputProcessor(gih);

    }

    @Override
    public void kill() {

        //Do something important

    }

    @Override
    public void resize(int width, int height) {

        model.setViewSize(width, height);

    }
}
