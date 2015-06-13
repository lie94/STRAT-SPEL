package test;

import nav.Pos;
import junit.framework.TestCase;

public class StatFuncTest extends TestCase{
	public void testOperator(){
		Pos p = new Pos(1,1);
		assertEquals(1.0,p.getX());
		assertEquals(1,p.getX() < 2 ? 1 : 2);
	}
}
