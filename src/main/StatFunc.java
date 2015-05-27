package main;

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
	/*public static Square[][] generateMap(int size){
		Square[][] temp;
		Random rn = new Random();
		switch(size){
		case 0:
			temp = new Square[36][20];
			break;
		case 1:
			temp = new Square[160][100];
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
	}*/
	public static Square[][] generateMap(int size, GameMap m){
		Random rn = new Random();
		Square[][] temp = new Square[160][100];
		temp = setAllElements(new Square(0),temp);
		int radius = temp[1].length / 5;
		int points = 40;
		for(int i = 0; i < points; i++){
			int x = rn.nextInt(temp.length);
			int y = rn.nextInt(temp[0].length);
			//int type = 2 + rn.nextInt(4);
			int l_limit = (y + 5 + rn.nextInt(radius + 1));
			if(l_limit > temp[0].length * m.getSquareHeight()){
				l_limit = temp[0].length * m.getSquareHeight();
			}
			int type = getType(y,l_limit,temp[0].length);
			temp[x][y] = new Square(type);
			int last_k_limit = x + radius + rn.nextInt(5);
			int k_limit;
			int last_k = x;
			for(int l = y; l < l_limit; l++){
				k_limit = last_k_limit - 2 + rn.nextInt(5);// - rn.nextInt(abs(l - l_limit / 2) + 1);
				int k = last_k;;
				if(last_k_limit < k_limit){
					k += rn.nextInt((k_limit - last_k_limit) + 1);
				}else{
					k += - rn.nextInt(last_k_limit - k_limit + 1);
				}
				last_k = k;
				for(; k < k_limit; k++){
					int l_mod = mod(l,temp[0].length);
					int k_mod = mod(k,temp.length);
					temp[k_mod][l_mod] = new Square(type);
				}
				//System.out.println(k + ", " + l);
				last_k_limit = k_limit;
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
}
