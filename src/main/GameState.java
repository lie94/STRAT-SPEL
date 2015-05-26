package main;

import java.awt.Graphics;
import nav.Screen;

public class GameState implements Refresh{
	private GameMap map;
	private Screen s;
	GameState(Screen s){
		map = new GameMap(1,1,s);
		this.s = s;
	}
	public void update(){
		
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
}
