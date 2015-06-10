package animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import nav.Screen;

public abstract class Animation {
	private final int tickRate;
	protected int tick;
	protected static int sheetWidth, sheetHeight;
	protected static int nrOfSprites;
	protected static boolean hasBeenInit = false;
	protected static BufferedImage imgSheet;
	private int sub_tick;
	Animation(){
		tickRate = 5;
	}
	public void update(){
		sub_tick++;
		if(sub_tick > tickRate){
			sub_tick = 0;
			tick = (tick + 1) % nrOfSprites;
		}
	}
	int getTick(){
		return tick;
	}
	public void draw(Graphics g, int x, int y){
		double coorVar = (double) (Screen.WIDTH) / Screen.HEIGHT;
		g.drawImage(imgSheet, 
				x									, y									,
				(int) (x + sheetWidth * coorVar)	, (int) (y + sheetHeight * coorVar)	, 
				tick * sheetWidth					, 0									,
				(tick + 1 ) * sheetWidth 			, sheetHeight						,
				null);
	}
	public void draw(Graphics g){
		draw(g,0,0);
	}
	protected void init(int nr_of_sprites, int sheet_width, int sheet_height, String s) throws IOException{
		nrOfSprites = nr_of_sprites;
		sheetWidth = sheet_width;
		sheetHeight = sheet_height;
		imgSheet = ImageIO.read(getClass().getResourceAsStream("/res/img/animation/" + s + ".png"));
	}
}
