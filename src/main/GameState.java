package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nav.Screen;

public class GameState implements Refresh{
	private GameMap map;
	private Screen s;
	GameState(Screen s){
		map = new GameMap(0,1,s);
	}
	public void update(){
		
	}
	public void draw(Graphics g){
		map.draw(g);
	}
	@Override
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
}
