package test;

import main.StatFunc;
import nav.Pos;
import junit.framework.TestCase;

public class StatFuncTest extends TestCase{
	public void testOperator(){
		Pos p = new Pos(1,1);
		assertEquals(1.0,p.getX());
		assertEquals(1,p.getX() < 2 ? 1 : 2);
	}
	public void testStatFuncs(){
		assertEquals(2	,StatFunc.mod(7, 5));
		assertEquals(4	,StatFunc.mod(-1,5));
		assertEquals(-1 ,-1 % 5);
		
	}
}
