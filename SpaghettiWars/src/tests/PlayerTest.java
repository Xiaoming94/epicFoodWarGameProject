//Author: Louise

package tests;

import static org.junit.Assert.*;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Player;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest{

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
		
		byte derp[] = {};
		Texture t = new Texture(new Pixmap(derp, 0, 0));
		Sprite sprite = new Sprite(t);
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
