package main;

import java.util.Random;

import nav.Pos;
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
	public static Square[][] generateMap(int size){
		Random rn = new Random();
		Square[][] temp = new Square[160][100];
		temp = setAllElements(new Square(0),temp);
		int radius = temp[1].length / 5;
		int points = 40;
		for(int i = 0; i < points; i++){
			int x = rn.nextInt(temp.length);
			int y = rn.nextInt(temp[0].length);
			int type = 2 + rn.nextInt(4);
			temp[x][y] = new Square(type);
			int l_limit = (y + radius + rn.nextInt(radius + 1)) % temp[0].length;
			if(l_limit > temp[0].length){
				l_limit = temp[0].length * Square.HEIGHT;
			}
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
				System.out.println(k + ", " + l);
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
	public static Square[][] setAllElements(Square element, Square[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				matrix[i][j] = element;
			}
		}
		return matrix;
	}
	private static int terrain(Random rn,int ... surroundingSquareTypes){
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
	private static Square generateTerrain(final int i, final int j, Random rn, Square[][] temp){
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
}
