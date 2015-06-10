package gamestate;

import gamestate.map.GameMap;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import main.Run;
import nav.Pos;
import nav.Screen;

public class GameStateManager implements KeyListener, MouseWheelListener, MouseListener{
	private GameState gs;
	private Run r;
	public static Screen s;
	public GameStateManager(Run r) {
		this.r = r;
		s = new Screen((int) r.frame.getWidth(),r.frame.getHeight());
		try {
			this.gs = new GameMap(s,this);
		} catch (IOException e) {
			e.printStackTrace();
			r.frame.dispatchEvent(new WindowEvent(r.frame, WindowEvent.WINDOW_CLOSING));
		} 
		s.setPos(GameMap.MAX_WIDTH / 2 - Screen.WIDTH / 2, GameMap.MAX_HEIGHT / 2 - Screen.HEIGHT / 2);
	}
	public void update() {
		gs.update();
	}
	public void draw(Graphics g) {
		gs.draw(g);
	}
	public void stop() {
		r.stop();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		gs.sendKeyboardPress(arg0.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		gs.sendKeyboardRelease(arg0.getKeyCode());
	}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		gs.sendMouseWheel(arg0.getWheelRotation(),arg0.getX(), arg0.getY());
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
		gs.sendMousePress(arg0.getButton(),arg0.getX() ,arg0.getY());
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		gs.sendMouseRelease(arg0.getButton(), arg0.getX(), arg0.getY());
	}
	public Pos getFramePos() {
		return new Pos(r.frame.getLocationOnScreen());
	}

}
