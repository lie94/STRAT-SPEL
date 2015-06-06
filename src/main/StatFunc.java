package main;

import gamestate.GameState;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

import map.GameMap;
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
	public static Square[][] generateMap(int size){
		Random rn = new Random();
		Square[][] temp = new Square[160][100];
		temp = setAllElements(new Square(0),temp);
		int radius = temp[1].length / 5;
		int points = 25 + rn.nextInt(11);
		for(int i = 0; i < points; i++){
			int x = rn.nextInt(temp.length);
			int y = rn.nextInt(temp[0].length);
			int l_limit = (y + 5 + rn.nextInt(radius + 1));
			if(l_limit >= temp[0].length){
				l_limit = temp[0].length;
			}
			int type = getType(y,l_limit,temp[0].length);
			temp[x][y] = new Square(type);
			int last_k_limit = x + radius + rn.nextInt(5);
			int k_limit;
			int last_k = x;
			//Shallower water on top
			int modifier = rn.nextInt(2);
			for(int temp_x = last_k - 2 - modifier; temp_x < last_k_limit + + 2 + rn.nextInt(2) + modifier; temp_x++){
				if(y - 1 >= 0 && y - 1 < temp[0].length && temp[mod(temp_x,temp.length)][y - 1].getType() == 0){
					temp[mod(temp_x,temp.length)][y - 1] = new Square(1);
				}
			}
			for(int l = y; l < l_limit; l++){
				k_limit = last_k_limit - 2 + rn.nextInt(5);
				int k = last_k;
				if(last_k_limit < k_limit){
					k += rn.nextInt((k_limit - last_k_limit) + 1);
				}else{
					k += - rn.nextInt(last_k_limit - k_limit + 1);
				}
				
				//Shallower water at the left side
				modifier = rn.nextInt(2) + 3 + abs(last_k - k);
				for(int n = k - modifier; n < k; n++){
					if(temp[mod(n,temp.length)][l].getType() == 0)
						temp[mod(n,temp.length)][l] = new Square(1);
				}
				// Right side
				modifier = rn.nextInt(2) + 2 + abs(last_k_limit - k_limit);
				for(int n = k_limit; n < k_limit + modifier; n++){
					if(temp[mod(n,temp.length)][l].getType() == 0)
						temp[mod(n,temp.length)][l] = new Square(1);
				}
				//Stop shallow water
				last_k = k;
				for(; k < k_limit; k++){
					temp[mod(k,temp.length)][l] = new Square(type);
				}
				last_k_limit = k_limit;
			}
			//Shallower water on the bottom
			modifier = rn.nextInt(3);
			for(int temp_x = last_k - 2 - modifier; temp_x < last_k_limit + 1 + rn.nextInt(2) + modifier; temp_x++){
				if(l_limit >= 0 && l_limit < temp[0].length && temp[mod(temp_x,temp.length)][l_limit].getType() == 0){
					temp[mod(temp_x,temp.length)][l_limit] = new Square(1);
				}
			}
		}
		return temp;
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
	private static int getType(int y, int y_end, int y_max){
		double temp = avrage(y,y_end);
		temp = 2 * abs((y_max / 2.0) - temp) / y_max;
		Random rn = new Random();
		if(temp > 0.85){
			if(temp < 0.90 && rn.nextInt(2) == 0){
				return 5; //TUNDRA
			}
			return 3; //ICE
		}else if(temp > 0.40){
			if(temp < 0.60 && rn.nextInt(2) == 0){
				return 2;
			}
			return 5;
		}else if(temp < 0.15 && rn.nextInt(2) == 0){
			return 4;
		}else{
			return 2;
		}
	}
	public static double avg(double ... numbers) {
		double sum = 0;
		for(double d : numbers){
			sum += d;
		}
		return sum / numbers.length;
	}
	public static Color getColor(int i){
		switch(i){
		case 0:
			return new Color(33,60,255);
		case 1:
			return new Color(81,188,255);
		case 2:
			return new Color(38,127,1);
		case 3:
			return new Color(127,255,255);
		case 4:
			return new Color(245,254,145);
		case 5:
			return new Color(128,128,128);
		}
		return null;
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
			return getColor(squares[x][y].getType());
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
			return getColor(index);
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
	public static void save(GameMap m, String savename){
		PrintWriter out;
		try {
			out = new PrintWriter("src/res/saves/" + savename + ".txt");
			out.print(m.toSaveFormat(new StringBuilder()));
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static GameMap loadMap(final String url, Screen s, GameState g) throws IOException{
		BufferedReader in;
		//in = new BufferedReader(new FileReader(getClass().getResourceAsStream("/saves/" + url + ".txt")));
		in = new BufferedReader(	new InputStreamReader(
		        g.getClass().getResourceAsStream("/res/saves/" + url + ".txt")));
				
				
		//in = new BufferedReader(new FileReader("saves/" + url + ".txt"));
		String width = in.readLine().replaceAll(".*:\\s+", "");
		String height = in.readLine().replaceAll(".*:\\s+", "");
		GameMap out = new GameMap(Integer.parseInt(width),Integer.parseInt(height),s);
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
