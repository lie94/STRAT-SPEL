package utility;

import gamestate.GameState;
import gamestate.GameStateManager;
import gamestate.map.GameMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import nav.Pos;
import nav.Screen;
import square.Square;

public abstract class Utility {
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
	public static String matrixToString(boolean[][] matrix){
		StringBuilder s = new StringBuilder();
		int[][] new_matrix = new int[matrix.length][matrix[0].length];
		for(int i = 0; i < matrix.length; i ++)
			for(int j = 0; j < matrix[0].length; j++)
				if(matrix[i][j])
					new_matrix[i][j] = 1;
				else
					new_matrix[i][j] = 0;
		for(int[] int_arr : new_matrix){
			s.append(arrToString(int_arr));
			s.append(System.getProperty("line.separator"));
		}
		return s.toString();
	} 
	public static String arrToString(Object arr[]){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < arr.length; i++){
			s.append(arr.toString() + ", ");
		}
		return s.toString();
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
	public static boolean PosIsIn(Pos p, Object[][] matrix){
		if(matrix == null){
			return false;
		}
		if(p.getiX() >= 0 && p.getiX() < matrix.length && PosIsIn(p.getiY(),matrix[0])){
			return true;
		}
		return false;
	}
	public static boolean PosIsIn(int p, Object[] arr){
		if(p >= 0 && p < arr.length){
			return true;
		}
		return false;
	}
	public static Pos getMatrixSize(Object[][] matrix){
		return new Pos(matrix.length, matrix[0].length);
	}
	@SuppressWarnings("unchecked")
	public static Pos[] bfs(Pos start, Pos end, Square[][] map) throws IllegalArgumentException{
		if(!PosIsIn(start,map) || !PosIsIn(end,map)){
			throw new IllegalArgumentException("Start or end was out of bounds. Start: " + start.toString() + ". End: " + end.toString() + ". Upper bounds: " + getMatrixSize(map).toString());
		}
		boolean [][] visited = new boolean[map.length][map[0].length];
		Stack<Pair<Pos,ArrayList<Pos>>> s = new Stack<Pair<Pos,ArrayList<Pos>>>();
		ArrayList<Pos> path = new ArrayList<Pos>();
		path.add(start);
		s.push(new Pair<Pos,ArrayList<Pos>>(start,path));
		//ArrayList<Pos> path = new ArrayList<Pos>();
		//System.out.println(start);
		while(!s.isEmpty()){
			Pair<Pos, ArrayList<Pos>> temp = s.pull();
			for(Pos p : getNeighbors(temp.getKey(), visited.length, visited[0].length)){
				//System.out.println(p);
				if(p.equals(end)){
					temp.getValue().add(end);
					Pos[] out_arr = new Pos[temp.getValue().size()];
					for(int i = 0 ; i < temp.getValue().size(); i++){
						out_arr[i] = temp.getValue().get(i);
					}
					return out_arr;
				}
				if(!visited[p.getiX()][p.getiY()]){
					visited[p.getiX()][p.getiY()] = true;
					ArrayList<Pos> path_copy = (ArrayList<Pos>) temp.getValue().clone();
					path_copy.add(p);
					s.push(
							new Pair<Pos,ArrayList<Pos>>(p,path_copy));
				}
			}
		}
		return null;
	}
	private static ArrayList<Pos> getNeighbors(Pos p, int map_width, int map_height){
		ArrayList<Pos> ret = new ArrayList<Pos>();
		if(p.getiY() != 0){
			ret.add(new Pos(	p.getiX()						,p.getiY() - 1));
			ret.add(new Pos(	mod(p.getiX() - 1,map_width)	,p.getiY() - 1));
			ret.add(new Pos(	mod(p.getiX() + 1,map_width)	,p.getiY() - 1));	
		}
		ret.add(new Pos(	mod(p.getiX() - 1,map_width)	,p.getiY()));
		ret.add(new Pos(	mod(p.getiX() + 1,map_width)	,p.getiY()));
		if(p.getiY() != map_height){
			ret.add(new Pos(	p.getiX()						,p.getiY() + 1));
			ret.add(new Pos(	mod(p.getiX() - 1,map_width)	,p.getiY() + 1));
			ret.add(new Pos(	mod(p.getiX() + 1,map_width)	,p.getiY() + 1));	
		}
		return ret;
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
