//Author: Louise

//A JUnit class for testing the Position class.

package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.Position;

public class PositionTest {

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
	public void testPosition() {
		Position pos = new Position(-9, 72);
		assertTrue(pos.getX() == -9 && pos.getY() == 72);
	}

	@Test
	public void testGetX() {
		Position pos = new Position(0,0);
		assertTrue(pos.getX() == 0);
	}

	@Test
	public void testGetY() {
		Position pos = new Position(0, 45.2);
		assertTrue(pos.getY() == 45.2);
	}

	@Test
	public void testDistanceTo() {
		//distance between points should be 9.21954
		Position p1 = new Position(2,3);
		Position p2 = new Position(-5, 9);
		assertTrue(p1.distanceTo(p2) > 9.21 && p1.distanceTo(p2) < 9.22);
		assertTrue(p1.distanceTo(p2) == p2.distanceTo(p1));
	}

}
