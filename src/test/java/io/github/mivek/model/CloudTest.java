package io.github.mivek.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.mivek.enums.CloudQuantity;
import io.github.mivek.enums.CloudType;
import io.github.mivek.model.Cloud;

public class CloudTest {
	@Test
	public void testSetAltitudeGetAltitude() {
		Cloud c = new Cloud();
		c.setAltitude(90);
		assertEquals(90, c.getAltitude());
	}
	
	@Test
	public void testSetAltitudeGetHeight() {
		Cloud c = new Cloud();
		c.setAltitude(90);
		assertEquals(300, c.getHeight());
	}
	
	@Test
	public void testSetHeightGetAltitude() {
		Cloud c = new Cloud();
		c.setHeight(300);
		assertEquals(90, c.getAltitude());
	}

	@Test
	public void testSetHeightGetHeight() {
		Cloud c = new Cloud();
		c.setHeight(300);
		assertEquals(300, c.getHeight());
	}

	@Test
	public void testToString() {
		Cloud c = new Cloud();
		c.setAltitude(90);
		c.setQuantity(CloudQuantity.BKN);
		c.setType(CloudType.CB);
		assertEquals(CloudQuantity.BKN.toString() + " " + CloudType.CB.toString() + " 300ft (approx 90m)",
				c.toString());
	}
}
