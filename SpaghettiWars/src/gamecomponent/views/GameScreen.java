package gamecomponent.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Entity;
import entities.Obstacle;
import entities.PowerUp;

import org.lwjgl.opengl.GL11;

import utilities.GameInputHandler;

import java.util.Iterator;

/**
 * Created by xiaoming on 28/04/14.
 */
public class GameScreen implements IGameScreen{

	private MainView parent;
    private SpriteBatch batch;
    private GameInputHandler gih;
    OrthographicCamera camera;

    public GameScreen(MainView mv){

    	parent = mv;
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

        gih = new GameInputHandler(parent.getModel());

        batch = new SpriteBatch();
        parent.getModel().getTextureHandler().loadTextures();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        camera.position.set((float)parent.getModel().getPlayer().getX(), (float)parent.getModel().getPlayer().getY(),0);

        camera.update();

        getBatch().setProjectionMatrix(camera.combined);

        getBatch().begin();
        
        parent.getModel().getMap().getDecorationMutex().lock();
        for(Obstacle o : parent.getModel().getMap().getDecorations())
            getBatch().draw(o.getSprite(), o.getSprite().getX(), o.getSprite().getY(), o.getSprite().getOriginX(), o.getSprite().getOriginY(), o.getSprite().getWidth(), o.getSprite().getHeight(), 1, 1, o.getSprite().getRotation());
        parent.getModel().getMap().getDecorationMutex().unlock();
        
        parent.getModel().getMap().getObstacleMutex().lock();
        for(Obstacle o: parent.getModel().getMap().getObstacles())
            getBatch().draw(o.getSprite(), o.getSprite().getX(), o.getSprite().getY(), o.getSprite().getOriginX(), o.getSprite().getOriginY(), o.getSprite().getWidth(), o.getSprite().getHeight(), 1, 1, o.getSprite().getRotation());
        parent.getModel().getMap().getObstacleMutex().unlock();
        

        parent.getModel().getPickUpsMutex().lock();
        for (PowerUp pu : parent.getModel().getPickUps()){
            getBatch().draw(pu.getSprite(), pu.getSprite().getX(), pu.getSprite().getY(), pu.getSprite().getOriginX(), pu.getSprite().getOriginY(), pu.getSprite().getWidth(), pu.getSprite().getHeight(), 1, 1, pu.getSprite().getRotation());
        }
        parent.getModel().getPickUpsMutex().unlock();
        
        parent.getModel().getProjectilesMutex().lock();
        for(Entity e : parent.getModel().getProjectiles())
            getBatch().draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY());
        parent.getModel().getProjectilesMutex().unlock();

        parent.getModel().getStillEntitiesMutex().lock();
        for(Entity e : parent.getModel().getStillEntitys()){
            getBatch().draw(e.getSprite(), e.getSprite().getX(), e.getSprite().getY(), e.getSprite().getOriginX(), e.getSprite().getOriginY(), e.getSprite().getWidth(), e.getSprite().getHeight(), 1, 1, e.getSprite().getRotation());
        }
        parent.getModel().getStillEntitiesMutex().unlock();


        parent.getModel().getOtherPlayersMutex().lock();
        Iterator<Integer> iterator = parent.getModel().getOtherPlayers().keySet().iterator();
        while(iterator.hasNext()){
            Integer key = iterator.next();
            getBatch().draw(parent.getModel().getOtherPlayers().get(key).getSprite(), parent.getModel().getOtherPlayers().get(key).getSprite().getX(), parent.getModel().getOtherPlayers().get(key).getSprite().getY(), parent.getModel().getOtherPlayers().get(key).getSprite().getOriginX(), parent.getModel().getOtherPlayers().get(key).getSprite().getOriginY(), parent.getModel().getOtherPlayers().get(key).getSprite().getWidth(), parent.getModel().getOtherPlayers().get(key).getSprite().getHeight(), 1, 1, parent.getModel().getOtherPlayers().get(key).getSprite().getRotation());
        }
        parent.getModel().getOtherPlayersMutex().unlock();


        getBatch().draw(parent.getModel().getPlayer().getSprite(), parent.getModel().getPlayer().getSprite().getX(), parent.getModel().getPlayer().getSprite().getY(), parent.getModel().getPlayer().getSprite().getOriginX(), parent.getModel().getPlayer().getSprite().getOriginY(), parent.getModel().getPlayer().getSprite().getWidth(), parent.getModel().getPlayer().getSprite().getHeight(), 1, 1, parent.getModel().getPlayer().getSprite().getRotation());
        getBatch().draw(parent.getModel().getActionBar(), camera.position.x-180, camera.position.y-camera.viewportHeight/2);
        getBatch().draw(parent.getModel().getActionBarSelection(), camera.position.x-182+40*parent.getModel().getSelectedWeapon(), camera.position.y-camera.viewportHeight/2);
        getBatch().draw(parent.getModel().getPowerUpBar(), camera.position.x-camera.viewportWidth/2, camera.position.y-camera.viewportHeight/2);
        
        if(parent.getModel().getPlayer().getPowerUp() != null)
        	getBatch().draw(parent.getModel().getPlayer().getPowerUp().getSprite(), camera.position.x-camera.viewportWidth/2 + 65, camera.position.y-camera.viewportHeight/2 + 60, parent.getModel().getPlayer().getPowerUp().getSprite().getOriginX(), parent.getModel().getPlayer().getPowerUp().getSprite().getOriginY(), parent.getModel().getPlayer().getPowerUp().getSprite().getWidth(), parent.getModel().getPlayer().getPowerUp().getSprite().getHeight(), 2, 2, parent.getModel().getPlayer().getPowerUp().getSprite().getRotation());

        getBatch().end();

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

        parent.getModel().setViewSize(width, height);

    }
}
