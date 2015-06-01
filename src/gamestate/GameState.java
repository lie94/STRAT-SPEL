package gamestate;

import intrface.Refresh;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GameMap;
import main.StatFunc;
import nav.Screen;

public class GameState implements Refresh{
	private GameMap map;
	private Screen s;
	private BufferedImage miniMap;
	private int miniMapWidth; 
	private int miniMapHeight;
	GameState(Screen s, boolean loadMap) throws IOException{
		if(loadMap)
			map = StatFunc.loadMap("test",s,this);
		else
			map = new GameMap(1,s);
		miniMap = StatFunc.getMiniMap(map);
		miniMapWidth = (int) (Screen.WIDTH / 10);
		miniMapHeight = (int) (((double) (miniMap.getHeight()) / miniMap.getWidth()) * (Screen.WIDTH / 10));
		this.s = s;
	}
	public void update(){
		map.update();
		if(miniMapWidth != (int) (Screen.WIDTH / 10)){
			miniMapWidth = (int) (Screen.WIDTH / 10);
			miniMapHeight = (int) (((double) (miniMap.getHeight()) / miniMap.getWidth()) * (Screen.WIDTH / 10));
		}
	}
	public void draw(Graphics g){
		map.draw(g);
		int borderthickness = (int) Screen.WIDTH / 160;
		g.setColor(Color.BLACK);
		g.fillRect((int) (Screen.WIDTH / 80), (int) (Screen.HEIGHT - miniMapHeight - 2 * borderthickness - Screen.WIDTH / 80), miniMapWidth + 2 * borderthickness, miniMapHeight + 2 * borderthickness);
		g.drawImage(miniMap, (int) (Screen.WIDTH / 80) + borderthickness, (int) (Screen.HEIGHT - miniMapHeight - borderthickness - Screen.WIDTH / 80),miniMapWidth,miniMapHeight, null);	
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
