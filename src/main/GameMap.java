package main;

import gamestate.GameStateManager;
import intrface.Refresh;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import nav.Pos;
import nav.Screen;
import square.Square;

public class GameMap implements Refresh{
	private Square[][] squares;
	public static int MAX_WIDTH, MAX_HEIGHT;
	private static int SQUARE_WIDTH,SQUARE_HEIGHT;
	private final int size;
	private final int type;
	private Screen s;
	private BufferedImage tiles;
	public GameMap(final int size, final int type, Screen s){
		if(size < 0 || size > 5){
			throw new IllegalArgumentException("Size must be larger than 0 and smaller than 5");
		}
		this.size = size;
		this.type = type;
		this.s = s;
		SQUARE_WIDTH = 10; 
		SQUARE_HEIGHT = 10;
		squares = StatFunc.generateMap(size);
		MAX_WIDTH = squares.length * SQUARE_WIDTH;
		MAX_HEIGHT = squares[0].length * SQUARE_HEIGHT;
		try {
			tiles = ImageIO.read(getClass().getResourceAsStream("/res/tiles.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getType(){
		return type;
	}
	public int getSize(){
		return size;
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
					temp = typeToMapPart(squares[i][j]);
					g.drawImage(tiles, 
							(int) p.getX()					, (int) p.getY()				,
							//500								, 500,
							(int) p.getX() + getSquareWidth(), (int) p.getY() + getSquareHeight(),
							(int) temp.getX()				, (int) temp.getY()				,
							//500								,	500
							(int) temp.getX() + 500			, (int) temp.getY() + 500		
							,null);
				}
			}
		}
	}
	public static Pos typeToMapPart(int type){
		switch(type){
		case 0:
			return new Pos(0,0);
		case 1:
			return new Pos(505,0);
		case 2:
			return new Pos(1010,0);
		case 3:
			return new Pos(0,505);
		case 4:
			return new Pos(505,505);
		case 5:
			return new Pos(1010,505);
		default:
			return null;
		}
	}
	public static int getSquareWidth(){
		return SQUARE_WIDTH;
	}
	public static int getSquareHeight(){
		return SQUARE_HEIGHT;
	}
	public static Pos typeToMapPart(Square s){
		return typeToMapPart(s.getType());
	}
	public Pos getPosOfSquare(int i, int j){
		return new Pos(SQUARE_WIDTH * i, SQUARE_HEIGHT * j);
	}
	@Override
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
	public void setSquareDim(int square_width, int square_height){
		if(square_height * squares[0].length * 0.9 < Screen.HEIGHT || square_width * squares.length * 0.9 < Screen.WIDTH)
			return;
		if(square_width < 1)
			square_width = 1;
		if(square_height < 1)
			square_height = 1;
		Pos oldMiddle = s.getRelativeMiddle();
		GameMap.SQUARE_WIDTH = square_width;
		GameMap.SQUARE_HEIGHT = square_height;
		refreshMax();
		System.out.println(oldMiddle);
		s.setPos(new Pos(oldMiddle.getX() * MAX_WIDTH - Screen.WIDTH / 2, oldMiddle.getY() * MAX_HEIGHT - Screen.HEIGHT / 2));
		System.out.println(s.getRelativeMiddle());
	}
	private void refreshMax(){
		MAX_WIDTH = squares.length * SQUARE_WIDTH;
		MAX_HEIGHT = squares[0].length * SQUARE_HEIGHT;
	}
}
