package gamestate;

import intrface.Refresh;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import main.GameMap;
import main.Run;
import main.StatFunc;
import nav.Screen;

public class GameStateManager implements KeyListener, Refresh, MouseWheelListener{
	private GameState gs;
	private boolean moveScreen[];
	public static Screen s;
	private Run r;
	public GameStateManager(Run r){
		this.r = r;
		s = new Screen();
		this.gs = new GameState(s);
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
		case KeyEvent.VK_ESCAPE:
			StatFunc.save(gs.getMap(),"test");
			r.stop();
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
			gs.getMap().setSquareDim(GameMap.getSquareWidth() + move, GameMap.getSquareHeight() + move);
			break;
		case 1: //DOWN
			gs.getMap().setSquareDim(GameMap.getSquareWidth() - move, GameMap.getSquareHeight() - move);
			break;
		}
	}

}
