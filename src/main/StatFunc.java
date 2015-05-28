package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

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
	public static void save(GameMap m, String savename){
		PrintWriter out;
		try {
			out = new PrintWriter("saves/" + savename + ".txt");
			out.print(m.toSaveFormat(new StringBuilder()));
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
