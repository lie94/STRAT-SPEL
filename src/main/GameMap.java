package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import square.Square;

public class GameMap implements Updateable{
	private Square[][] squares;
	private final int size;
	private final int type;
	private BufferedImage tiles;
	public GameMap(final int size, final int type){
		if(size < 0 || size > 5){
			throw new IllegalArgumentException("Size must be larger than 0 and smaller than 5");
		}
		this.size = size;
		this.type = type;
		squares = generateMap();
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(tiles, 0, 0, tiles.getWidth(), tiles.getHeight(), 505, 0, 500, 500, null);
	}
	private Square[][] generateMap(){
		Square[][] temp;
		Random rn = new Random();
		switch(size){
		case 0:
			temp = new Square[20][36];
			break;
		default:
			temp = new Square[40][72];
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
		return new Square(type);
	}
	private int terrain(Random rn,int ... surroundingSquareTypes){
		double proc = (rn.nextInt(99) + 1 / 100);
		if(proc > 0.80){
			return rn.nextInt(6);
		}
		int[] weights = new int[6];
		for(int i : surroundingSquareTypes){
			weights[i]++;
		}
		double sum = StatFunc.sum(weights);
		double waterbuff = 0.8 * sum;
		sum += ((int) (waterbuff)) * 2;
		weights[0] += waterbuff;
		weights[1] += waterbuff;
		for(int i = 0; i < weights.length; i ++){
			if(weights[i] / sum > proc){
				if(i == 0 || i == 1){
					proc = rn.nextInt(99);
					if(proc < 70){
						return 0;
					}else{
						return 1;
					}
				}
				return i; 
			}else{
				proc -= weights[i] / sum;
			}
		}
		return weights[5];
	}
}
