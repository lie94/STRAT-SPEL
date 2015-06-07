package map;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import nav.Screen;
import intrface.Refresh;
import square.Square;
import units.Team;
import units.Unit;

public class Board implements Refresh{
	private boolean isActive;
	private Team team1, team2;
	Square square;
	public boolean isActive(){
		return isActive;
	}
	public void startFight(Team t1, Team t2, Square s){
		this.square = s;
		this.team1 = t1;
		this.team2 = t2;
		setActive(true);
	}
	private void setActive(boolean b){
		isActive = true;
	}
	@Override
	public void update() {
		
	}
	@Override
	public void draw(Graphics g){
		g.setColor(square.getColor());
		g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
		for(Unit u : team1.getUnits()){
			u.toString();
		}
		for(Unit u : team2.getUnits()){
			u.toString();
		}
	}
	@Override
	public void newTurn() {
		
	}
	public void sendInput(KeyEvent arg0) {
		switch(arg0.getKeyCode()){
			case KeyEvent.VK_ESCAPE:
				team1 = null;
				team2 = null;
				square = null;
				isActive = false;
				break;
		}
	
	}
}
