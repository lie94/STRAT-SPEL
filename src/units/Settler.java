package units;

import gamestate.map.GameMap;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import nav.Screen;
import square.Square;

public class Settler extends Unit{
	public static final String TYPE = "Settler ";
	private static BufferedImage image;
	public Settler(Square s) throws IOException {
		super(false,s);
		if(image == null)
			image = ImageIO.read(getClass().getResourceAsStream("/res/img/sprites.png"));
	}
	@Override
	public String getName() {
		return TYPE + name;
	}
	@Override
	public void draw(Graphics g, Screen s, int x, int y) {
		//System.out.println(x + ", " + y + ": " + GameMap.getSquareSize() / 2 + ", " + (int) (  GameMap.getSquareSize() * (double) (5 / 6)));
		g.drawImage(image,x + (int) (GameMap.getSquareSize() * 0.25),y + (int) (GameMap.getSquareSize() * 0.1), GameMap.getSquareSize() / 2,(int) ((GameMap.getSquareSize() / 2) * 1.6), null);
		//g.drawImage(image, x, y, null);
	}
	@Override
	public void fight(Unit u) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void ability() {
		// TODO Auto-generated method stub
		
	}
	
}
