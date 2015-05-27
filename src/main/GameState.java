package main;

import java.awt.Graphics;
import nav.Screen;

public class GameState implements Refresh{
	private GameMap map;
	private Screen s;
	GameState(){
		map = new GameMap(1,1);
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
	public GameMap getMap(){
		return map;
	}
}
