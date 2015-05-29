package nav;

import main.GameMap;
import main.StatFunc;


public class Screen extends Pos{
	public static final double SPEED = 10;
	public static double WIDTH = 1280 /*+ 1280 * 0.1*/, HEIGHT = 720 /*+ 720 * 0.1*/;
	public Screen(int width, int height){
		WIDTH = width;
		HEIGHT = height;
		x = 0;
		y = 0;
	}
	private Screen(double x, double y, double width, double height){
		WIDTH = width;
		HEIGHT = height;
		this.x = x;
		this.y = y;
	}
	public Screen(){
		x = 0;
		y = 0;
	}
	public void setPos(Pos p){
		x = p.getX();
		y = p.getY();
	}
	public void move(int dir, double speed){
		switch(dir){
		case 0:
			y = (y - speed);
			if(y < 0)
				y = 0;
			break;
		case 1:
			x = (x + speed) % GameMap.MAX_WIDTH;
			break;
		case 2:
			y = (y + speed);
			if(y > GameMap.MAX_HEIGHT - HEIGHT)
				y = GameMap.MAX_HEIGHT - HEIGHT;
			break;
		case 3:
			x = (x - speed);
			if(x < -WIDTH)
				x = GameMap.MAX_WIDTH - WIDTH;
			break;
		}
	}
	public Pos getRelativeMiddle(){
		return new Pos(StatFunc.avg(x,x + WIDTH)  / GameMap.MAX_WIDTH, StatFunc.avg(y,y+HEIGHT) / GameMap.MAX_HEIGHT);
	}
	public void move(int dir){
		move(dir,SPEED);
	}
	public Screen clone(){
		return new Screen(x,y,WIDTH,HEIGHT);
	}
	/**
	 * Returns 0 if the square isn't in
	 * Returns 1 if the square is in normaly
	 * Returns 2 if the suaree is in on the opposite side of the screen
	 * @param p
	 * @return
	 */
	public int isSquareIn(Pos p){
		if(	x < p.getX() + GameMap.getSquareWidth() 		&& x + WIDTH 		> p.getX() &&
			y < p.getY() + GameMap.getSquareHeight() 		&& y + HEIGHT 		> p.getY())
			return 1;
		if(x < 0 || x > GameMap.MAX_WIDTH - WIDTH){
			Screen d = clone();
			if(d.getX() < 0)
				d.setX(GameMap.MAX_WIDTH + d.getX());
			else
				d.setX(-GameMap.MAX_WIDTH + d.getX());
			if(	d.getX() < p.getX() + GameMap.getSquareWidth()		&& d.getX() + WIDTH 		> p.getX() &&
				d.getY() < p.getY() + GameMap.getSquareHeight() 		&& d.getY() + HEIGHT 		> p.getY())
				return 2;
		}
		return 0;
	}
}
