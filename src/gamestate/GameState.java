package gamestate;

import java.awt.Graphics;

import nav.Pos;
import nav.Screen;

public abstract class GameState{
	protected Screen s;
	private GameStateManager gsm;
	protected GameState(Screen s, GameStateManager gsm){
		this.s = s;
		this.gsm = gsm;
	}
	protected void stop(){
		gsm.stop();
	}
	protected void menu(){
		gsm.menu();
	}
	protected void map(){
		gsm.map();
	}
	/*protected void board(){
		gsm.board();
	}*/
	protected Pos getScreenPos(){
		return gsm.getFramePos();
	}
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void endTurn();
	public abstract StringBuilder save(StringBuilder s);
	public abstract void sendMousePress(int k, int x, int y);
	public abstract void sendMouseRelease(int k, int x, int y);
	public abstract void sendKeyboardPress(int k);
	public abstract void sendKeyboardRelease(int k);
	public abstract void sendMouseWheel(int k, int x, int y);
}
