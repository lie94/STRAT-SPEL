package gamestate;

import intrface.Refresh;

import java.awt.Color;
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
		//g.drawImage(miniMap, (int) Screen.WIDTH / 20, (int) (Screen.HEIGHT / 20), null);
		//int borderthickness = (int) Screen.WIDTH / 80;
		g.setColor(Color.BLACK);
		int miniMapWidth = (int) (Screen.WIDTH / 10), miniMapHeight = (int) ((miniMap.getHeight() / miniMap.getWidth()) * (Screen.WIDTH / 10));
		g.fillRect(0, 0, miniMapWidth, miniMap.getHeight());
		g.fillRect(0,(int) (Screen.HEIGHT - miniMap.getHeight()),miniMapWidth, miniMapHeight);
		g.fillRect((int) Screen.WIDTH - miniMap.getWidth(), 0 , miniMapWidth, miniMapHeight);
		g.fillRect((int) Screen.WIDTH - miniMap.getWidth(), (int) Screen.HEIGHT - miniMap.getHeight(), miniMapWidth, miniMapHeight);
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
