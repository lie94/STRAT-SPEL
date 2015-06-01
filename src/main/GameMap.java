package main;

import gamestate.GameStateManager;
import intrface.Refresh;
import intrface.SaveAble;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import nav.Pos;
import nav.Screen;
import square.Square;

public class GameMap implements Refresh,SaveAble{
	private Square[][] squares;
	public static int MAX_WIDTH, MAX_HEIGHT;
	public static final int STD_MAX_WIDTH = 40, STD_MAX_HEIGHT = 40;
	private static int SQUARE_WIDTH,SQUARE_HEIGHT;
	private Screen s;
	private BufferedImage tiles;
	public GameMap(final int size, Screen s) throws IOException{
		this.s = s;
		SQUARE_WIDTH = STD_MAX_WIDTH; 
		SQUARE_HEIGHT = STD_MAX_HEIGHT;
		squares = StatFunc.generateMap(size);
		MAX_WIDTH = squares.length * SQUARE_WIDTH;
		MAX_HEIGHT = squares[0].length * SQUARE_HEIGHT;
		tiles = ImageIO.read(getClass().getResourceAsStream("/res/tiles.jpg"));
	}
	public GameMap(final int width, final int height, Screen s){
		squares = new Square[width][height];
		this.s = s;
		SQUARE_WIDTH = STD_MAX_WIDTH; 
		SQUARE_HEIGHT = STD_MAX_HEIGHT;
		MAX_WIDTH = squares.length * SQUARE_WIDTH;
		MAX_HEIGHT = squares[0].length * SQUARE_HEIGHT;
		try {
			tiles = ImageIO.read(getClass().getResourceAsStream("/res/tiles.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < 2 * squares.length + 2; i++){
			s.append("-");
		}
		s.append(System.getProperty("line.separator"));
		for(int j = 0; j < squares[0].length; j++){
			s.append("|");
			for(int i = 0; i < squares.length; i++){
				s.append(squares[i][j].getType());
				s.append(",");
			}
			s.append("|");
			s.append(System.getProperty("line.separator"));
		}
		for(int i = 0; i < 2 * squares.length + 2; i++){
			s.append("-");
		}
		return s.toString();
	}
	@Override
	public void update() {
		
	}
	@Override
	public void draw(Graphics g) {
		Pos p, temp;
		int isIn;
		for(int i = 0; i < squares.length; i++){
			for(int j = 0; j < squares[0].length; j++){
				p = getPosOfSquare(i,j);
				isIn = GameStateManager.s.isSquareIn(p); 
				if(	 isIn != 0){
					if(isIn == 1)
						p.minus(GameStateManager.s);
					else
						if(GameStateManager.s.getX() < 0)
							p.addX(-MAX_WIDTH).minus(GameStateManager.s);
						else
							p.addX(MAX_WIDTH).minus(GameStateManager.s);
					if(getSquareWidth() < 100){
						g.setColor(StatFunc.getColor(squares[i][j].getType()));
						g.fillRect((int) p.getX(), (int) p.getY(), (int) getSquareWidth(), (int) getSquareHeight());
					}else{
						temp = StatFunc.typeToMapPart(squares[i][j]);
						g.drawImage(tiles, 
								(int) p.getX()						, (int) p.getY()					,
								(int) p.getX() + getSquareWidth()	, (int) p.getY() + getSquareHeight(),
								(int) temp.getX()					, (int) temp.getY()					,
								(int) temp.getX() + 500				, (int) temp.getY() + 500		
								,null);
					}
				}
			}
		}
	}
	public static int getSquareWidth(){
		return SQUARE_WIDTH;
	}
	public static int getSquareHeight(){
		return SQUARE_HEIGHT;
	}
	
	public Pos getPosOfSquare(int i, int j){
		return new Pos(SQUARE_WIDTH * i, SQUARE_HEIGHT * j);
	}
	@Override
	public void newTurn() {
	}
	public void setSquareDim(double d, double e){
		if((int) e * squares[0].length * 0.9 < Screen.HEIGHT || (int) d * squares.length * 0.9 < Screen.WIDTH)
			return;
		if( d < 1)
			d = 1;
		if( e < 1)
			e = 1;
		Pos oldMiddle = s.getRelativeMiddle();
		GameMap.SQUARE_WIDTH = (int) d;
		GameMap.SQUARE_HEIGHT = (int) e;
		refreshMax();
		s.setPos(new Pos(oldMiddle.getX() * MAX_WIDTH - Screen.WIDTH / 2, oldMiddle.getY() * MAX_HEIGHT - Screen.HEIGHT / 2));
	}
	private void refreshMax(){
		MAX_WIDTH = squares.length * SQUARE_WIDTH;
		MAX_HEIGHT = squares[0].length * SQUARE_HEIGHT;
	}
	public Square[][] getSquares(){
		return squares;
	}
	@Override
	public String toSaveFormat(StringBuilder s) {
		String new_line = System.getProperty("line.separator");
		s.append("SX LENGTH: " + squares.length);
		s.append(new_line);
		s.append("SY LENGTH: " + squares[0].length);
		s.append(new_line);
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[0].length; y++){
				//s.append("(" + x + ", " + y + "): ");
				squares[x][y].toSaveFormat(s);
				s.append(new_line);
			}
		}
		return s.toString();
	}
}
