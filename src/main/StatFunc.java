package main;

import gamestate.GameState;
import gamestate.GameStateManager;
import gamestate.map.GameMap;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import nav.Pos;
import nav.Screen;
import square.Square;

public class StatFunc {
	public static int sum(int[] intArray){
		int sum = 0;
		for(int i : intArray){
			sum += i;
		}
		return sum;
	}
	
	public static int mod(int i, int dividor){
		if( i < 0)
			return dividor + i;
		else
			return i % dividor;
	}
	public static boolean isIn(Object t, Object ta[]){
		for(Object temp : ta)
			if(t.equals(temp))
				return true;
		return false;
	}
	public static boolean isIn(int t, int ta[]){
		for(int temp : ta)
			if(t == temp)
				return true;
		return false;
	}
	public static int abs(int i){
		if( i < 0)
			return -i;
		return i;
	}
	public static double abs(double i){
		if( i < 0)
			return -i;
		return i;
	}
	public static Square[][] setAllElements(Square element, Square[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				matrix[i][j] = element;
			}
		}
		return matrix;
	}
	public static double avrage(int ... numbers){
		double sum = 0;
		for(int i : numbers){
			sum += i;
		}
		return sum/numbers.length;
	}
	public static double avg(double ... numbers) {
		double sum = 0;
		for(double d : numbers){
			sum += d;
		}
		return sum / numbers.length;
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
	private static Color avrageColor(Square[][] squares, int x, int y, int width, int height){
		int [] arr = new int[6];
		if(squares.length == width){
			return squares[x][y].getColor();
		}else{
			System.out.println(squares.length > width);
			System.out.println(((x + 1 ) * squares.length) / width);
			for(int i = (int) ((x * squares.length) / width) ; i < (int) (((x + 1) * squares.length) / width); i++){
				for(int j = (int) ((y * squares[0].length) / height) ; j < (int) (((y + 1) * squares[0].length)/ height) ; j++){
					System.out.println("SWAG");
					arr[squares[x][y].getType()]++;
				}
			}
			int largest = 0;
			int index = -1;
			for(int i = 0; i < arr.length; i++){
				if(arr[i] > largest){
					index = i;
					largest = arr[i];
				}
			}
			return Square.getColor(index);
		}
	}
	public static BufferedImage getMiniMap(GameMap map) {
		Square[][] squares = map.getSquares();
		BufferedImage temp; 
		if((int) Screen.WIDTH / 5 < squares.length)
			temp = new BufferedImage((int) Screen.WIDTH / 5, (int) (Screen.WIDTH / 5) * squares[0].length / squares.length, BufferedImage.TYPE_INT_RGB);
		else
			temp = new BufferedImage(squares.length, squares[0].length, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < temp.getWidth(); x++){
			for(int y = 0; y < temp.getHeight(); y++){
				temp.setRGB(x, y, avrageColor(squares,x,y,temp.getWidth(),temp.getWidth()).getRGB());
			}
		}
		return temp;
	}
	public static String arrToString(int arr[]){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < arr.length; i++){
			s.append(arr[i] + ", ");
		}
		return s.toString();
	}
	public static void save(GameState gs, String savename){
		PrintWriter out;
		try {
			out = new PrintWriter("src/res/saves/" + savename + ".txt");
			out.print(gs.save(new StringBuilder()).toString());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static GameMap loadMap(final String url, Screen s, GameStateManager gsm) throws IOException{
		BufferedReader in;
		//in = new BufferedReader(new FileReader(getClass().getResourceAsStream("/saves/" + url + ".txt")));
		in = new BufferedReader(	new InputStreamReader(
		        gsm.getClass().getResourceAsStream("/res/saves/" + url + ".txt")));
				
				
		//in = new BufferedReader(new FileReader("saves/" + url + ".txt"));
		String width = in.readLine().replaceAll(".*:\\s+", "");
		String height = in.readLine().replaceAll(".*:\\s+", "");
		GameMap out = new GameMap(Integer.parseInt(width),Integer.parseInt(height),s,gsm);
		String line = in.readLine();
		Square[][] squares = out.getSquares();
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[0].length; y++){
				String[] commands = line.split(", ");
				squares[x][y] = new Square(Integer.parseInt(commands[0]));
				line = in.readLine();
			}
		}
		in.close();
		return out;
	}
}
