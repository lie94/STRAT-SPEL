package gamestate;

import intrface.Refresh;
import java.awt.Graphics;
import java.io.IOException;

import main.StatFunc;
import map.GameMap;
import map.MiniMap;
import nav.Screen;

public class GameState implements Refresh{
	private GameMap map;
	private Screen s;
	private MiniMap minimap;
	GameState(Screen s, boolean loadMap) throws IOException{
		if(loadMap)
			try{
				map = StatFunc.loadMap("test",s,this);
			}catch(Exception e){
				map = new GameMap(1,s);
			}
		else
			map = new GameMap(1,s);
		minimap = new MiniMap(StatFunc.getMiniMap(map),s,map);
		
		this.s = s;
	}
	public void update(){
		map.update();
		minimap.update();
	}
	public void draw(Graphics g){
		map.draw(g);
		minimap.draw(g);
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
