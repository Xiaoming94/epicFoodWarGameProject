package tests;

import static org.junit.Assert.*;
import gamecomponent.Model;
import gamecomponent.views.GameScreen;
import gamecomponent.views.MainView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameScreenTest {

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
	public void testGameScreen() {
		Model model = new Model();
		MainView mainView = new MainView(model);
		GameScreen gameScreen = new GameScreen(mainView);
		assertTrue(gameScreen.getClass() == GameScreen.class);
		//fail("Not yet implemented");
	}

	@Test
	public void testRender() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBatch() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetToCorrectInputProcessor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCamera() {
		fail("Not yet implemented");
	}

	@Test
	public void testKill() {
		fail("Not yet implemented");
	}

	@Test
	public void testResize() {
		fail("Not yet implemented");
	}

}
