package gamestate;

import intrface.Refresh;

import java.awt.Graphics;

import main.GameMap;
import nav.Screen;

public class GameState implements Refresh{
	private GameMap map;
	private Screen s;
	GameState(Screen s, boolean loadMap){
		if(loadMap)
			map = null;
		else
			map = new GameMap(1,1,s);
		this.s = s;
	}
	public void update(){
		map.update();
	}
	public void draw(Graphics g){
		map.draw(g);
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
