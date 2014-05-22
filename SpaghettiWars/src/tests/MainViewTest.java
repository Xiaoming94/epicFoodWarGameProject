package tests;

import static org.junit.Assert.*;
import gamecomponent.Model;
import gamecomponent.views.GameScreen;
import gamecomponent.views.IGameScreen;
import gamecomponent.views.MainView;
import gamecomponent.views.MenuScreen;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Input.Keys;

public class MainViewTest {
	Model model = new Model();
	MainView mainView;
	//MainView mainView = new MainView(model);
	

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
	public void testMainView() {
		mainView = new MainView(model);
		assertTrue(model.getClass() == Model.class && mainView.getClass() == MainView.class);
		//fail("Not yet implemented");
	}

	@Test
	public void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	public void testResize() {
		fail("Not yet implemented");
	}

	@Test
	public void testRender() {
		fail("Not yet implemented");
	}

	@Test
	public void testPause() {
		fail("Not yet implemented");
	}

	@Test
	public void testResume() {
		fail("Not yet implemented");
	}

	@Test
	public void testDispose() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetScreen() {	
//		Model model = new Model();
//		MainView mainView = new MainView(model);
		assertTrue(mainView.getScreen() == null);
		//fail("Not yet implemented");
	}

	@Test
	public void testSetScreen() {
		IGameScreen g = new GameScreen(mainView);
		mainView.setScreen(g);
		assertTrue(mainView.getScreen() != null);
		//fail("Not yet implemented");
	}

	@Test
	public void testStartGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
//		MainView mainView = new MainView(model);
		mainView.setScreen(new GameScreen(mainView));
		mainView.update(null,  new Integer(Keys.ESCAPE));
		assertTrue(mainView.getScreen().getClass() == GameScreen.class);
		
		//fail("Not yet implemented");
	}

	@Test
	public void testDisconnect() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetModel() {
		
		fail("Not yet implemented");
	}

}
