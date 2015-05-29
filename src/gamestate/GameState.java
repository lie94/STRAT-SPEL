package gamestate;

import intrface.Refresh;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GameMap;
import main.StatFunc;
import nav.Screen;

public class GameState implements Refresh{
	private GameMap map;
	private Screen s;
	private BufferedImage miniMap;
	GameState(Screen s, boolean loadMap){
		if(loadMap)
			map = null;
		else
			map = new GameMap(1,1,s);
		miniMap = StatFunc.getMiniMap(map);
		this.s = s;
	}
	public void update(){
		map.update();
	}
	public void draw(Graphics g){
		map.draw(g);
		//g.drawImage(miniMap, (int) Screen.WIDTH / 20, (int) Screen.WIDTH / 20, null);
	}
	public Screen getScreen(){
		return s;
	}
	@Override
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
	public GameMap getMap(){
		return map;
	}
}
