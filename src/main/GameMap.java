package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

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
		squares = generateMap();
		MAX_WIDTH = squares.length * Square.WIDTH;
		MAX_HEIGHT = squares[0].length * Square.HEIGHT;
		try {
			tiles = ImageIO.read(getClass().getResourceAsStream("/res/tiles.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(toString());
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
		BufferedImage tempImg;
		for(int i = 0; i < squares.length; i++){
			for(int j = 0; j < squares[0].length; j++){
				p = getPosOfSquare(i,j);
				if(	s.isSquareIn(p)){
					p.minus(s);
					temp = typeToMapPart(squares[i][j]);
					g.drawImage(tiles, 
							(int) p.getX()					, (int) p.getY()				,
							//500								, 500,
							(int) p.getX() + Square.WIDTH	, (int) p.getY() + Square.HEIGHT,
							(int) temp.getX()				, (int) temp.getY()				,
							//500								,	500
							(int) temp.getX() + 500			, (int) temp.getY() + 500		
							,null);
					//tempImg = ; 
					//System.out.println(squares[i][j].getType() + ": "  + temp);
					//g.drawImage(tiles.getSubimage((int) temp.getX(), (int) temp.getY(), 500, 500), (int) p.getX(), (int) p.getY(), Square.WIDTH, Square.HEIGHT,null);
					//g.drawImage(tiles, (int) p.getX(), (int) p.getY(), Square.WIDTH	, Square.HEIGHT, (int) temp.getX(), (int) temp.getY(), 500, 500, null);
					//g.drawImage(tiles, (int) p.getX(), (int) p.getY(), 500			, 500					, (int) temp.getX(), (int) temp.getY(), 500, 500, null);
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
	private Square[][] generateMap(){
		Square[][] temp;
		Random rn = new Random();
		switch(size){
		case 0:
			temp = new Square[36][20];
			break;
		default:
			temp = new Square[72][36];
		}
		for(int i = 0; i < temp.length; i++){
			for(int j = 0; j < temp[0].length; j++){
				temp[i][j] = generateTerrain(i,j,rn,temp); 
			}
		}
		return temp;
	}
	private Square generateTerrain(final int i, final int j, Random rn, Square[][] temp){
		int type = 0;
		if(i == 0){
			if(j == 0){
				type = rn.nextInt(6);
			}else{
				type = terrain(rn,temp[i][j - 1].getType());
			}
		}else if(i != temp.length - 1){
			if(j == 0)
				type = terrain(rn,temp[i - 1][j].getType());
			else{
				if(j != temp[0].length - 1){
					type = terrain(rn,temp[i - 1][j].getType(), temp[i - 1][j - 1].getType(), temp[i][j - 1].getType(), temp[i - 1][j + 1].getType());
				}else
					type = terrain(rn,temp[i - 1][j].getType(), temp[i - 1][j - 1].getType(), temp[i][j - 1].getType());
			}
		}else{
			if(j == 0){
				type = terrain(rn,temp[i - 1][j].getType(),temp[0][0].getType());
			}else{
				if(j != temp[0].length - 1)
					type = terrain(rn,temp[i - 1][j].getType(), temp[i - 1][j - 1].getType(), temp[i][j - 1].getType(), temp[i - 1][j + 1].getType(),
							temp[0][j].getType(), temp[0][j - 1].getType(), temp[0][j + 1].getType());
				else
					type = terrain(rn,temp[i - 1][j].getType(), temp[i - 1][j - 1].getType(), temp[i][j - 1].getType(),
							temp[0][j].getType(), temp[0][j - 1].getType());
			}
		}
		return new Square(type,this);
	}
	private int terrain(Random rn,int ... surroundingSquareTypes){
		double proc = (rn.nextInt(99) + 1.0 ) / 100;
		if(proc > 0.90){
			return rn.nextInt(6);
		}
		int[] weights = new int[6];
		for(int i : surroundingSquareTypes){
			weights[i]++;
		}
		double sum = StatFunc.sum(weights);
		if(weights[0] / (double) sum > 0.5){
			proc = rn.nextInt(5);
			if(proc > 0){
				return 0;
			}else{
				return rn.nextInt(6);
			}
		}
		return rn.nextInt(6);
	}
	@Override
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
}
