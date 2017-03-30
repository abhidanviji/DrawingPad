package drawpad;

import static org.junit.Assert.*;

import java.awt.Graphics;

import org.junit.Test;

public class JUnitTest {
	
	
	RoundedRectangleShape tes = new RoundedRectangleShape();
	
	
	@Test
	public void GetRect1PointsTest(){
		
		tes.setEnd1(5, 10);
		 assertTrue(tes.getX1() == 5);
		 assertTrue(tes.getY1() == 10);
	}
	@Test
	public void GetRect2PointsTest(){
		
		tes.setEnd2(12,14);
		 assertTrue(tes.getX2() == 12);
		 assertTrue(tes.getY2() == 14);
	}

}
