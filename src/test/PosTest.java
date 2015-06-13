package test;

import nav.Pos;
import junit.framework.TestCase;

public class PosTest extends TestCase{
	public void testLength(){
		Pos p1 = new Pos(0,1);
		Pos p2 = new Pos(0,0);
		Pos p3 = new Pos(3,4);
		assertEquals(1.0, p1.lenghtTo(p2));
		assertEquals(5.0, p2.lenghtTo(p3));
		assertEquals(p1.lenghtTo(p2), p2.lenghtTo(p1));
		assertEquals(0.0,p1.lenghtTo(p1));
		assertEquals(Math.sqrt(18),p1.lenghtTo(p3));
	}
}
