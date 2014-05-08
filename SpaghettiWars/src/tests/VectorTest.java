package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.Vector;

public class VectorTest {

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
	public void testVector() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDeltaX() {
		Vector v = new Vector(8, 8);
		assertTrue(v.getDeltaX() == 8);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetDeltaY() {
		Vector v = new Vector(-1, -14);
		assertTrue(v.getDeltaY() == -14);
		//fail("Not yet implemented");
	}

	@Test
	public void testSetDeltaX() {
		Vector v = new Vector(1,1);
		v.setDeltaX(2);
		assertTrue(v.getDeltaX() == 2);
		//fail("Not yet implemented");
	}

	@Test
	public void testSetDeltaY() {
		Vector v = new Vector(1,1);
		v.setDeltaY(44);
		assertTrue(v.getDeltaY() == 44);
		//fail("Not yet implemented");
	}

	@Test
	public void testSetVector() {
		Vector v = new Vector(1,1);
		v.setVector(-18,  0);
		assertTrue(v.getDeltaX() == -18 && v.getDeltaY() == 0);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetLength() {
		Vector v = new Vector(2, 2);
		assertTrue(v.getLength() < 2.83 && v.getLength() > 2.81);
		//fail("Not yet implemented");
	}

	@Test
	public void testSetLengthTo() {
		//if vector is zero, it has no direction and length must thus remain zero
		Vector v1 = new Vector(0,0);
		v1.setLengthTo(5);
		assertTrue(v1.getLength() == 0);
		
		//if vector has only x-coordinate, new length should be set
		//new x-coordinate should be plus or minus new length
		Vector v2 = new Vector(-1, 0);
		v2.setLengthTo(5);
		assertTrue(v2.getLength() == 5 && v2.getDeltaX() == -5);
		//fail("Not yet implemented");
		
		//if vector has only y-coordinate, new length should be set
		//new y-coordinate should be plus or minus new length
		Vector v3 = new Vector(0, 18);
		v3.setLengthTo(5);
		assertTrue(v3.getLength() == 5 && v3.getDeltaY() == 5);
		
		//x and y should both be sqrt(5^2/2)=3.53553
		Vector v4 = new Vector(2,2);
		v4.setLengthTo(5);
		assertTrue(v4.getLength() == 5 && v4.getDeltaY() < 3.54 && v4.getDeltaY() > 3.53);
		
		//a vector cannot have negative length
		//should setting a negative length reverse the direction of the vector?
		Vector v5 = new Vector(0,1);
		v5.setLengthTo(-5);
		assertTrue(v5.getLength() == 5 && v5.getDeltaY() == -5);
	}

	@Test
	public void testSetVectorByDegree() {
		fail("Not yet implemented");
	}

	@Test
	public void testMultiplyLengthBy() {
		fail("Not yet implemented");
	}

}
