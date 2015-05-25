package square;

import main.GameMap;

/**
 * There are 6 different types of squares
 * Type 	| Meaning
 * ---------|--------------
 * 0		|Ocean
 * 1		|Shallow water
 * 2 		|Grass
 * 3		|Ice
 * 4		|Sand
 * 5		|Tundra
 * @author Felix
 *
 */
public class Square {
	public static final int WIDTH = 500, HEIGHT = 500;
	private int type;
	//private GameMap map;
	public Square(final int type, GameMap map){
		if(type < 0 || type > 5){
			throw new IllegalArgumentException("A square can only be of type 0-5. type = " + type);
		}
		//this.map = map;
		this.type = type;
	}
	public int getType(){
		return type;
	}
}
