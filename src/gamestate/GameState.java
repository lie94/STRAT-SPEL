package gamestate;

import intrface.Refresh;

import java.awt.Graphics;
import java.io.IOException;

import main.StatFunc;
import map.Board;
import map.GameMap;
import map.MiniMap;
import nav.Screen;

public class GameState implements Refresh{
	private GameMap map;
	private Screen s;
	private MiniMap minimap;
	private Board board;
	GameState(Screen s, Board b, boolean loadMap) throws IOException{
		if(loadMap)
			map = StatFunc.loadMap("test",s,this);
		else
			map = new GameMap(1,s);
		board = b;
		minimap = new MiniMap(s,map);
		
		this.s = s;
	}
	GameState(Screen s, int square_width){
		map = new GameMap(1,s,square_width);
		minimap = new MiniMap(s,map);
		map.setSquareDim(square_width);
	}
	public void update(){
		map.update();
		minimap.update();
	}
	public void draw(Graphics g){
		if(board.isActive()){
			board.draw(g);
			return;
		}
		map.draw(g);
		minimap.draw(g);
	}
	public Screen getScreen(){
		return s;
	}
	@Override
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
	public GameMap getMap(){
		return map;
	}
}
