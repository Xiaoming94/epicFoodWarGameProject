package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import networking.SpaghettiClient;
import networking.SpaghettiFace;
import networking.SpaghettiServer;
import sun.awt.Mutex;
import utilities.TextureHandler;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Energydrink;
import entities.Entity;
import entities.Player;
import entities.PowerUp;
import entities.Projectile;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.TextureHandler;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;  

import entities.Player;
import gamecomponent.Main;
import gamecomponent.Model;
import gamecomponent.views.MainView;

public class PlayerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlayerStringDoubleDoubleSpriteDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlayerStringDoubleDoubleSpriteDoubleTextureHandler() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlayerStringDoubleDoubleSpriteDoubleIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFatPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testGainWeight() {
		fail("Not yet implemented");
	}

	@Test
	public void testLooseWeight() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetWeight() {
		//this doesn't work because i can't load textures
		TextureHandler txt = new TextureHandler();
		txt.loadTextures();
		Sprite sprite = new Sprite(txt.getTextureByName("ful.png"));
		Player player = new Player("Fat Bastard", 5, 5, sprite,3);
		player.setWeight(10);
		assertTrue(player.getFatPoint() == 10);
		fail("Not yet implemented");
	}

	@Test
	public void testRemovePowerUpEffect() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsDead() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifySpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetScale() {
		fail("Not yet implemented");
	}

	@Test
	public void testShoot() {
		fail("Not yet implemented");
	}

	@Test
	public void testOverlapsRectangle() {
		fail("Not yet implemented");
	}

	@Test
	public void testOverlapsProjectile() {
		fail("Not yet implemented");
	}

	@Test
	public void testObstructedMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeWeapon() {
		fail("Not yet implemented");
	}

	@Test
	public void testUsePowerUp() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPowerUp() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPowerUp() {
		fail("Not yet implemented");
	}

}