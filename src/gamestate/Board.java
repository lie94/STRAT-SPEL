package gamestate;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import animations.AnimatedChar;
import animations.Animation;
import animations.ImageRelayer;
import nav.Screen;
import square.Square;
import units.Team;
import units.Unit;

public class Board extends GameState{
	protected Board(Screen s, GameStateManager gsm) {
		super(s, gsm);
	}
	private boolean isActive;
	private ArrayList<AnimatedChar> animations;
	Square square;
	public boolean isActive(){
		return isActive;
	}
	public void startFight(Team t1, Team t2, Square s){
		this.square = s;
		animations = new ArrayList<AnimatedChar>();
		for(Unit u : t1.getUnits()){
			animations.add(new AnimatedChar(u));
		}
		for(Unit u : t2.getUnits()){
			animations.add(new AnimatedChar(u));
		}
		setActive(true);
	}
	private void setActive(boolean b){
		isActive = true;
	}
	@Override
	public void update() {
		for(Animation a : animations){
			a.update();
		}
	}
	@Override
	public void draw(Graphics g){
		g.setColor(square.getColor());
		g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
		for(int i = 0 ; i < animations.size(); i++){
			animations.get(i).draw(g, 200 + i * (ImageRelayer.CHAR_SPRITES_WIDTH + 50), 700 - ImageRelayer.CHAR_SPRITES_HEIGHT);
		}
	}
	public void sendInput(KeyEvent arg0) {
		switch(arg0.getKeyCode()){
			case KeyEvent.VK_ESCAPE:
				animations = null;
				square = null;
				isActive = false;
				break;
		}
	
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
