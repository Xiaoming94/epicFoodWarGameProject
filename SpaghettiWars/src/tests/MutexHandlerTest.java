//Author: Jimmy

//A JUnit class for testing the MutexHandler class.

package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import sun.awt.Mutex;
import utilities.MutexHandler;

public class MutexHandlerTest {

	@Test
	public void testGetInstance() {
		assertTrue(MutexHandler.getInstance() instanceof MutexHandler);
	}

	@Test
	public void testGetPickUpsMutex() {
		assertTrue(MutexHandler.getInstance().getPickUpsMutex() instanceof Mutex);
	}

	@Test
	public void testGetStillEntitiesMutex() {
		assertTrue(MutexHandler.getInstance().getStillEntitiesMutex() instanceof Mutex);
	}

	@Test
	public void testGetProjectilesMutex() {
		assertTrue(MutexHandler.getInstance().getProjectilesMutex() instanceof Mutex);
	}

	@Test
	public void testGetOtherPlayersMutex() {
		assertTrue(MutexHandler.getInstance().getOtherPlayersMutex() instanceof Mutex);
	}

	@Test
	public void testGetTemporaryProjectilesMutex() {
		assertTrue(MutexHandler.getInstance().getTemporaryProjectilesMutex() instanceof Mutex);
	}

}
