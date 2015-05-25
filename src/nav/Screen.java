package nav;

import main.GameMap;
import main.Run;
import square.Square;


public class Screen extends Pos{
	public static final double SPEED = 10;
	public static double WIDTH = 1280 + 10, HEIGHT = 720 + 10;
	public Screen(int width, int height){
		WIDTH = width;
		HEIGHT = height;
		x = 0;
		y = 0;
	}
	public Screen(){
		x = 0;
		y = 0;
	}
	public void move(int dir, double speed){
		switch(dir){
		case 0:
			y -= speed;
			if(y < 0)
				y = 0;
			break;
		case 1:
			x += speed;
			if(x > GameMap.MAX_WIDTH - WIDTH)
				x = GameMap.MAX_WIDTH - WIDTH;
			break;
		case 2:
			y += speed;
			if(y > GameMap.MAX_HEIGHT - HEIGHT)
				y = GameMap.MAX_HEIGHT - HEIGHT;
			break;
		case 3:
			x -= speed;
			if(x < 0)
				x = 0;
			break;
		}
	}
	public void move(int dir){
		move(dir,SPEED);
	}
	public boolean isSquareIn(Pos p){
		if(	x < p.getX() + Square.WIDTH 		&& x + WIDTH 		> p.getX() &&
			y < p.getY() + Square.HEIGHT 		&& y + HEIGHT 		> p.getY())
			return true;
		return false;
	}
}
