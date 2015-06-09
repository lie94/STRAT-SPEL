package animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import nav.Screen;
import main.Run;

public class ImageRelayer {
	private static BufferedImage charSheet;
	public static final int CHAR_SPRITES = 4;
	public static final int CHAR_SPRITES_WIDTH = 100;
	public static final int CHAR_SPRITES_HEIGHT = 160;
	public ImageRelayer(Run r) throws IOException{
		charSheet = ImageIO.read(getClass().getResourceAsStream("/res/img/charsheet.png"));
	}
	ImageRelayer(){}
	public void draw(Graphics g, int x, int y,Animation a){
		if(a instanceof AnimatedChar){
			double coorVar = (double) (Screen.WIDTH) / Screen.HEIGHT;
			g.drawImage(charSheet, 
						x										, y											,
						(int) (x + CHAR_SPRITES_WIDTH * coorVar), (int) (y + CHAR_SPRITES_HEIGHT * coorVar)	,
						a.getTick() * CHAR_SPRITES_WIDTH		, 0											,
						(a.getTick() + 1 ) * CHAR_SPRITES_WIDTH , CHAR_SPRITES_HEIGHT						,
						null);
		}else{
			g.drawImage(charSheet, x, y, null);
		}
	}
}
