package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import nav.Screen;

public class GameStateManager implements KeyListener, Refresh, MouseWheelListener{
	private GameState gs;
	private boolean moveScreen[];
	private GameMap m;
	public static Screen s;
	public GameStateManager(){
		this.gs = new GameState();
		m = gs.getMap();
		moveScreen = new boolean[4];
		s = new Screen();
	}
	public void update(){
		gs.update();
		for(int i = 0; i < 4; i++){
			if(moveScreen[i]){
				s.move(i);
			}
		}
	}
	public void draw(Graphics g){
		gs.draw(g);
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		int code = arg0.getKeyCode();
		switch(code){
		case 38:
			moveScreen[0] = true;
			break;
		case 39:
			moveScreen[1] = true;
			break;
		case 40:
			moveScreen[2] = true;
			break;
		case 37:
			moveScreen[3] = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int code = arg0.getKeyCode();
		switch(code){
		case 38:
			moveScreen[0] = false;
			break;
		case 39:
			moveScreen[1] = false;
			break;
		case 40:
			moveScreen[2] = false;
			break;
		case 37:
			moveScreen[3] = false;
			break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	@Override
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int move = 2;
		switch(arg0.getWheelRotation()){
		case -1: //UP
			m.setSquareDim(m.getSquareWidth() + move, m.getSquareHeight() + move);
		case 1: //DOWN
			m.setSquareDim(m.getSquareWidth() - move, m.getSquareHeight() - move);
		}
	}

}
