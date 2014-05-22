//Author: Louise

//A JUnit class for testing the Vector class.

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
		Vector vector = new Vector(7, -99);
		assertTrue(vector.getDeltaX() == 7 && vector.getDeltaY() == -99);
	}

	@Test
	public void testGetDeltaX() {
		Vector v = new Vector(8, 8);
		assertTrue(v.getDeltaX() == 8);
	}

	@Test
	public void testGetDeltaY() {
		Vector v = new Vector(-1, -14);
		assertTrue(v.getDeltaY() == -14);
	}

	@Test
	public void testSetDeltaX() {
		Vector v = new Vector(1,1);
		v.setDeltaX(2);
		assertTrue(v.getDeltaX() == 2);
	}

	@Test
	public void testSetDeltaY() {
		Vector v = new Vector(1,1);
		v.setDeltaY(44);
		assertTrue(v.getDeltaY() == 44);
	}

	@Test
	public void testSetVector() {
		Vector v = new Vector(1,1);
		v.setVector(-18,  0);
		assertTrue(v.getDeltaX() == -18 && v.getDeltaY() == 0);
	}

	@Test
	public void testGetLength() {
		Vector v = new Vector(2, 2);
		assertTrue(v.getLength() < 2.83 && v.getLength() > 2.81);
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
		Vector v = new Vector(0,0);
		//set vector to length 16, 30 degrees
		//y should be 16*sin(30)=8 and x should be 16*cos(30)=13.85641
		v.setVectorByDegree(16, 30);
		assertTrue(v.getDeltaY() > 7.99 && v.getDeltaY() < 8.01 && v.getDeltaX() > 13.85 && v.getDeltaY() < 13.86 );
		
		v.setVectorByDegree(Math.sqrt(2), 45);
		assertTrue(v.getDeltaY() > 0.99 && v.getDeltaY() < 1.01 && v.getDeltaX() > 0.99 && v.getDeltaY() < 1.01 );
	
	}

	@Test
	public void testMultiplyLengthBy() {
		Vector v = new Vector(1,1);
		//set vector length to 3
		v.setLengthTo(3);
		assertTrue(v.getLength() < 3.01 && v.getLength() > 2.99);
		//multiply it by 4, should make vector length 12
		v.multiplyLengthBy(4);
		assertTrue(v.getLength() < 12.01 && v.getLength() > 11.99);
	}

}
