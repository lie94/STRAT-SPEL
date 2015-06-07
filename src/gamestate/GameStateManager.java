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
import java.util.Random;

import players.Human;
import players.Player;
import square.Square;
import units.Team;
import main.Run;
import main.StatFunc;
import map.Board;
import map.GameMap;
import nav.Pos;
import nav.Screen;

public class GameStateManager implements KeyListener, MouseWheelListener, MouseListener{
	private GameState gs;
	private boolean moveScreen[];
	private Run r;
	private int mouseWheelRot = 0;
	private Pos shiftScreen;
	private boolean setSSNull;
	private Player[] players;
	private Board board;
	public static Screen s;
	public GameStateManager(Run r){
		this.r = r;
		s = new Screen((int) r.frame.getWidth(),r.frame.getHeight());
		board = new Board();
		try {
			this.gs = new GameState(s,board,false);//CHANGE TO FALSE TO GENERATE A NEW MAP
		} catch (IOException e) {
			e.printStackTrace();
			r.frame.dispatchEvent(new WindowEvent(r.frame, WindowEvent.WINDOW_CLOSING));
		} 
		s.setPos(GameMap.MAX_WIDTH / 2 - Screen.WIDTH / 2, GameMap.MAX_HEIGHT / 2 - Screen.HEIGHT / 2);
		moveScreen = new boolean[4];
		players = new Player[1];
		players[0] = new Human();
		
	}
	public void update(){
		if(board.isActive()){
			board.update();
			return;
		}
		gs.update();
		for(int i = 0; i < 4; i++){
			if(moveScreen[i]){
				s.move(i,26);
			}
		}
		changeSquare(mouseWheelRot);
		if(shiftScreen != null){
			Pos temp = new Pos(MouseInfo.getPointerInfo().getLocation());
			temp.add(-r.frame.getLocationOnScreen().getX(), -r.frame.getLocationOnScreen().getY());
			s.add(shiftScreen.sub(temp));
			shiftScreen = temp.clone();
			s.correctPos();
		}
		if(setSSNull){
			shiftScreen = null;
			setSSNull = false;
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(board.isActive()){
			board.sendInput(arg0);
			return;
		}
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
		case KeyEvent.VK_1:
			if(board.isActive()){
				board.sendInput(arg0);
			}else{
				board.startFight(new Team().generateRandom(), new Team().generateRandom(), new Square(new Random().nextInt(6)));
			}
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
			setSSNull = true;
	}

}
