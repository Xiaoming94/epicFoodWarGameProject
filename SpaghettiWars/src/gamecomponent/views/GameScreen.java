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
        
        model.getMap().getDecorationMutex().lock();
        for(Obstacle o : model.getMap().getDecorations())
            batch.draw(o.getSprite(), o.getSprite().getX(), o.getSprite().getY(), o.getSprite().getOriginX(), o.getSprite().getOriginY(), o.getSprite().getWidth(), o.getSprite().getHeight(), 1, 1, o.getSprite().getRotation());
        model.getMap().getDecorationMutex().unlock();
        
        model.getMap().getObstacleMutex().lock();
        for(Obstacle o: model.getMap().getObstacles())
            batch.draw(o.getSprite(), o.getSprite().getX(), o.getSprite().getY(), o.getSprite().getOriginX(), o.getSprite().getOriginY(), o.getSprite().getWidth(), o.getSprite().getHeight(), 1, 1, o.getSprite().getRotation());
        model.getMap().getObstacleMutex().unlock();
        

        model.getPickUpsMutex().lock();
        for (PowerUp pu : model.getPickUps()){
            batch.draw(pu.getSprite(), pu.getSprite().getX(), pu.getSprite().getY(), pu.getSprite().getOriginX(), pu.getSprite().getOriginY(), pu.getSprite().getWidth(), pu.getSprite().getHeight(), 1, 1, pu.getSprite().getRotation());
        }
        model.getPickUpsMutex().unlock();
        
        model.getProjectilesMutex().lock();
        for(Entity e : model.getProjectiles())
            batch.draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY());
        model.getProjectilesMutex().unlock();

        model.getStillEntitiesMutex().lock();
        for(Entity e : model.getStillEntitys()){
            //changed this to make dead player still be fat
            batch.draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY(), e.getSprite().getOriginX(), e.getSprite().getOriginY(), e.getSprite().getWidth(), e.getSprite().getHeight(), 1, 1, e.getSprite().getRotation());
        }
        model.getStillEntitiesMutex().unlock();


        model.getOtherPlayersMutex().lock();
        Iterator<Integer> iterator = model.getOtherPlayers().keySet().iterator();
        while(iterator.hasNext()){
            Integer key = iterator.next();
            batch.draw(model.getOtherPlayers().get(key).getSprite(), model.getOtherPlayers().get(key).getSprite().getX(), model.getOtherPlayers().get(key).getSprite().getY(), model.getOtherPlayers().get(key).getSprite().getOriginX(), model.getOtherPlayers().get(key).getSprite().getOriginY(), model.getOtherPlayers().get(key).getSprite().getWidth(), model.getOtherPlayers().get(key).getSprite().getHeight(), 1, 1, model.getOtherPlayers().get(key).getSprite().getRotation());
        }
        model.getOtherPlayersMutex().unlock();


        batch.draw(model.getPlayer().getSprite(), model.getPlayer().getSprite().getX(), model.getPlayer().getSprite().getY(), model.getPlayer().getSprite().getOriginX(), model.getPlayer().getSprite().getOriginY(), model.getPlayer().getSprite().getWidth(), model.getPlayer().getSprite().getHeight(), 1, 1, model.getPlayer().getSprite().getRotation());
        batch.draw(model.getActionBar(), camera.position.x-180, camera.position.y-camera.viewportHeight/2);
        batch.draw(model.getActionBarSelection(), camera.position.x-182+40*model.getSelectedWeapon(), camera.position.y-camera.viewportHeight/2);
        batch.draw(model.getPowerUpBar(), camera.position.x-camera.viewportWidth/2, camera.position.y-camera.viewportHeight/2);
        
        if(model.getPlayer().getPowerUp() != null)
        	batch.draw(model.getPlayer().getPowerUp().getSprite(), camera.position.x-camera.viewportWidth/2 + 65, camera.position.y-camera.viewportHeight/2 + 60, model.getPlayer().getPowerUp().getSprite().getOriginX(), model.getPlayer().getPowerUp().getSprite().getOriginY(), model.getPlayer().getPowerUp().getSprite().getWidth(), model.getPlayer().getPowerUp().getSprite().getHeight(), 2, 2, model.getPlayer().getPowerUp().getSprite().getRotation());

        batch.end();

    }

    public SpriteBatch getBatch() {
		return batch;
	}

	@Override
    public void setToCorrectInputProcessor() {

        Gdx.input.setInputProcessor(gih);

    }

    public OrthographicCamera getCamera() {
		return camera;
	}

	@Override
    public void kill() {

        //Do something important

    }

    @Override
    public void resize(int width, int height) {

        model.setViewSize(width, height);

    }

    /**
     * Temporary method used for the Pause Screen.
     * @return
     */
    public Model getModel(){
        return this.model;
    }
}
