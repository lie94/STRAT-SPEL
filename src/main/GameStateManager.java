package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nav.Screen;

public class GameStateManager implements KeyListener, MouseListener, Refresh{
	private GameState gs;
	private Screen s;
	private boolean moveScreen[];
	public GameStateManager(GameState gs, Screen s){
		this.gs = gs;
		this.s = s;
		moveScreen = new boolean[4];
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
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
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

}
