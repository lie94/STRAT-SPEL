package gamestate;

import java.awt.Graphics;

import animations.BackgroundAnimation;
import nav.Screen;

public class Menu extends GameState{
	private BackgroundAnimation bga;
	protected Menu(Screen s, GameStateManager gsm) {
		super(s, gsm);
		bga = new BackgroundAnimation();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		bga.draw(g);
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StringBuilder save(StringBuilder s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMousePress(int k, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMouseRelease(int k, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendKeyboardPress(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendKeyboardRelease(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMouseWheel(int k, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
