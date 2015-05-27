package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import nav.Pos;
import nav.Screen;
import square.Square;

public class GameMap implements Refresh{
	private Square[][] squares;
	public static int MAX_WIDTH = 0, MAX_HEIGHT = 0;
	private final int size;
	private final int type;
	private Screen s;
	private BufferedImage tiles;
	public GameMap(final int size, final int type, Screen s){
		if(size < 0 || size > 5){
			throw new IllegalArgumentException("Size must be larger than 0 and smaller than 5");
		}
		this.s = s;
		this.size = size;
		this.type = type;
		squares = StatFunc.generateMap(size);
		MAX_WIDTH = squares.length * Square.WIDTH;
		MAX_HEIGHT = squares[0].length * Square.HEIGHT;
		try {
			tiles = ImageIO.read(getClass().getResourceAsStream("/res/tiles.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(toString());
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
				isIn = s.isSquareIn(p); 
				if(	 s.isSquareIn(p) != 0){
					//p.minus(s);
					if(isIn == 1)
						p.minus(s);
					else
						if(s.getX() < 0)
							p.addX(-MAX_WIDTH).minus(s);
						else
							p.addX(MAX_WIDTH).minus(s);
					temp = typeToMapPart(squares[i][j]);
					g.drawImage(tiles, 
							(int) p.getX()					, (int) p.getY()				,
							//500								, 500,
							(int) p.getX() + Square.WIDTH	, (int) p.getY() + Square.HEIGHT,
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
	public static Pos typeToMapPart(Square s){
		return typeToMapPart(s.getType());
	}
	public Pos getPosOfSquare(int i, int j){
		return new Pos(Square.WIDTH * i, Square.HEIGHT * j);
	}
	@Override
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
}
