package main;

import java.awt.Graphics;

import nav.Pos;
import nav.Screen;

public abstract class ClickSquare {
	protected Pos position, size;
	protected Screen s;
	public ClickSquare(Screen s){
		this.s = s;
	}
	public abstract void sendMousePress(int k, int x, int y);
	public abstract void sendMouseRelease(int k, int x, int y);
	public abstract void sendKeyboardPress(int k);
	public abstract void sendKeyboardRelease(int k);
	public abstract void update();
	public abstract void draw(Graphics g);
}
