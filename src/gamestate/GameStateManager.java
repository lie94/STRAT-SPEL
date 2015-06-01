package gamestate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import main.GameMap;
import main.Run;
import main.StatFunc;
import nav.Screen;

public class GameStateManager implements KeyListener, MouseWheelListener{
	private GameState gs;
	private boolean moveScreen[];
	private Run r;
	private int mouseWheelRot = 0;
	public static Screen s;
	public GameStateManager(Run r){
		this.r = r;
		s = new Screen((int) r.frame.getWidth(),r.frame.getHeight());
		this.gs = new GameState(s,false);
		moveScreen = new boolean[4];
	}
	public void update(){
		gs.update();
		for(int i = 0; i < 4; i++){
			if(moveScreen[i]){
				s.move(i);
			}
		}
		changeSquare(mouseWheelRot);
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
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		mouseWheelRot =  arg0.getWheelRotation();
	}
	private void changeSquare(int i){
		if(mouseWheelRot != 0){
			if(i == -1){
				gs.getMap().setSquareDim(GameMap.getSquareWidth() * 1.2, GameMap.getSquareHeight() * 1.2);
				if(GameMap.getSquareWidth() > 500){
					gs.getMap().setSquareDim(500, GameMap.getSquareHeight());
				}
				if(GameMap.getSquareHeight() > 500){
					gs.getMap().setSquareDim(GameMap.getSquareWidth(), 500);
				}
			}else{
				gs.getMap().setSquareDim(GameMap.getSquareWidth() * 0.8, GameMap.getSquareHeight() * 0.8);
				
			}
		}else{
			return;
		}
		mouseWheelRot = 0;
	}
	public GameState getGameState() {
		return gs;
	}

}
