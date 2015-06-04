package gamestate;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import main.Run;
import main.StatFunc;
import map.GameMap;
import nav.Pos;
import nav.Screen;

public class GameStateManager implements KeyListener, MouseWheelListener, MouseListener{
	private GameState gs;
	private boolean moveScreen[];
	private Run r;
	private int mouseWheelRot = 0;
	private Pos shiftScreen;
	public static Screen s;
	public GameStateManager(Run r){
		this.r = r;
		s = new Screen((int) r.frame.getWidth(),r.frame.getHeight());
		try {
			this.gs = new GameState(s,false);//CHANGE TO FALSE TO GENERATE A NEW MAP
		} catch (IOException e) {
			e.printStackTrace();
			r.frame.dispatchEvent(new WindowEvent(r.frame, WindowEvent.WINDOW_CLOSING));
		} 
		s.setPos(GameMap.MAX_WIDTH / 2 - Screen.WIDTH / 2, GameMap.MAX_HEIGHT / 2 - Screen.HEIGHT / 2);
		moveScreen = new boolean[4];
	}
	public void update(){
		gs.update();
		for(int i = 0; i < 4; i++){
			if(moveScreen[i]){
				s.move(i,26);
			}
		}
		changeSquare(mouseWheelRot);
		if(shiftScreen != null){
			s.add(	shiftScreen.sub(MouseInfo.getPointerInfo().getLocation()));
			shiftScreen.setPos(MouseInfo.getPointerInfo().getLocation());
			s.correctPos();
		}
		
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
		case KeyEvent.VK_SPACE:
			gs = new GameState(s,GameMap.getSquareSize());
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
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		mouseWheelRot =  arg0.getWheelRotation();
	}
	private void changeSquare(int i){
		if(mouseWheelRot != 0){
			if(i == -1){
				gs.getMap().setSquareDim(GameMap.getSquareSize() * 1.2);
			}else{
				gs.getMap().setSquareDim(GameMap.getSquareSize() * 0.8);
			}
		}else{
			return;
		}
		mouseWheelRot = 0;
	}
	public GameState getGameState() {
		return gs;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == 1){
			shiftScreen = new Pos(arg0.getX(),arg0.getY());
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton() == 1)
			shiftScreen = null;
	}

}
