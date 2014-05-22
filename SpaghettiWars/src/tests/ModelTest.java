//Author: Jimmy

package tests;

import static org.junit.Assert.*;
import gamecomponent.Model;

import org.junit.Test;

import utilities.PizzaSlicer;

public class ModelTest {

	Model model = new Model();
	

	@Test
	public void testModel() {
		Model temp = model;
		model = new Model();
		assertTrue(model != temp);
	}

	@Test
	public void testGetOtherPlayers() {
		assertTrue(model.getOtherPlayers().size() == 0);
	}

	@Test
	public void testGetStillEntitys() {
		assertTrue(model.getStillEntitys().size() == 0);
	}

	@Test
	public void testGetPickUps() {
		assertTrue(model.getPickUps().size() == 0);
	}

	@Test
	public void testGetPizzaSlicer() {
		assertTrue(model.getPizzaSlicer() instanceof PizzaSlicer);
	}

	@Test
	public void testGetSelectedWeapon() {
		assertTrue(model.getSelectedWeapon() > -1 && model.getSelectedWeapon() < 9);
	}

	@Test
	public void testGetProjectiles() {
		assertTrue(model.getProjectiles().size() == 0);
	}

	@Test
	public void testSetGameActive() {
		model.setGameActive(false);
		model.setGameActive(true);
		assertTrue(model.isGameActive());
	}

	@Test
	public void testIsGameActive() {
		model.setGameActive(true);
		model.setGameActive(false);
		assertTrue(!model.isGameActive());
	}
	
	@Test
	public void testSetViewSize() {
		model.setViewSize(5, 3);
		assertTrue(model.getWidth() == 5 && model.getHeight() == 3);
	}

	@Test
	public void testGetWidth() {
		model.setViewSize(10000, 9);
		assertTrue(model.getWidth() == 10000);
	}

	@Test
	public void testGetHeight() {
		model.setViewSize(8, 2);
		assertTrue(model.getHeight() == 2);
	}

	@Test
	public void testSetControllerRunning() {
		model.setControllerRunning(true);
		model.setControllerRunning(false);
		model.setControllerRunning(true);
		assertTrue(model.isControllerRunning());
	}

	@Test
	public void testIsControllerRunning() {
		model.setControllerRunning(true);
		model.setControllerRunning(false);
		model.setControllerRunning(false);
		assertTrue(!model.isControllerRunning());
	}
}
