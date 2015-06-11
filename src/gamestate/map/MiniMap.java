package gamestate.map;

import gamestate.GameState;
import gamestate.GameStateManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;

import main.StatFunc;
import nav.Pos;
import nav.Screen;

public class MiniMap extends GameState{
	private BufferedImage map;
	private Pos position, size;
	private int borderthickness;
	// Follow screen
	private boolean followScreen;
	// Color of the square indicating where the screen is
	private Color screenBorder;
	public MiniMap(Screen s,GameStateManager gsm, GameMap m){
		super(s,gsm);
		map = StatFunc.getMiniMap(m);
		borderthickness = (int) Screen.WIDTH / 160;
		size = new Pos(Screen.WIDTH / 10,(((double) (map.getHeight()) / map.getWidth()) * (Screen.WIDTH / 10)));
		position = new Pos(Screen.WIDTH / 80, Screen.HEIGHT - size.getY() - 2 * borderthickness - (Screen.WIDTH / 80));
		screenBorder = new Color(0,0,0,192);
	}
	public Pos getPos(){
		return position;
	}
	public Pos getSize(){
		return size;
	}
	public int getThickness(){
		return borderthickness;
	}
	public void update() {
		if(size.getX() != (Screen.WIDTH / 10)){
			size.setX((Screen.WIDTH / 10));
			size.setY(((double) (map.getHeight()) / map.getWidth()) * size.getX());
		}
		if(position.getX() != Screen.WIDTH / 80 || position.getY() != Screen.HEIGHT - size.getY() - 2 * borderthickness - Screen.WIDTH / 80)
			position.setPos(Screen.WIDTH / 80, Screen.HEIGHT - size.getY() - 2 * borderthickness - Screen.WIDTH / 80);
		if(followScreen){
			Pos temp = new Pos(MouseInfo.getPointerInfo().getLocation());
			if(isInMiniMapBorders(temp)){
				Pos move_screen = new Pos(	(double) (temp.getX() - position.getX() - borderthickness) / size.getX(), 
											(double) (temp.getY() - position.getY() - borderthickness) / size.getY());
				s.setPos(move_screen.getX() * GameMap.MAX_WIDTH - Screen.WIDTH / 2, move_screen.getY() * GameMap.MAX_HEIGHT - Screen.HEIGHT / 2);
			}else{
				followScreen = false;
			}
		}
		if(borderthickness != (int) Screen.WIDTH / 160)
			borderthickness = (int) Screen.WIDTH / 160;
	}
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		//Background
		g.fillRect((int) position.getX(), (int) position.getY(), (int) size.getX() + 2 * borderthickness, (int) size.getY() + 2 * borderthickness);
		//Image
		g.drawImage(map, (int) position.getX() + borderthickness, (int) position.getY() + borderthickness,(int) size.getX(),(int) size.getY(), null);
		// Screen border indicating where the sceen is on the map
		g.setColor(screenBorder);
		Pos [] s_corners = getTranslatedCorners();
		if(s_corners[0].getX() < position.getX() + borderthickness){
			//Right line is normal
			g.drawLine((int) s_corners[1].getX(), (int) s_corners[1].getY(), (int) s_corners[2].getX(), (int) s_corners[2].getY());
			//Top line
			g.drawLine((int) position.getX() + borderthickness	, (int) s_corners[1].getY(), (int) s_corners[1].getX(), (int) s_corners[1].getY());
			g.drawLine(	(int) (size.getX() + (s_corners[0].getX())), 
						(int) s_corners[0].getY(), 
						(int) (position.getX() +  borderthickness + size.getX()), 
						(int) s_corners[0].getY());
			//Left Line
			g.drawLine(	(int) (size.getX() + (s_corners[0].getX())),
						(int) s_corners[0].getY(),
						(int) (size.getX() + s_corners[3].getX()),
						(int) s_corners[3].getY());
			//Bottom line
			g.drawLine((int) (position.getX() + borderthickness), (int) s_corners[2].getY(), (int) s_corners[2].getX(), (int) s_corners[2].getY());
			g.drawLine(	(int) (size.getX() + (s_corners[0].getX())), 
						(int) s_corners[3].getY(), 
						(int) (position.getX() +  borderthickness + size.getX()), 
						(int) s_corners[3].getY());
		}else if(s_corners[1].getX() > position.getX() + borderthickness + size.getX()){
			//Right line
			g.drawLine(	(int) (-size.getX() + (s_corners[1].getX())),
						(int) s_corners[1].getY(),
						(int) (-size.getX() + s_corners[1].getX()),
						(int) s_corners[3].getY());
			//Top line
			g.drawLine((int) s_corners[0].getX(), (int) s_corners[0].getY(), (int) (position.getX() + borderthickness + size.getX()), (int) s_corners[0].getY());
			g.drawLine((int) (position.getX() + borderthickness), (int) s_corners[0].getY(),
						(int) (s_corners[1].getX() - size.getX()), (int) s_corners[1].getY());
			//Left line
			g.drawLine((int) s_corners[0].getX(), (int) s_corners[0].getY(), (int) s_corners[3].getX(), (int) s_corners[3].getY());
			//Bottom line
			g.drawLine((int) s_corners[3].getX(), (int) s_corners[3].getY(), (int) (position.getX() + borderthickness + size.getX()), (int) s_corners[3].getY());
			g.drawLine((int) (position.getX() + borderthickness), (int) s_corners[3].getY(), (int) (s_corners[2].getX() - size.getX()), (int) s_corners[2].getY());
			
		}else{
			g.drawLine((int) s_corners[0].getX(), (int) s_corners[0].getY(), (int) s_corners[3].getX(), (int) s_corners[3].getY());
			g.drawLine((int) s_corners[0].getX(), (int) s_corners[0].getY(), (int) s_corners[1].getX(), (int) s_corners[1].getY());
			g.drawLine((int) s_corners[1].getX(), (int) s_corners[1].getY(), (int) s_corners[2].getX(), (int) s_corners[2].getY());
			g.drawLine((int) s_corners[2].getX(), (int) s_corners[2].getY(), (int) s_corners[3].getX(), (int) s_corners[3].getY());
		}
		
	}
	@Override
	public void endTurn() {}

	@Override
	public StringBuilder save(StringBuilder s) {
		return null;
	}

	@Override
	public void sendMousePress(int k, int x, int y) {
		if(k == 1) { //&& move_screen == null ){
			followScreen = true;
		}
	}

	@Override
	public void sendMouseRelease(int k, int x, int y) {
		if(k == 1) {
			followScreen = false;
		}
		
	}

	@Override
	public void sendKeyboardPress(int k) {}

	@Override
	public void sendKeyboardRelease(int k) {}

	@Override
	public void sendMouseWheel(int k, int x, int y) {}
	public boolean isInMiniMap(Pos p){
		return isInMiniMap((int) p.getX(),(int) p.getY());
	}
	public boolean isInMiniMap(int x, int y)  {
		if(		position.getX() <= x && position.getX() + size.getX() + 2 * borderthickness >= x &&
				position.getY() <= y && position.getY() + size.getY() + 2 * borderthickness >= y)
			return true;
		return false;
	}
	public boolean isInMiniMapBorders(Pos p){
		if(		p.getX() >= position.getX() + borderthickness && p.getX() <= position.getX() + size.getX() + borderthickness &&
				p.getY() >= position.getY() + borderthickness && p.getY() <= position.getY() + size.getY() + borderthickness)
				return true;
		return false;
		
	}

	private Pos translatePos(Pos p) {
		return new Pos(translateX(p.getX()),translateY(p.getY()));
	}

	private double translateY(double y) {
		return (y / GameMap.MAX_HEIGHT) * size.getY();
	}

	/**
	 * Translates a coord in the screen to a coord in the minimap
	 * @param x
	 * @return
	 */
	private double translateX(double x) {
		return (x / GameMap.MAX_WIDTH) * size.getX();
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
}
