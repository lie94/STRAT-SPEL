package main;

import gamestate.GameState;
import gamestate.GameStateManager;
import gamestate.map.GameMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import nav.Screen;
import square.Square;

public abstract class StatFunc {
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
				matrix[i][j] = element.clone();
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
