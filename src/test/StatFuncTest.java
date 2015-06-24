package test;

import gamestate.map.GameMap;
import utility.Utility;
import nav.Pos;
import junit.framework.TestCase;

public class StatFuncTest extends TestCase{
	public void testOperator(){
		Pos p = new Pos(1,1);
		assertEquals(1.0,p.getX());
		assertEquals(1,p.getX() < 2 ? 1 : 2);
	}
	public void testStatFuncs(){
		assertEquals(2	,Utility.mod(7, 5));
		assertEquals(4	,Utility.mod(-1,5));
		assertEquals(-1 ,-1 % 5);
		Pos[] temp = Utility.bfs(new Pos(0,0), new Pos(79,0), GameMap.generateMap(false));
		for(Pos p : temp){
			System.out.println(p);
		}
	}
}
