package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nav.Pos;
import nav.Screen;
import intrface.Refresh;
import intrface.ScreenDependent;

public class MiniMap implements ScreenDependent,Refresh{
	private BufferedImage map;
	private Pos position, size;
	private int borderthickness;
	private Screen s;
	private GameMap m;
	public MiniMap(BufferedImage b, Screen s, GameMap m){
		map = b;
		this.s = s;
		this.m = m;
		borderthickness = (int) Screen.WIDTH / 160;
		size = new Pos(Screen.WIDTH / 10, (int) (((double) (map.getHeight()) / map.getWidth()) * (Screen.WIDTH / 10)));
		position = new Pos(Screen.WIDTH / 80, Screen.HEIGHT - size.getY() - 2 * borderthickness - Screen.WIDTH / 80);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		updateScreenDependency();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		//Background
		g.fillRect((int) position.getX(), (int) position.getY(), (int) size.getX() + 2 * borderthickness, (int) size.getY() + 2 * borderthickness);
		//Image
		g.drawImage(map, (int) position.getX() + borderthickness, (int) position.getY() + borderthickness,(int) size.getX(),(int) size.getY(), null);
		//Square
		g.setColor(Color.YELLOW);
		Pos [] s_corners = getTranslatedCorners();
		g.drawLine((int) s_corners[0].getX(), (int) s_corners[0].getY(), (int) s_corners[3].getX(), (int) s_corners[3].getY());
		g.drawLine((int) s_corners[0].getX(), (int) s_corners[0].getY(), (int) s_corners[1].getX(), (int) s_corners[1].getY());
	}
	/**
	 * 	0		1
	 *  ---------
	 *  |		|
	 *  |		|
	 *  ---------
	 *	3 		2
	 * @return
	 */
	private Pos[] getTranslatedCorners() {
		Pos [] corners = new Pos[4];
		Pos temp = s.clone();
		corners[0] = translatePos(temp).add(position.getX() + borderthickness, position.getY() + borderthickness);
		corners[1] = translatePos(temp.addX(Screen.WIDTH)).add(position.getX() + borderthickness, position.getY() + borderthickness);
		corners[2] = translatePos(temp.addY(Screen.HEIGHT)).add(position.getX() + borderthickness, position.getY() + borderthickness);
		corners[3] = translatePos(temp.addX(-Screen.WIDTH)).add(position.getX() + borderthickness, position.getY() + borderthickness);
		return corners;
	}
	/**
	 * Translates a coord in the screen to a coord in the minimap
	 * @param x
	 * @return
	 */
	private double translateX(double x){
		return (x / GameMap.MAX_WIDTH) * size.getX();
	}
	private double translateY(double y){
		return (y / GameMap.MAX_HEIGHT) * size.getY();
	}
	private Pos translatePos(Pos p){
		return new Pos(translateX(p.getX()),translateY(p.getY()));
	}
	@Override
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateScreenDependency() {
		if(size.getX() != (Screen.WIDTH / 10)){
			size.setX((Screen.WIDTH / 10));
			size.setY(((double) (map.getHeight()) / map.getWidth()) * size.getX());
		}
		if(position.getX() != Screen.WIDTH / 80 || position.getY() != Screen.HEIGHT - size.getY() - 2 * borderthickness - Screen.WIDTH / 80)
			position.setPos(Screen.WIDTH / 80, Screen.HEIGHT - size.getY() - 2 * borderthickness - Screen.WIDTH / 80);
		if(borderthickness != (int) Screen.WIDTH / 160)
			borderthickness = (int) Screen.WIDTH / 160;
	}
	
}
